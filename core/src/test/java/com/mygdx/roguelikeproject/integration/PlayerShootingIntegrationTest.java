package com.mygdx.roguelikeproject.integration;

import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.Projectile;
import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import com.mygdx.roguelikeproject.utils.Direction;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.world.GameMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerShootingIntegrationTest {

    private Player player;
    private EnemyBase enemy;
    private List<Projectile> projectiles;

    @Before
    public void setUp() {
        GameMap mockGameMap = mock(GameMap.class);
        when(mockGameMap.isWall(anyInt(), anyInt())).thenReturn(false);
        player = new Player(mockGameMap);

        enemy = mock(EnemyBase.class);
        when(enemy.getHitbox()).thenReturn(new Hitbox(player.getX() + 20, player.getY(), 32, 32));
        when(enemy.isDead()).thenReturn(false);

        projectiles = new ArrayList<>();
    }

    @Test
    public void testPlayerProjectileHitsEnemy() {
        Projectile projectile = new Projectile(player.getX(), player.getY(), Direction.RIGHT);
        projectiles.add(projectile);

        float deltaTime = 0.1f;

        for (Projectile p : projectiles) {
            p.update(deltaTime);
        }

        for (Projectile p : projectiles) {
            if (p.getHitbox().overlaps(enemy.getHitbox())) {
                enemy.takeDamage(10);
            }
        }

        verify(enemy, times(1)).takeDamage(10);
    }
}
