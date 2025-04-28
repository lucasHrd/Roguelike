package com.mygdx.roguelikeproject.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class PositionTest {

    @Test
    public void testPositionValues() {
        Position position = new Position(100f, 200f);

        assertEquals(100f, position.x(), 0.001f);
        assertEquals(200f, position.y(), 0.001f);
    }

    @Test
    public void testNegativeValues() {
        Position position = new Position(-50f, -75f);

        assertEquals(-50f, position.x(), 0.001f);
        assertEquals(-75f, position.y(), 0.001f);
    }

    @Test
    public void testZeroValues() {
        Position position = new Position(0f, 0f);

        assertEquals(0f, position.x(), 0.001f);
        assertEquals(0f, position.y(), 0.001f);
    }
}
