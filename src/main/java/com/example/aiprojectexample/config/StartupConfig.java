package com.example.aiprojectexample.config;

import com.example.aiprojectexample.ChatGPT.ChatGPTRequestReturningJSON;
import com.example.aiprojectexample.schema.RecipeSchemaAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class StartupConfig implements CommandLineRunner {
    @Value("${openai.key}")
    private String key;

    @Override
    public void run(String... args) {
        WebClient webClient = WebClient.create("https://api.openai.com");

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

//        webClient.post()
//                .uri("/v1/chat/completions")
//                .header("Authorization", key)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(GPTResponse.class)
//                .subscribe(response -> {
//                    String name = response.choices.get(0).message.tool_calls.get(0).function.name;
//                    String argsJson = response.choices.get(0).message.tool_calls.get(0).function.arguments;
//                    System.out.println("ChatGPT Response: " + name);
//                    System.out.println(argsJson);
//                });
    }
}
