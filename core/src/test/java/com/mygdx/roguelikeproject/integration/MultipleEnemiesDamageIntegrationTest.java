package com.mygdx.roguelikeproject.integration;

import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import com.mygdx.roguelikeproject.utils.Constants;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.world.GameMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MultipleEnemiesDamageIntegrationTest {

    private Player player;
    private List<EnemyBase> enemies;

    @Before
    public void setUp() {
        GameMap mockGameMap = mock(GameMap.class);
        when(mockGameMap.isWall(anyInt(), anyInt())).thenReturn(false);

        player = new Player(mockGameMap);
        enemies = new ArrayList<>();
    }

    @Test
    public void testPlayerTakesDamageFromMultipleEnemies() {
        // Créer 2 ennemis simulés qui touchent le joueur
        EnemyBase enemy1 = mock(EnemyBase.class);
        EnemyBase enemy2 = mock(EnemyBase.class);

        Hitbox overlappingHitbox = player.getHitbox();

        when(enemy1.getHitbox()).thenReturn(overlappingHitbox);
        when(enemy2.getHitbox()).thenReturn(overlappingHitbox);

        enemies.add(enemy1);
        enemies.add(enemy2);

        int initialHealth = player.getDamageable().getCurrentHealth();

        // Simuler les collisions (comme dans GameScreen)
        Hitbox playerHitbox = player.getHitbox();
        for (EnemyBase enemy : enemies) {
            if (playerHitbox.overlaps(enemy.getHitbox()) && !player.isInvincible()) {
                player.takeDamage(Constants.ENEMY_CONTACT_DAMAGE);
            }
        }

        // Vérifier que le joueur a pris les dégâts des deux ennemis
        int expectedHealth = initialHealth - (Constants.ENEMY_CONTACT_DAMAGE * 2);
        assertEquals(expectedHealth, player.getDamageable().getCurrentHealth());
    }
}
