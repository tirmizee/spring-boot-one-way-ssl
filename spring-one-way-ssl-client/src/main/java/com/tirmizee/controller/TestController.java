package com.tirmizee.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    private final WebClient webClient;

    public TestController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/call-server")
    public Mono<Map<String, String>> callServer() {
        return webClient
                .get()
                .uri("/api/hello")
                .retrieve() .bodyToMono(new ParameterizedTypeReference<>(){});
    }

}
