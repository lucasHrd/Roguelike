package com.mygdx.roguelikeproject.utils;

public class Timer {

    private float elapsedTime;

    public Timer() {
        this.elapsedTime = 0f;
    }

    public void update(float deltaTime) {
        elapsedTime += deltaTime;
    }

    public void reset() {
        elapsedTime = 0f;
    }

    public float getTime() {
        return elapsedTime;
    }

    public String getFormattedTime() {
        int totalSeconds = (int) elapsedTime;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
