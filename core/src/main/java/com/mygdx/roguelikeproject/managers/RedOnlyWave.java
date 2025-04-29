// 📁 Fichier : managers/RedOnlyWave.java
package com.mygdx.roguelikeproject.managers;

import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import com.mygdx.roguelikeproject.entities.enemies.RedDuckEnemy;
import com.mygdx.roguelikeproject.entities.Player;

import java.util.List;
import java.util.Random;

public class RedOnlyWave implements WaveManager {

    private final Player player;
    private float spawnTimer;
    private static final float SPAWN_INTERVAL = 5.0f; // toutes les 5 secondes
    private final Random random = new Random();

    public RedOnlyWave(Player player) {
        this.player = player;
        this.spawnTimer = 0;
    }

    @Override
    public void update(float deltaTime, List<EnemyBase> enemies) {
        spawnTimer += deltaTime;

        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0;

            // On spawn 2 ennemis rouges
            for (int i = 0; i < 2; i++) {
                float[] spawnPos = getRandomSpawnPosition();
                enemies.add(new RedDuckEnemy(spawnPos[0], spawnPos[1], player));
            }
        }
    }

    private float[] getRandomSpawnPosition() {
        int choice = random.nextInt(8);

        int screenWidth = 800;  // taille de la fenêtre
        int screenHeight = 600;
        int offset = 50; // décale pour ne pas être collé au bord

        return switch (choice) {
            case 0 -> new float[]{offset, offset}; // coin bas gauche
            case 1 -> new float[]{screenWidth - offset, offset}; // coin bas droit
            case 2 -> new float[]{offset, screenHeight - offset}; // coin haut gauche
            case 3 -> new float[]{screenWidth - offset, screenHeight - offset}; // coin haut droit
            case 4 -> new float[]{screenWidth / 2f, screenHeight - offset}; // centre haut
            case 5 -> new float[]{screenWidth / 2f, offset}; // centre bas
            case 6 -> new float[]{offset, screenHeight / 2f}; // centre gauche
            case 7 -> new float[]{screenWidth - offset, screenHeight / 2f}; // centre droit
            default -> new float[]{screenWidth / 2f, screenHeight / 2f}; // centre écran (par sécurité)
        };
    }
}
