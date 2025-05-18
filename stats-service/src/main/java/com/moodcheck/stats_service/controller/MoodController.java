package com.moodcheck.stats_service.controller;

import com.moodcheck.stats_service.model.MoodEntity;
import com.moodcheck.stats_service.repository.MoodRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    // - get most frequent mood within set period of time

    private final MoodRepository moodRepository;

    @Autowired
    public MoodController(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Operation(
            summary = "Get all moods for a user",
            description = "Returns a list of all mood entries for the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moods found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            }
    )
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

    @Operation(
            summary = "Get latest mood type for a user",
            description = "Returns the mood type of the most recent mood entry for the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Last mood retrieved"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                    @ApiResponse(responseCode = "404", description = "No moods found for user")
            }
    )
    @GetMapping("/user/{userId}/last")
    public String getLastMoodType(@PathVariable String userId, @AuthenticationPrincipal Jwt jwt) {
        String currentUserId = jwt.getSubject();
        log.info(currentUserId + " is trying to access last mood of " + userId);

        if (!Objects.equals(currentUserId, userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot access other users' information");
        }

        List<MoodEntity> moods = moodRepository.findByUserId(userId);

        if (moods.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No moods found for user");
        }

        MoodEntity lastMood = moods.stream()
                .max((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error"));

        return lastMood.getMoodType();
    }
}
