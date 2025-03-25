// 📁 Fichier : utils/Health.java
package com.mygdx.roguelikeproject.utils;

public class Health {
    private int max;
    private int current;

    public Health(int max) {
        this.max = max;
        this.current = max;
    }

    public void takeDamage(int amount) {
        current = Math.max(0, current - amount);
    }

    public void heal(int amount) {
        current = Math.min(max, current + amount);
    }

    public int getCurrent() { return current; }
    public int getMax() { return max; }
    public float getRatio() {
        return (float) current / max;
    }
    public boolean isDead() { return current <= 0; }
}
