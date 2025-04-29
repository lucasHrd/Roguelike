package com.mygdx.roguelikeproject.entities;

import com.mygdx.roguelikeproject.utils.Constants;
import com.mygdx.roguelikeproject.world.GameMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private Player player;
    private GameMap mockGameMap;

    @Before
    public void setUp() {
        mockGameMap = mock(GameMap.class);
        when(mockGameMap.isWall(anyInt(), anyInt())).thenReturn(false);
        player = new Player(mockGameMap);
    }

    @Test
    public void testPlayerHealing() {
        player.takeDamage(50);
        player.heal(30);
        assertEquals(Constants.PLAYER_HEALTH - 20, player.getDamageable().getCurrentHealth());
    }

    @Test
    public void testPlayerDeath() {
        player.takeDamage(Constants.PLAYER_HEALTH);
        assertTrue(player.isDead());
    }

    @Test
    public void testSpeedResetAfterBoost() {
        player.setSpeedModifier(2f);
        player.scheduleSpeedReset(0.1f);
        player.handleInput(0.2f, new ArrayList<>());
        assertEquals(Constants.PLAYER_BASE_SPEED, player.getSpeed().get(), 0.01f);
    }

    @Test
    public void testProjectileBoostEnds() {
        player.boostProjectileFiring(0.1f);
        player.handleInput(0.2f, new ArrayList<>());
        assertFalse(player.isInvincible());
    }
}
