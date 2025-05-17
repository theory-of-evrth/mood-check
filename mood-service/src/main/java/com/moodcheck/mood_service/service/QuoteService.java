package com.moodcheck.mood_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Service
public class QuoteService {

    private final RestTemplate restTemplate;

    public QuoteService() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://zenquotes.io/api"));
    }

    public String getRandomQuote() {
        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity("/random", Object[].class);
            if (response.getBody() != null && response.getBody().length > 0) {
                Map<String, String> quote = (Map<String, String>) response.getBody()[0];
                return quote.get("q") + " — " + quote.get("a");
            }
        } catch (Exception e) {
            return "No quote available right now.";
        }
        return "No quote found.";
    }
}
