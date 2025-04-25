package com.mygdx.roguelikeproject.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMap {
    private static final int TILE_SIZE = 32;
    private static final int MAP_WIDTH = 25;  // 800 / 32 = 25
    private static final int MAP_HEIGHT = 19; // 600 / 32 = 19

    private final int[][] map = new int[MAP_HEIGHT][MAP_WIDTH];
    private final Texture wallTexture;
    private final Texture floorTexture;

    public GameMap() {
        wallTexture = new Texture("assets/stone1.png");
        floorTexture = new Texture("assets/black_screen.jpg");
        generateMap();
    }

    private void generateMap() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                map[y][x] = (x == 0 || y == 0 || x == MAP_WIDTH - 1 || y == MAP_HEIGHT - 1) ? 1 : 0;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                int drawX = x * TILE_SIZE;
                int drawY = y * TILE_SIZE;

                if (map[y][x] == 1) {
                    batch.draw(wallTexture, drawX, drawY);
                } else {
                    batch.draw(floorTexture, drawX, drawY);
                }
            }
        }

    }

    public boolean isWall(int x, int y) {
        int tileX = x / TILE_SIZE;
        int tileY = y / TILE_SIZE;

        if (tileX < 0 || tileY < 0 || tileX >= MAP_WIDTH || tileY >= MAP_HEIGHT) {
            return true;
        }
        return map[tileY][tileX] == 1;
    }

    public void dispose() {
        wallTexture.dispose();
        floorTexture.dispose();
    }
}
