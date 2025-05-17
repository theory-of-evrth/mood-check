package com.moodcheck.mood_service.service;

import com.moodcheck.shared.Mood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MoodService {

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
}
