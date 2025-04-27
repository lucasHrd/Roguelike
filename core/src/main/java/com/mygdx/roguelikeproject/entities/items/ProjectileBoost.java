package com.mygdx.roguelikeproject.entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.utils.Position;

public class ProjectileBoost implements ItemBase {

    private final Position position;
    private final Texture texture;
    private boolean collected = false;
    private float lifetime = 30f;

    public ProjectileBoost(float x, float y) {
        this.position = new Position(x, y);
        this.texture = new Texture("assets/projectile_boost.png");
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
        player.setProjectileBoost(true);
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
