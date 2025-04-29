package com.mygdx.roguelikeproject.integration;

import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.enemies.BossDuck;
import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import com.mygdx.roguelikeproject.managers.WaveController;
import com.mygdx.roguelikeproject.world.GameMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BossSpawnIntegrationTest {

    private WaveController waveController;
    private List<EnemyBase> enemies;

    @Before
    public void setUp() {
        GameMap mockMap = mock(GameMap.class);
        when(mockMap.isWall(anyInt(), anyInt())).thenReturn(false);
        Player mockPlayer = new Player(mockMap);

        waveController = new WaveController(mockPlayer);
        enemies = new ArrayList<>();
    }

    @Test
    public void testBossSpawnsAfterTime() {
        float elapsedTime = 0f;
        float deltaTime = 1f;

        // Simuler 151 secondes de jeu
        while (elapsedTime < 151f) {
            waveController.update(deltaTime, enemies);
            elapsedTime += deltaTime;
        }

        assertFalse(enemies.isEmpty());
        assertTrue(waveController.isBossFight());
        assertTrue(waveController.getBoss() instanceof BossDuck);
    }
}
