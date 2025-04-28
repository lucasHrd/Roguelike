// 📁 Fichier : utils/Damageable.java
package com.mygdx.roguelikeproject.utils;

public class Damageable {

    private final int maxHealth;
    private int currentHealth;
    private final float invincibilityDuration;
    private boolean isInvincible;
    private float invincibilityTimer;

    public Damageable(int maxHealth, float invincibilityDuration) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.invincibilityDuration = invincibilityDuration;
        this.isInvincible = false;
        this.invincibilityTimer = 0;
    }

    public void takeDamage(int damage) {
        if (!isInvincible && currentHealth > 0) {
            currentHealth -= damage;
            if (currentHealth < 0) currentHealth = 0;
            isInvincible = true;
            invincibilityTimer = invincibilityDuration;
        }
    }

    public void update(float deltaTime) {
        if (isInvincible) {
            invincibilityTimer -= deltaTime;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
                invincibilityTimer = 0;
            }
        }
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }


    public void heal(int amount) {
        if (currentHealth > 0) {
            currentHealth = Math.min(currentHealth + amount, maxHealth);
        }
    }


    public float getHealthRatio() {
        return (float) currentHealth / maxHealth;
    }
}
