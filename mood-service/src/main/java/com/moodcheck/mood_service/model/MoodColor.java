package com.moodcheck.mood_service.model;

public enum MoodColor {
    SAD("#0000FF"),
    ANGRY("#FF0000"),
    CALM("#00BFFF"),
    HAPPY("#FFFF00"),
    NEUTRAL("#808080"),
    UNKNOWN("#AAAAAA");

    private final String hex;
    MoodColor(String hex) { this.hex = hex; }
    public String getHex() { return hex; }

    public static MoodColor fromMood(String moodType) {
        if (moodType == null) return UNKNOWN;
        return switch (moodType.toLowerCase()) {
            case "sad", "depressed", "blue" -> SAD;
            case "angry", "mad", "furious" -> ANGRY;
            case "calm", "relaxed" -> CALM;
            case "happy", "excited", "joyful" -> HAPPY;
            case "neutral", "meh" -> NEUTRAL;
            default -> UNKNOWN;
        };
    }
}
