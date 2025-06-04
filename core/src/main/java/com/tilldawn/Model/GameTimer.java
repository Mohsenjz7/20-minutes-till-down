package com.tilldawn.Model;

public class GameTimer {
    private static float elapsedTime = 0;

    public static void update(float delta) {
        elapsedTime += delta;
    }

    public static String getFormattedTime() {
        int totalSeconds = (int) elapsedTime;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
