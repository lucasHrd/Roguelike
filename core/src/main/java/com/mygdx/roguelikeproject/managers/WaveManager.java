package com.mygdx.roguelikeproject.managers;

import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;

import java.util.List;

public interface WaveManager {
    void update(float deltaTime, List<EnemyBase> enemies);
}
