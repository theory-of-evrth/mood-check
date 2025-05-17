package com.moodcheck.mood_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MoodController {

    @GetMapping("/mood/check")
    public String checkStatsService() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8082/ping", String.class);
        return "Stats service says: " + result;
    }
}
