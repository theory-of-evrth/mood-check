package com.moodcheck.shared;

import java.time.LocalDateTime;

public class Mood {
    private String userId;
    private String moodType;
    private LocalDateTime timestamp;

    public Mood() {}

    public Mood(String userId, String moodType, LocalDateTime timestamp) {
        this.userId = userId;
        this.moodType = moodType;
        this.timestamp = timestamp;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMoodType() { return moodType; }
    public void setMoodType(String moodType) { this.moodType = moodType; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
