package com.mygdx.roguelikeproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.entities.Player.Direction;

public class Projectile {
    private static final float SPEED = 500;
    private static Texture texture;

    private float x, y;
    private final Direction direction;

    public Projectile(float x, float y, Direction direction) {
        if (texture == null) {
            texture = new Texture("assets/balle_left.png");
        }
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void update(float deltaTime) {
        switch (direction) {
            case LEFT -> x -= SPEED * deltaTime;
            case RIGHT -> x += SPEED * deltaTime;
            case UP -> y += SPEED * deltaTime;
            case DOWN -> y -= SPEED * deltaTime;
        }
    }

    public boolean isOutOfBounds() {
        return x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public static void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
