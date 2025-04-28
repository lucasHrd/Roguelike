package com.mygdx.roguelikeproject.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class TimerTest {

    @Test
    public void testInitialTime() {
        Timer timer = new Timer();
        assertEquals(0f, timer.getTime(), 0.01f);
    }

    @Test
    public void testTimeUpdate() {
        Timer timer = new Timer();
        timer.update(1.5f);
        assertEquals(1.5f, timer.getTime(), 0.01f);
    }

    @Test
    public void testMultipleUpdates() {
        Timer timer = new Timer();
        timer.update(1.5f);
        timer.update(2.5f);
        assertEquals(4f, timer.getTime(), 0.01f);
    }

    @Test
    public void testReset() {
        Timer timer = new Timer();
        timer.update(2f);
        timer.reset();
        assertEquals(0f, timer.getTime(), 0.01f);
    }
}
