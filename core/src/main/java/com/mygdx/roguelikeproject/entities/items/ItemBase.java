package com.mygdx.roguelikeproject.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.utils.Hitbox;

public interface ItemBase {

    void update(float deltaTime);
    void draw(SpriteBatch batch);
    void applyEffect(Player player);
    boolean isCollected();
    boolean shouldBeRemoved();
    float getX();
    float getY();
    Hitbox getHitbox();
}
