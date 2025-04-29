package com.mygdx.roguelikeproject.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class MovementSpeedTest {

    @Test
    public void testInitialSpeed() {
        MovementSpeed speed = new MovementSpeed(200f);
        assertEquals(200f, speed.get(), 0.01f);
    }

    @Test
    public void testSpeedModifier() {
        MovementSpeed speed = new MovementSpeed(200f);
        speed.setModifier(1.5f); // 50% plus rapide
        assertEquals(300f, speed.get(), 0.01f);
    }

    @Test
    public void testResetModifier() {
        MovementSpeed speed = new MovementSpeed(200f);
        speed.setModifier(1.5f);
        speed.resetModifier();
        assertEquals(200f, speed.get(), 0.01f);
    }
}
