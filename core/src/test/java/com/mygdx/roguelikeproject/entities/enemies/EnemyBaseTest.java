package com.mygdx.roguelikeproject.entities.enemies;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EnemyBaseTest {

    private EnemyBase enemy;

    @Before
    public void setUp() {
        enemy = mock(EnemyBase.class);
        when(enemy.isDead()).thenReturn(false);
    }

    @Test
    public void testEnemyTakesDamage() {
        doNothing().when(enemy).takeDamage(anyInt());
        enemy.takeDamage(10);
        verify(enemy, times(1)).takeDamage(10);
    }

    @Test
    public void testEnemyDies() {
        when(enemy.isDead()).thenReturn(true);
        assertTrue(enemy.isDead());
    }
}
