package com.mygdx.roguelikeproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.utils.Direction;
import com.mygdx.roguelikeproject.utils.Constants;
import com.mygdx.roguelikeproject.utils.Hitbox;

public class Projectile {
    private static final float BASE_SPEED = Constants.PROJECTILE_SPEED;
    private static Texture texture;
    private float x, y;
    private final Direction direction;
    private final float width = 16;
    private final float height = 16;

    private boolean boosted = false;

    public Projectile(float x, float y, Direction direction) {
        if (texture == null) {
            texture = new Texture("assets/balle_left.png");
        }
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void update(float deltaTime) {
        float speed = boosted ? BASE_SPEED * 1.5f : BASE_SPEED;

        switch (direction) {
            case LEFT -> x -= speed * deltaTime;
            case RIGHT -> x += speed * deltaTime;
            case UP -> y += speed * deltaTime;
            case DOWN -> y -= speed * deltaTime;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isOutOfBounds() {
        return x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight();
    }

    public Hitbox getHitbox() {
        return new Hitbox(x, y, width, height);
    }

    public static void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }
}
