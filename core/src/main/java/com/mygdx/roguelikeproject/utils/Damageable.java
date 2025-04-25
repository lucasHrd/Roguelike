package com.mygdx.roguelikeproject.utils;

/**
 * Représente une entité qui peut subir des dégâts et devenir temporairement invincible.
 */
public class Damageable {

    private final Health health;
    private boolean isInvincible = false;
    private float invincibilityTimer = 0f;
    private final float invincibilityDuration;

    public Damageable(int maxHealth, float invincibilityDuration) {
        this.health = new Health(maxHealth);
        this.invincibilityDuration = invincibilityDuration;
    }

    public void takeDamage(int dmg) {
        if (!isInvincible) {
            health.takeDamage(dmg);
            isInvincible = true;
            invincibilityTimer = invincibilityDuration;
        }
    }

    public void update(float deltaTime) {
        if (isInvincible) {
            invincibilityTimer -= deltaTime;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
            }
        }
    }

    public boolean isDead() {
        return health.isDead();
    }

    public float getHealthRatio() {
        return health.getRatio();
    }

    public int getCurrentHealth() {
        return health.getCurrent();
    }

    public int getMaxHealth() {
        return health.getMax();
    }

    public boolean isInvincible() {
        return isInvincible;
    }
}
