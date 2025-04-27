package com.mygdx.roguelikeproject.managers;

import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.enemies.BossDuck;
import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;

import java.util.List;

public class BossWave implements WaveManager {

    private final Player player;
    private boolean bossSpawned = false;
    private BossDuck boss;

    public BossWave(Player player) {
        this.player = player;
    }

    @Override
    public void update(float deltaTime, List<EnemyBase> enemies) {
        if (!bossSpawned) {
            // Supprimer tous les ennemis existants
            enemies.clear();

            // Créer et ajouter le boss au centre
            float centerX = 400 - 32; // Ajuste selon sprite boss
            float centerY = 300 - 32;
            boss = new BossDuck(centerX, centerY, player);
            enemies.add(boss);

            bossSpawned = true;
        }

        if (boss != null && !boss.isDead()) {
            boss.update(deltaTime);
        }
    }

    public boolean isBossDefeated() {
        return boss != null && boss.isDead();
    }
}
