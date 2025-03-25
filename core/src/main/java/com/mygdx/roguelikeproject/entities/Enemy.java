package com.mygdx.roguelikeproject.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    private float x, y;
    private final float speed = 50;
    private final Texture texture;
    private int health = 50;
    private final Player player;

    public Enemy(float x, float y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.texture = new Texture("assets/walk_down2.png");
    }

    public void update(float deltaTime) {
        float deltaX = player.getX() - x;
        float deltaY = player.getY() - y;
        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (length > 0) {
            x += (deltaX / length) * speed * deltaTime;
            y += (deltaY / length) * speed * deltaTime;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // À implémenter : mort de l'ennemi
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
