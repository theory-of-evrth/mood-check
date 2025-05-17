package com.moodcheck.stats_service.service;

import com.moodcheck.shared.Mood;
import com.moodcheck.stats_service.model.MoodEntity;
import com.moodcheck.stats_service.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MoodConsumer {

    private final MoodRepository moodRepository;

    @Autowired
    public MoodConsumer(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @JmsListener(destination = "mood.queue")
    public void receiveMood(Mood mood) {
        MoodEntity entity = new MoodEntity(mood.getUserId(), mood.getMoodType(), mood.getTimestamp());
        moodRepository.save(entity);
        System.out.println("Saved mood for user: " + mood.getUserId());
    }
}

