package com.mygdx.roguelikeproject.utils;

import com.mygdx.roguelikeproject.utils.Damageable;
import org.junit.Test;
import static org.junit.Assert.*;

public class DamageableTest {

    @Test
    public void testInitialHealth() {
        Damageable damageable = new Damageable(100, 1.5f);
        assertEquals(100, damageable.getCurrentHealth());
        assertEquals(100, damageable.getMaxHealth());
    }

    @Test
    public void testTakeDamage() {
        Damageable damageable = new Damageable(100, 1.5f);
        damageable.takeDamage(30);
        assertEquals(70, damageable.getCurrentHealth());
    }

    @Test
    public void testHeal() {
        Damageable damageable = new Damageable(100, 1.5f);
        damageable.takeDamage(50);
        damageable.heal(20);
        assertEquals(70, damageable.getCurrentHealth());
    }

    @Test
    public void testDeath() {
        Damageable damageable = new Damageable(50, 1.5f);
        damageable.takeDamage(50);
        assertTrue(damageable.isDead());
    }

    @Test
    public void testInvincibility() {
        Damageable damageable = new Damageable(100, 1.5f);
        damageable.takeDamage(30);
        assertTrue(damageable.isInvincible());

        // Simuler le passage de 2 secondes
        damageable.update(2f);
        assertFalse(damageable.isInvincible());
    }

    @Test
    public void testHealthRatio() {
        Damageable damageable = new Damageable(100, 1.5f);
        damageable.takeDamage(25);
        assertEquals(0.75f, damageable.getHealthRatio(), 0.01f);
    }
}
