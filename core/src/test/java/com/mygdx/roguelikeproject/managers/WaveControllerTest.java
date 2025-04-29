package com.mygdx.roguelikeproject.managers;

import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WaveControllerTest {

    private WaveController waveController;
    private Player mockPlayer;
    private List<EnemyBase> enemies;

    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        waveController = new WaveController(mockPlayer);
        enemies = new ArrayList<>();
    }

    @Test
    public void testStartBasicWave() {
        waveController.update(10f, enemies);
        assertFalse(waveController.isBossFight());
    }

    @Test
    public void testStartBlueYellowWave() {
        waveController.update(40f, enemies);
        assertFalse(waveController.isBossFight());
    }

    @Test
    public void testStartRedWave() {
        waveController.update(80f, enemies);
        assertFalse(waveController.isBossFight());
    }

    @Test
    public void testStartFullWave() {
        waveController.update(130f, enemies);
        assertFalse(waveController.isBossFight());
    }

    @Test
    public void testBossSpawn() {
        waveController.update(151f, enemies);
        assertTrue(waveController.isBossFight());
        assertNotNull(waveController.getBoss());
    }

    @Test
    public void testBossDefeatedResetsToFullWave() {
        waveController.update(151f, enemies);
        assertTrue(waveController.isBossFight());

        waveController.getBoss().takeDamage(99999);

        waveController.update(0.1f, enemies);

        assertFalse(waveController.isBossFight());
    }
}
