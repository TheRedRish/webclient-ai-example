package com.example.aiprojectexample;

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

        WebClient webClient = WebClient.create("https://api.openai.com");

        ChatGPTRequest request = new ChatGPTRequest(
                "gpt-3.5-turbo",
                List.of(new ChatGPTRequest.Message("user", "Tell me a joke")),
                1,
                1
        );

        webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", key)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .subscribe(response -> {
                    String content = response.getChoices().get(0).getMessage().getContent();
                    System.out.println("ChatGPT Response: " + content);
                });
    }
}
