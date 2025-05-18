package com.moodcheck.mood_service.service;

import com.moodcheck.mood_service.model.MoodColor;
import com.moodcheck.shared.Mood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class MoodService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final MoodMessageProducer producer;

    @Autowired
    public MoodService(MoodMessageProducer producer) {
        this.producer = producer;
    }

    public void processMood(Mood mood) {
        if (mood.getTimestamp() == null) {
            mood.setTimestamp(LocalDateTime.now());
        }

        producer.sendMood(mood);
    }

    public String getColorForUser(String userId) {
        String lastMood = restTemplate.getForObject(
                "http://localhost:8082/stats/user/" + userId + "/last", // example path
                String.class
        );

        return MoodColor.fromMood(lastMood).getHex();
    }

}
