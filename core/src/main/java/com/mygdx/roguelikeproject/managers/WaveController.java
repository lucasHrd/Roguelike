// 📁 Fichier : managers/WaveController.java
package com.mygdx.roguelikeproject.managers;

import com.badlogic.gdx.Gdx;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.enemies.BossDuck;
import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;

import java.util.List;

public class WaveController {

    private final Player player;
    private final WaveManager basicWave;
    private final WaveManager blueYellowWave;
    private final WaveManager redWave;
    private final WaveManager fullWave;

    private float totalTime;
    private boolean bossHasSpawned = false;
    private boolean bossFight = false;
    private boolean finalBossDefeated = false; // 🔥 Nouvelle variable
    private BossDuck boss;

    public WaveController(Player player) {
        this.player = player;
        this.basicWave = new BasicWave(player);
        this.blueYellowWave = new BlueYellowOnlyWave(player);
        this.redWave = new RedOnlyWave(player);
        this.fullWave = new FullWave(player);
        this.totalTime = 0f;
    }

    public void update(float deltaTime, List<EnemyBase> enemies) {
        totalTime += deltaTime;

        if (bossFight) {
            if (boss != null) {
                boss.update(deltaTime);
                if (boss.isDead()) {
                    bossFight = false;
                    bossHasSpawned = false;
                    finalBossDefeated = true; // ✅ Le boss a été battu
                    boss = null;
                    enemies.clear();
                    totalTime = 150f; // 🔥 Reprendre sur FullWave
                }
            }
            return;
        }

        if (totalTime <= 30f) {
            basicWave.update(deltaTime, enemies);
        } else if (totalTime <= 75f) {
            blueYellowWave.update(deltaTime, enemies);
        } else if (totalTime <= 120f) {
            redWave.update(deltaTime, enemies);
        } else if (totalTime <= 150f) {
            fullWave.update(deltaTime, enemies);
        } else if (!bossHasSpawned && !finalBossDefeated) {
            spawnBoss(enemies);
        } else {
            fullWave.update(deltaTime, enemies);
        }
    }

    private void spawnBoss(List<EnemyBase> enemies) {
        enemies.clear();
        float centerX = (Gdx.graphics.getWidth() / 2f) - 24;
        float centerY = (Gdx.graphics.getHeight() / 2f) - 24;
        boss = new BossDuck(centerX, centerY, player);
        enemies.add(boss);
        bossHasSpawned = true;
        bossFight = true;
    }

    public boolean isBossFight() {
        return bossFight;
    }

    public BossDuck getBoss() {
        return boss;
    }
}
