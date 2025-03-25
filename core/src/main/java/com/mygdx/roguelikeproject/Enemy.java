package com.mygdx.roguelikeproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    private float x, y;
    private float speed = 50;
    private Texture texture;
    private int health = 50;
    private Player player;

    public Enemy(float x, float y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.texture = new Texture("C:\\Users\\mehdi\\Desktop\\L3\\genie logiciel\\RGLK_PRJKT\\assets\\walk_down2.png");
    }

    public void update(float deltaTime) {
        float playerX = player.getX();
        float playerY = player.getY();

        float deltaX = playerX - x;
        float deltaY = playerY - y;
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
            // Gérer la mort de l'ennemi
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
