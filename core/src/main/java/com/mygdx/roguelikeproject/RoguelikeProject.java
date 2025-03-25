package com.mygdx.roguelikeproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoguelikeProject extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private List<Projectile> projectiles;
    private GameMap gameMap; // Ajout de la map

    @Override
    public void create() {
        batch = new SpriteBatch();
        projectiles = new ArrayList<>();
        gameMap = new GameMap();
        player = new Player(gameMap);

    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        player.handleInput(deltaTime, projectiles);
        updateProjectiles(deltaTime);

        ScreenUtils.clear(0, 0, 0, 1); // Nettoyage de l'écran

        batch.begin();
        gameMap.render(batch); // Affichage de la map
        player.draw(batch); // Affichage du joueur
        for (Projectile projectile : projectiles) {
            projectile.draw(batch);
        }
        batch.end();
    }

    private void updateProjectiles(float deltaTime) {
        Iterator<Projectile> iter = projectiles.iterator();
        while (iter.hasNext()) {
            Projectile projectile = iter.next();
            projectile.update(deltaTime);
            if (projectile.isOutOfBounds()) {
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Roguelike Project");
        config.setWindowedMode(800,600);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new RoguelikeProject(), config);
    }
}
