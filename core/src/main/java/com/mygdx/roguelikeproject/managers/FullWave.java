package com.mygdx.roguelikeproject.managers;

import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import com.mygdx.roguelikeproject.entities.enemies.RedDuckEnemy;
import com.mygdx.roguelikeproject.entities.enemies.BlueDuckEnemy;
import com.mygdx.roguelikeproject.entities.enemies.DuckEnemy;
import com.mygdx.roguelikeproject.entities.Player;

import java.util.List;
import java.util.Random;

public class FullWave implements WaveManager {

    private final Player player;
    private float spawnTimer;
    private static final float SPAWN_INTERVAL = 4.0f;
    private final Random random = new Random();

    public FullWave(Player player) {
        this.player = player;
        this.spawnTimer = 0;
    }

    @Override
    public void update(float deltaTime, List<EnemyBase> enemies) {
        spawnTimer += deltaTime;

        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0;

            // RedDuckEnemy
            float[] redPos = getRandomSpawnPosition();
            enemies.add(new RedDuckEnemy(redPos[0], redPos[1], player));

            // BlueDuckEnemy
            float[] bluePos = getRandomSpawnPosition();
            enemies.add(new BlueDuckEnemy(bluePos[0], bluePos[1], player));

            // DuckEnemy (jaune)
            float[] yellowPos = getRandomSpawnPosition();
            enemies.add(new DuckEnemy(yellowPos[0], yellowPos[1], player));
        }
    }

    private float[] getRandomSpawnPosition() {
        int choice = random.nextInt(8);

        int screenWidth = 800;
        int screenHeight = 600;
        int offset = 50;

        return switch (choice) {
            case 0 -> new float[]{offset, offset};
            case 1 -> new float[]{screenWidth - offset, offset};
            case 2 -> new float[]{offset, screenHeight - offset};
            case 3 -> new float[]{screenWidth - offset, screenHeight - offset};
            case 4 -> new float[]{screenWidth / 2f, screenHeight - offset};
            case 5 -> new float[]{screenWidth / 2f, offset};
            case 6 -> new float[]{offset, screenHeight / 2f};
            case 7 -> new float[]{screenWidth - offset, screenHeight / 2f};
            default -> new float[]{screenWidth / 2f, screenHeight / 2f};
        };
    }
}
