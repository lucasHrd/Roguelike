package com.mygdx.roguelikeproject.entities.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.entities.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ItemManager {

    private final List<ItemBase> items;
    private final Random random;
    private final Player player;
    private float spawnTimer;

    public ItemManager(Player player) {
        this.player = player;
        this.items = new ArrayList<>();
        this.random = new Random();
        this.spawnTimer = 30f;
    }

    public void update(float deltaTime) {
        spawnTimer -= deltaTime;
        if (spawnTimer <= 0f) {
            spawnItem();
            spawnTimer = 30f;
        }

        Iterator<ItemBase> iterator = items.iterator();
        while (iterator.hasNext()) {
            ItemBase item = iterator.next();
            item.update(deltaTime);
            if (item.shouldBeRemoved()) {
                iterator.remove();
            }
        }
    }

    private void spawnItem() {
        int itemType = random.nextInt(3);

        ItemBase item;
        float x = random.nextFloat() * (Gdx.graphics.getWidth() - 32);
        float y = random.nextFloat() * (Gdx.graphics.getHeight() - 32);

        switch (itemType) {
            case 0 -> item = new HealthBoost(x, y);
            case 1 -> item = new SpeedBoost(x, y);
            case 2 -> item = new ProjectileBoost(x, y);
            default -> throw new IllegalStateException("Unexpected value: " + itemType);
        }

        items.add(item);
    }

    public void draw(SpriteBatch batch) {
        for (ItemBase item : items) {
            item.draw(batch);
        }
    }

    public void checkPlayerCollision(Player player) {
        Iterator<ItemBase> iterator = items.iterator();
        while (iterator.hasNext()) {
            ItemBase item = iterator.next();
            if (item.getHitbox().overlaps(player.getHitbox()) && !item.isCollected()) {
                item.applyEffect(player);
                iterator.remove();
            }
        }
    }
}
