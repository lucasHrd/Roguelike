// 📁 screens/GameScreen.java
package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.RoguelikeProject;
import com.mygdx.roguelikeproject.entities.EnemyBase;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.Projectile;
import com.mygdx.roguelikeproject.managers.BasicWave;
import com.mygdx.roguelikeproject.managers.WaveManager;
import com.mygdx.roguelikeproject.utils.Constants;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.world.GameMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    private final RoguelikeProject game;
    private SpriteBatch batch;
    private Player player;
    private List<Projectile> projectiles;
    private List<EnemyBase> enemies;
    private GameMap gameMap;
    private WaveManager waveManager;
    private float elapsedTime;

    private boolean isPaused = false;

    // Pause menu images
    private Texture pauseBackground;
    private Texture resumeButton;
    private Texture restartButton;
    private Texture menuButton;

    // Boutons
    private float resumeX, resumeY, resumeWidth, resumeHeight;
    private float restartX, restartY, restartWidth, restartHeight;
    private float menuX, menuY, menuWidth, menuHeight;

    public GameScreen(RoguelikeProject game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        projectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        gameMap = new GameMap();
        player = new Player(gameMap);
        waveManager = new BasicWave(player);

        elapsedTime = 0f;

        // Chargement images pause
        pauseBackground = new Texture("assets/black_screen.jpg"); // petit fond noir semi-transparent
        resumeButton = new Texture("assets/jouer2.jpg");
        restartButton = new Texture("assets/replay.png");
        menuButton = new Texture("assets/menu.png");

        // Position et taille des boutons
        resumeWidth = 200;
        resumeHeight = 60;
        resumeX = Gdx.graphics.getWidth() / 2f - resumeWidth / 2;
        resumeY = Gdx.graphics.getHeight() / 2f + 80;

        restartWidth = 200;
        restartHeight = 60;
        restartX = Gdx.graphics.getWidth() / 2f - restartWidth / 2;
        restartY = Gdx.graphics.getHeight() / 2f;

        menuWidth = 200;
        menuHeight = 60;
        menuX = Gdx.graphics.getWidth() / 2f - menuWidth / 2;
        menuY = Gdx.graphics.getHeight() / 2f - 80;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        if (player.getDamageable().isDead()) {
            game.setScreen(new DeathScreen(game, elapsedTime));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isPaused) {
            player.handleInput(delta, projectiles);
            waveManager.update(delta, enemies);
            updateProjectiles(delta);
            updateEnemies(delta);
            checkEnemyCollisions();
        }

        batch.begin();
        gameMap.render(batch);
        player.draw(batch);
        for (Projectile projectile : projectiles) {
            projectile.draw(batch);
        }
        for (EnemyBase enemy : enemies) {
            enemy.draw(batch);
        }
        batch.end();

        player.drawHealthBarCentered();

        if (isPaused) {
            renderPauseMenu();
            handlePauseInput();
        }
    }

    private void renderPauseMenu() {
        batch.begin();
        batch.draw(pauseBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(resumeButton, resumeX, resumeY, resumeWidth, resumeHeight);
        batch.draw(restartButton, restartX, restartY, restartWidth, restartHeight);
        batch.draw(menuButton, menuX, menuY, menuWidth, menuHeight);
        batch.end();
    }

    private void handlePauseInput() {
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // inverser Y

            if (mouseX >= resumeX && mouseX <= resumeX + resumeWidth &&
                mouseY >= resumeY && mouseY <= resumeY + resumeHeight) {
                isPaused = false;
            }
            else if (mouseX >= restartX && mouseX <= restartX + restartWidth &&
                mouseY >= restartY && mouseY <= restartY + restartHeight) {
                game.setScreen(new GameScreen(game));
            }
            else if (mouseX >= menuX && mouseX <= menuX + menuWidth &&
                mouseY >= menuY && mouseY <= menuY + menuHeight) {
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    private void updateProjectiles(float deltaTime) {
        Iterator<Projectile> projectileIter = projectiles.iterator();
        while (projectileIter.hasNext()) {
            Projectile projectile = projectileIter.next();
            projectile.update(deltaTime);

            if (projectile.isOutOfBounds()) {
                projectileIter.remove();
                continue;
            }

            Iterator<EnemyBase> enemyIter = enemies.iterator();
            while (enemyIter.hasNext()) {
                EnemyBase enemy = enemyIter.next();
                if (projectile.getHitbox().overlaps(enemy.getHitbox())) {
                    enemy.takeDamage(Constants.PROJECTILE_DAMAGE);
                    projectileIter.remove();
                    if (enemy.isDead()) {
                        enemyIter.remove();
                    }
                    break;
                }
            }
        }
    }

    private void updateEnemies(float deltaTime) {
        for (EnemyBase enemy : enemies) {
            enemy.update(deltaTime);
        }
    }

    private void checkEnemyCollisions() {
        Hitbox playerHitbox = new Hitbox(player.getX(), player.getY(), 32, 32);
        for (EnemyBase enemy : enemies) {
            if (playerHitbox.overlaps(enemy.getHitbox()) && !player.getDamageable().isInvincible()) {
                player.takeDamage(Constants.ENEMY_CONTACT_DAMAGE);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        pauseBackground.dispose();
        resumeButton.dispose();
        restartButton.dispose();
        menuButton.dispose();
    }
}
