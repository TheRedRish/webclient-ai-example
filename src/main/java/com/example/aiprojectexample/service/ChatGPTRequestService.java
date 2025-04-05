package com.example.aiprojectexample.service;

import com.example.aiprojectexample.ChatGPT.ChatGPTRequestReturningJSON;
import com.example.aiprojectexample.ChatGPT.ChatGPTResponseFromJSON;
import com.example.aiprojectexample.Model.Recipe;
import com.example.aiprojectexample.schema.RecipeSchemaAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.aiprojectexample.Model.Recipe.fromJson;

@Service
public class ChatGPTRequestService {
    @Value("${openai.key}")
    private static String key;
    private static final WebClient webClient = WebClient.create("https://api.openai.com");


    public Mono<Recipe> generateRecipeWithSchema() {
        ChatGPTRequestReturningJSON request = ChatGPTRequestReturningJSON.builder()
                .model("gpt-3.5-turbo-1106")
                .messages(List.of(
                        new ChatGPTRequestReturningJSON.Message("system", "Return only a recipe in JSON format matching the schema."),
                        new ChatGPTRequestReturningJSON.Message("user", "Give me a quick dinner recipe with left over pizza.")
                ))
                .tools(List.of(
                        new ChatGPTRequestReturningJSON.Tool(
                                "function",
                                new ChatGPTRequestReturningJSON.Function("create_recipe", new RecipeSchemaAdapter(), true)
                        )
                ))
                .tool_choice(new ChatGPTRequestReturningJSON.ToolChoice("function", new ChatGPTRequestReturningJSON.ToolChoice.Function("create_recipe")))
                .temperature(0.7)
                .top_p(1.0)
                .build();

        return webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", key)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGPTResponseFromJSON.class)
                .map(response -> fromJson(response.getChoices().get(0).getMessage().getTool_calls().get(0).getFunction().getArguments()));
    }
}
