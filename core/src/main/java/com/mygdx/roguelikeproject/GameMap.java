package com.mygdx.roguelikeproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMap {
    private static final int TILE_SIZE = 32;
    private static final int MAP_WIDTH = 25;  // 800 / 32 = 25
    private static final int MAP_HEIGHT = 19; // 600 / 32 = 19

    private int[][] map = new int[MAP_HEIGHT][MAP_WIDTH];
    private Texture wallTexture;
    private Texture floorTexture;

    public GameMap() {
        wallTexture = new Texture("C:\\Users\\mehdi\\Desktop\\L3\\genie logiciel\\RGLK_PRJKT\\assets\\stone1.png");
        floorTexture = new Texture("C:\\Users\\mehdi\\Desktop\\L3\\genie logiciel\\RGLK_PRJKT\\assets\\black_screen.jpg");
        generateMap();
    }

    private void generateMap() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (x == 0 || y == 0 || x == MAP_WIDTH - 1 || y == MAP_HEIGHT - 1) {
                    map[y][x] = 1; // Mur en bordure
                } else {
                    map[y][x] = 0; // Sol
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                int drawX = x * TILE_SIZE;
                int drawY = y * TILE_SIZE; // Correction ici, plus besoin d'inverser

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
        int tileY = y / TILE_SIZE; // Correction ici

        if (tileX < 0 || tileY < 0 || tileX >= MAP_WIDTH || tileY >= MAP_HEIGHT) {
            return true;
        }
        return map[tileY][tileX] == 1;
    }
}
