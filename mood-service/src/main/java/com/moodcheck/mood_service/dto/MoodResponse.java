package com.moodcheck.mood_service.dto;

import com.moodcheck.shared.Mood;
import com.moodcheck.mood_service.model.MoodColor;

public class MoodResponse {
    private String moodType;
    private String timestamp;
    private String colorHex;

    public MoodResponse(Mood mood) {
        this.moodType = mood.getMoodType();
        this.timestamp = mood.getTimestamp().toString();
        this.colorHex = MoodColor.fromMood(mood.getMoodType()).getHex();
    }

    // Getters
    public String getMoodType() { return moodType; }
    public String getTimestamp() { return timestamp; }
    public String getColorHex() { return colorHex; }
}

