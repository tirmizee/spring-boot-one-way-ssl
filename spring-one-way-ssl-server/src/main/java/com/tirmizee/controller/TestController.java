package com.tirmizee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/hello")
    public Mono<Map<String, String>> hello() {
        return Mono.just( Map.of( "message", "Hello from 1 Way SSL Server" ) );
    }
}
