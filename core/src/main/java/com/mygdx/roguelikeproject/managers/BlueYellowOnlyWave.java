// 📁 Fichier : managers/BlueYellowOnlyWave.java
package com.mygdx.roguelikeproject.managers;

import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import com.mygdx.roguelikeproject.entities.enemies.BlueDuckEnemy;
import com.mygdx.roguelikeproject.entities.enemies.DuckEnemy;
import com.mygdx.roguelikeproject.entities.Player;

import java.util.List;
import java.util.Random;

public class BlueYellowOnlyWave implements WaveManager {

    private final Player player;
    private float spawnTimer;
    private static final float SPAWN_INTERVAL = 4.0f; // toutes les 4 secondes
    private final Random random = new Random();

    public BlueYellowOnlyWave(Player player) {
        this.player = player;
        this.spawnTimer = 0;
    }

    @Override
    public void update(float deltaTime, List<EnemyBase> enemies) {
        spawnTimer += deltaTime;

        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0;

            // Spawner un blue duck
            float[] spawnPosBlue = getRandomSpawnPosition();
            enemies.add(new BlueDuckEnemy(spawnPosBlue[0], spawnPosBlue[1], player));

            // Spawner un yellow duck
            float[] spawnPosYellow = getRandomSpawnPosition();
            enemies.add(new DuckEnemy(spawnPosYellow[0], spawnPosYellow[1], player));
        }
    }

    private float[] getRandomSpawnPosition() {
        int choice = random.nextInt(8);

        int screenWidth = 800;
        int screenHeight = 600;
        int offset = 50;

        return switch (choice) {
            case 0 -> new float[]{offset, offset}; // coin bas gauche
            case 1 -> new float[]{screenWidth - offset, offset}; // coin bas droit
            case 2 -> new float[]{offset, screenHeight - offset}; // coin haut gauche
            case 3 -> new float[]{screenWidth - offset, screenHeight - offset}; // coin haut droit
            case 4 -> new float[]{screenWidth / 2f, screenHeight - offset}; // centre haut
            case 5 -> new float[]{screenWidth / 2f, offset}; // centre bas
            case 6 -> new float[]{offset, screenHeight / 2f}; // centre gauche
            case 7 -> new float[]{screenWidth - offset, screenHeight / 2f}; // centre droit
            default -> new float[]{screenWidth / 2f, screenHeight / 2f}; // centre écran
        };
    }
}
