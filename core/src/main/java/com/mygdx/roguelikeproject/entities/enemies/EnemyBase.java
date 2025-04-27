package com.mygdx.roguelikeproject.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.utils.Hitbox;

public interface EnemyBase {
    void update(float deltaTime);
    void draw(SpriteBatch batch);
    void takeDamage(int dmg);
    boolean isDead();
    float getX();
    float getY();
    Hitbox getHitbox();
}
