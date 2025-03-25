package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.*;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.Projectile;
import com.mygdx.roguelikeproject.world.GameMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    private final RoguelikeProject game;
    private SpriteBatch batch;
    private Player player;
    private List<Projectile> projectiles;
    private GameMap gameMap;

    public GameScreen(RoguelikeProject game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        projectiles = new ArrayList<>();
        gameMap = new GameMap();
        player = new Player(gameMap);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.handleInput(delta, projectiles);
        updateProjectiles(delta);

        batch.begin();
        gameMap.render(batch);
        player.draw(batch);
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
}
