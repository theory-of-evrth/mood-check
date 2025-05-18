package com.moodcheck.stats_service.controller;

import com.moodcheck.stats_service.model.MoodEntity;
import com.moodcheck.stats_service.repository.MoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MoodController.class)
class MoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MoodRepository moodRepository;

    @Test
    @WithMockUser
    void testGetUserMoods_Success() throws Exception {
        String userId = "user123";
        MoodEntity mood = new MoodEntity(userId, "HAPPY", LocalDateTime.now());
        when(moodRepository.findByUserId(userId)).thenReturn(List.of(mood));

        mockMvc.perform(get("/stats/user/{userId}", userId)
                        .requestAttr("jwt", jwt(userId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].moodType").value("HAPPY"));
    }

    @Test
    @WithMockUser
    void testGetUserMoods_Unauthorized() throws Exception {
        mockMvc.perform(get("/stats/user/{userId}", "otherUser")
                        .requestAttr("jwt", jwt("myUser")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetLastMoodType_Success() throws Exception {
        String userId = "user123";
        MoodEntity mood = new MoodEntity(userId, "SAD", LocalDateTime.now());
        when(moodRepository.findByUserId(userId)).thenReturn(List.of(mood));

        mockMvc.perform(get("/stats/user/{userId}/last", userId)
                        .requestAttr("jwt", jwt(userId)))
                .andExpect(status().isOk())
                .andExpect(content().string("SAD"));
    }

    @Test
    @WithMockUser
    void testGetLastMoodType_NotFound() throws Exception {
        String userId = "user123";
        when(moodRepository.findByUserId(userId)).thenReturn(List.of());

        mockMvc.perform(get("/stats/user/{userId}/last", userId)
                        .requestAttr("jwt", jwt(userId)))
                .andExpect(status().isNotFound());
    }

    private Jwt jwt(String subject) {
        return Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", subject)
                .build();
    }
}
