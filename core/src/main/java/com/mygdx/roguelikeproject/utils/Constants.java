// 📁 Fichier : utils/Constants.java
package com.mygdx.roguelikeproject.utils;

public class Constants {

    // === PLAYER ===
    public static final int PLAYER_HEALTH = 150;
    public static final float PLAYER_INVINCIBILITY_DURATION = 1.0f;
    public static final float PLAYER_BASE_SPEED = 150f;

    // === PROJECTILES ===
    public static final float PROJECTILE_SPEED = 500f;
    public static final int PROJECTILE_DAMAGE = 100;

    // === ENEMIES ===
    public static final float ENEMY_BASE_SPEED = 50f;
    public static final int ENEMY_MAX_HEALTH = 50;
    public static final float ENEMY_INVINCIBILITY_DURATION = 0.5f;
    public static final int ENEMY_CONTACT_DAMAGE = 15;

    // === BOSS ===
    public static final int BOSS_HEALTH = 1000;
    public static final float BOSS_SPEED = 30f;
    public static final float BOSS_INVINCIBILITY_DURATION = 1.5f;
    public static final float BOSS_PROJECTILE_INTERVAL_ACTIVE = 3.0f;
    public static final float BOSS_PROJECTILE_INTERVAL_PAUSE = 2.0f;
    public static final int BOSS_PROJECTILE_DAMAGE = 30;
    public static final int BOSS_MAX_HEALTH = 500;

    // === HEALTH BAR PLAYER ===
    public static final float HEALTHBAR_WIDTH = 250f;
    public static final float HEALTHBAR_HEIGHT = 12f;
    public static final float HEALTHBAR_Y_OFFSET = 30f;

    // === HEALTH BAR BOSS ===
    public static final float BOSS_HEALTHBAR_WIDTH = 300f;
    public static final float BOSS_HEALTHBAR_HEIGHT = 16f;
    public static final float BOSS_HEALTHBAR_Y_OFFSET = 20f;

    // === GAME LOGIC ===
    public static final float ENEMY_SPAWN_INTERVAL = 3.0f; // pour les vagues
}
