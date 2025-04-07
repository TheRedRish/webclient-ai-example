package com.example.aiprojectexample.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final WebClient nemligWebClient;

    public ProxyController() {
        // WebClient for the Random Number API
        this.nemligWebClient = WebClient.create("https://www.nemlig.com");
    }

    @PostMapping("/nemlig")
    public ResponseEntity<String> proxyNemlig(@RequestBody Credentials credentials, HttpServletResponse httpServletResponse) {
        return nemligWebClient.post()
                .uri("/webapi/login/login")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(new Credentials(credentials.username, credentials.password))
                .exchangeToMono(response -> {
                    // Get the 'Set-Cookie' headers from the response
                    List<String> cookies = response.headers().header("Set-Cookie");

                    // Iterate over the cookies and add each one to the response
                    for (String cookie : cookies) {
                        httpServletResponse.addHeader("Set-Cookie", cookie);
                    }

                    // Return a success response
                    return Mono.just(ResponseEntity.ok(""));
                }).block();
    }

    @PostMapping("/add-to-basket")
    public ResponseEntity<String> addToBasket(@RequestParam String productId, @RequestParam int quantity, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String cookies = request.getHeader("Cookie");

        return nemligWebClient.post()
                .uri("/webapi/basket/AddToBasket")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.COOKIE, cookies)  // Pass cookies here
                .body(BodyInserters.fromValue(new AddToBasketRequest(productId, quantity)))
                .exchangeToMono(response -> {
                    List<String> moreCookies = response.headers().header("Set-Cookie");

                    // Iterate over the cookies and add each one to the response
                    for (String cookie : moreCookies) {
                        httpServletResponse.addHeader("Set-Cookie", cookie);
                    }
                    // Return a success response
                    return Mono.just(ResponseEntity.ok(""));
                }).block();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddToBasketRequest {
        private String productId;
        private int quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Credentials {
        private String username;
        private String password;
    }
}
