package com.mygdx.roguelikeproject.utils;

public record Hitbox(float x, float y, float width, float height) {
    public boolean overlaps(Hitbox other) {
        return x < other.x + other.width &&
            x + width > other.x &&
            y < other.y + other.height &&
            y + height > other.y;
    }
}
