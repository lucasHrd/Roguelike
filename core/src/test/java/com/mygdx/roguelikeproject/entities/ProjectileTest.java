package com.mygdx.roguelikeproject.entities;

import com.mygdx.roguelikeproject.utils.Direction;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProjectileTest {

    @Test
    public void testInitialization() {
        Projectile projectile = new Projectile(100, 100, Direction.RIGHT);

        assertEquals(100, projectile.getX(), 0.001f);
        assertEquals(100, projectile.getY(), 0.001f);
    }

    @Test
    public void testMovementRight() {
        Projectile projectile = new Projectile(0, 0, Direction.RIGHT);
        projectile.update(1f);

        assertTrue(projectile.getX() > 0);
        assertEquals(0, projectile.getY(), 0.001f);
    }

    @Test
    public void testMovementLeft() {
        Projectile projectile = new Projectile(100, 0, Direction.LEFT);
        projectile.update(1f);

        assertTrue(projectile.getX() < 100);
        assertEquals(0, projectile.getY(), 0.001f);
    }

    @Test
    public void testMovementUp() {
        Projectile projectile = new Projectile(0, 0, Direction.UP);
        projectile.update(1f);

        assertEquals(0, projectile.getX(), 0.001f);
        assertTrue(projectile.getY() > 0);
    }

    @Test
    public void testMovementDown() {
        Projectile projectile = new Projectile(0, 100, Direction.DOWN);
        projectile.update(1f);

        assertEquals(0, projectile.getX(), 0.001f);
        assertTrue(projectile.getY() < 100);
    }

    @Test
    public void testBoostedSpeed() {
        Projectile projectile = new Projectile(0, 0, Direction.RIGHT);
        projectile.setBoosted(true);

        float beforeX = projectile.getX();
        projectile.update(1f);
        float afterX = projectile.getX();

        assertTrue(afterX - beforeX > com.mygdx.roguelikeproject.utils.Constants.PROJECTILE_SPEED);
    }
}
