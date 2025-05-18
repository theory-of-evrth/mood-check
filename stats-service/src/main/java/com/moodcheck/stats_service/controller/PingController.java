package com.moodcheck.stats_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @Operation(summary = "Ping the server", description = "Returns 'pong!' if the service is up")
    @GetMapping("/ping")
    public String respond() {
        return "pong!";
    }
}
