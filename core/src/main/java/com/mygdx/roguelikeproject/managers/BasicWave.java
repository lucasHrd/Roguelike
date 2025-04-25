package com.mygdx.roguelikeproject.managers;

import com.badlogic.gdx.Gdx;
import com.mygdx.roguelikeproject.entities.DuckEnemy;
import com.mygdx.roguelikeproject.entities.EnemyBase;
import com.mygdx.roguelikeproject.entities.Player;

import java.util.List;
import java.util.Random;

public class BasicWave implements WaveManager {

    private float spawnTimer = 0;
    private final float spawnInterval = 3f; // toutes les 3 secondes
    private final Player player;
    private final Random random = new Random();

    public BasicWave(Player player) {
        this.player = player;
    }

    @Override
    public void update(float deltaTime, List<EnemyBase> enemies) {
        spawnTimer += deltaTime;

        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;

            float x = 0, y = 0;
            int type = random.nextInt(8);

            switch (type) {
                case 0 -> { x = 0; y = 0; }                                      // coin haut gauche
                case 1 -> { x = 0; y = Gdx.graphics.getHeight(); }              // coin bas gauche
                case 2 -> { x = Gdx.graphics.getWidth(); y = 0; }               // coin haut droit
                case 3 -> { x = Gdx.graphics.getWidth(); y = Gdx.graphics.getHeight(); } // coin bas droit
                case 4 -> { x = Gdx.graphics.getWidth() / 2f; y = 0; }          // milieu haut
                case 5 -> { x = Gdx.graphics.getWidth() / 2f; y = Gdx.graphics.getHeight(); } // milieu bas
                case 6 -> { x = 0; y = Gdx.graphics.getHeight() / 2f; }         // milieu gauche
                case 7 -> { x = Gdx.graphics.getWidth(); y = Gdx.graphics.getHeight() / 2f; } // milieu droit
            }

            enemies.add(new DuckEnemy(x, y, player));
        }
    }
}
