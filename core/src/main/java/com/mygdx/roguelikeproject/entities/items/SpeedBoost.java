package com.mygdx.roguelikeproject.entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.utils.Position;

public class SpeedBoost implements ItemBase {

    private final Position position;
    private final Texture texture;
    private boolean collected = false;
    private float lifetime = 30f;

    public SpeedBoost(float x, float y) {
        this.position = new Position(x, y);
        this.texture = new Texture("assets/speed_boost.png");
    }

    @Override
    public void update(float deltaTime) {
        lifetime -= deltaTime;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, position.x(), position.y());
        }
    }

    @Override
    public void applyEffect(Player player) {
        player.scheduleSpeedReset(10f);
        player.setSpeedModifier(1.5f);
        collected = true;
    }

    @Override
    public boolean isCollected() {
        return collected;
    }

    @Override
    public boolean shouldBeRemoved() {
        return collected || lifetime <= 0;
    }

    @Override
    public float getX() {
        return position.x();
    }

    @Override
    public float getY() {
        return position.y();
    }

    @Override
    public Hitbox getHitbox() {
        return new Hitbox(position.x(), position.y(), 32, 32);
    }
}
