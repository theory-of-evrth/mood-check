package com.moodcheck.stats_service.controller;

import com.moodcheck.stats_service.model.MoodEntity;
import com.moodcheck.stats_service.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class MoodController {

    // TODO features :
    // - get most recent mood
    // - get most frequent mood within set period of time

    private final MoodRepository moodRepository;

    @Autowired
    public MoodController(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @GetMapping("/user/{userId}")
    public List<MoodEntity> getUserMoods(@PathVariable String userId) {
        return moodRepository.findByUserId(userId);
    }
}
