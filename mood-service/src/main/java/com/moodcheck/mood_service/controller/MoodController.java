package com.moodcheck.mood_service.controller;

import com.moodcheck.mood_service.model.MoodColor;
import com.moodcheck.mood_service.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moodcheck.shared.Mood;
import com.moodcheck.mood_service.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/moods")
public class MoodController {

    private final MoodService moodService;
    private final QuoteService quoteService;

    @Autowired
    public MoodController(MoodService moodService, QuoteService quoteService) {
        this.moodService = moodService;
        this.quoteService = quoteService;
    }

    @Operation(summary = "Soumettre une humeur", description = "Envoie une humeur vers la queue JMS pour traitement.")
    @PostMapping
    public String submitMood(@RequestBody Mood mood, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        if (!Objects.equals(mood.getUserId(), userId))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create moods for other users");
        }
        moodService.processMood(mood);
        return "Mood received and dispatched.";
    }

    // TODO : (? discuss) to integrate logic with services, possibly returns quote+color of most recent mood
    @GetMapping("/quote")
    public String getQuote() {
        return quoteService.getRandomQuote();
    }

    @GetMapping("/check")
    public String checkStatsService() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8082/ping", String.class);
        return "Stats service says: " + result;
    }

    @GetMapping("/suggestion")
    public Map<String, String> getSuggestionWithColor(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        String quote = quoteService.getRandomQuote();
        String color = moodService.getColorForUser(userId);

        return Map.of(
                "quote", quote,
                "color", color
        );
    }
}
