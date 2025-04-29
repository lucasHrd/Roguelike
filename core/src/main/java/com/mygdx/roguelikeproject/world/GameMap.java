package com.mygdx.roguelikeproject.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMap {

    public GameMap() {
        // Plus besoin d'assets, on veut juste un fond noir
    }

    public void render(SpriteBatch batch) {
        // Rien à dessiner, fond noir automatique
    }

    public boolean isWall(int x, int y) {
        return false; // Pas de mur détecté, tout est accessible pour l'instant
    }

    public float getSpawnX() {
        return 64f;
    }

    public float getSpawnY() {
        return 64f;
    }

    public void dispose() {
        // Rien à libérer pour l'instant
    }
}
