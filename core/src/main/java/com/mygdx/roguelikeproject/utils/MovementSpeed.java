package com.mygdx.roguelikeproject.utils;

/**
 * Représente la vitesse de déplacement d'une entité.
 * Peut être modifiée dynamiquement (boost, slow, etc.)
 */
public class MovementSpeed {

    private float baseSpeed;
    private float speedModifier = 1.0f; // Pour les boosts ou ralentissements

    public MovementSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public float get() {
        return baseSpeed * speedModifier;
    }

    public void setBaseSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public void setModifier(float modifier) {
        this.speedModifier = modifier;
    }

    public void resetModifier() {
        this.speedModifier = 1.0f;
    }
}
