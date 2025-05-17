package com.moodcheck.mood_service.service;

import com.moodcheck.shared.Mood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MoodMessageProducer {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public MoodMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMood(Mood mood) {
        jmsTemplate.convertAndSend("mood.queue", mood);
    }
}
