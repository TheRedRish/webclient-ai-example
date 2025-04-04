package com.example.aiprojectexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class AiProjectExampleApplication implements CommandLineRunner {
    @Value("${api.key}")
    private String key;

    public static void main(String[] args) {
        SpringApplication.run(AiProjectExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Build WebClient with Jackson support
//        WebClient webClient = WebClient.builder()
//                .baseUrl("https://api.openai.com")
//                .defaultHeader("Authorization", key)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .codecs(configurer -> configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper())))
//                .build();

//         Create the JSON request body
        GPTRequest request = new GPTRequest(
                "gpt-4-1106-preview", // Model you're using
                List.of(
                        new GPTRequest.Message("system", "Return only a recipe in JSON format matching the schema."),
                        new GPTRequest.Message("user", "Give me a quick dinner recipe with shrimp and pasta, under 30 minutes.")
                ),
                List.of(
                        new GPTRequest.Tool(
                                "function",
                                new GPTRequest.Function("create_recipe", new GPTRequest.Parameters())
                        )
                ),
                new GPTRequest.ToolChoice("function", new GPTRequest.ToolChoice.Function("create_recipe"))
        );

//        ChatGPTRequest request = new ChatGPTRequest(
//                "gpt-3.5-turbo",
//                List.of(new ChatGPTRequest.Message("user", "Tell me a joke")),
//                1,
//                1
//        );

        // Send the POST request
//        webClient.post()
//                .uri("/v1/chat/completions")
//                .bodyValue(jsonString)
//                .retrieve()
//                .bodyToMono(GPTResponse.class)
//                .subscribe(response -> {
//                    String content = response.getChoices().get(0).getMessage().getContent();
//                    System.out.println("ChatGPT Response: " + content);
//                });
       WebClient webClient = WebClient.create("https://api.openai.com");


        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(request); // your GPTRequest instance

        webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", key)
                .header("Content-Type", "application/json")
                .bodyValue(jsonString)
                .retrieve()
                .bodyToMono(GPTResponse.class)
                .subscribe(response -> {
                    String name = response.choices.get(0).message.tool_calls.get(0).function.name;
                    String argsJson = response.choices.get(0).message.tool_calls.get(0).function.arguments;
                    System.out.println("ChatGPT Response: " + name);
                    System.out.println(argsJson);
                });
    }
}