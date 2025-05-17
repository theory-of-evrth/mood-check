package com.moodcheck.stats_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mood_logs")
public class MoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String moodType;
    private LocalDateTime timestamp;

    public MoodEntity() {}

    public MoodEntity(String userId, String moodType, LocalDateTime timestamp) {
        this.userId = userId;
        this.moodType = moodType;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMoodType() { return moodType; }
    public void setMoodType(String moodType) { this.moodType = moodType; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
