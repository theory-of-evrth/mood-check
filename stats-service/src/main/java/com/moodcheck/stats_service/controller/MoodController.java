package com.moodcheck.stats_service.controller;

import com.moodcheck.stats_service.model.MoodEntity;
import com.moodcheck.stats_service.repository.MoodRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/stats")
public class MoodController {
    private static final Logger log = LogManager.getLogger(MoodController.class);

    // TODO features :
    // - get most recent mood
    // - get most frequent mood within set period of time

    private final MoodRepository moodRepository;

    @Autowired
    public MoodController(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @GetMapping("/user/{userId}")
    public List<MoodEntity> getUserMoods(@PathVariable String userId, @AuthenticationPrincipal Jwt jwt) {
        String currentUserId = jwt.getSubject();
        log.info(currentUserId + "is trying to access info of " + userId);

        if (!Objects.equals(currentUserId, userId))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot access other users' information");
        }
        return moodRepository.findByUserId(userId);
    }
}
