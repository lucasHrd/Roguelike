package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.roguelikeproject.RoguelikeProject;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.entities.Projectile;
import com.mygdx.roguelikeproject.entities.enemies.EnemyBase;
import com.mygdx.roguelikeproject.entities.items.ItemManager;
import com.mygdx.roguelikeproject.managers.WaveController;
import com.mygdx.roguelikeproject.utils.Constants;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.utils.Timer;
import com.mygdx.roguelikeproject.world.GameMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    private final RoguelikeProject game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private List<Projectile> projectiles;
    private List<EnemyBase> enemies;
    private GameMap gameMap;
    private WaveController waveController;
    private ItemManager itemManager;
    private float elapsedTime;
    private boolean isPaused = false;
    private Texture resumeBtn, menuBtn, quitBtn, background;
    private BitmapFont font;
    private Timer timer;

    private float buttonWidth = 150;
    private float buttonHeight = 60;
    private float startX, btnY;

    public GameScreen(RoguelikeProject game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(2f);

        projectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        gameMap = new GameMap();
        player = new Player(gameMap);
        waveController = new WaveController(player);
        itemManager = new ItemManager(player);
        timer = new Timer();

        background = new Texture("assets/game_background2.png");
        resumeBtn = new Texture("assets/jouer.png");
        menuBtn = new Texture("assets/menu.png");
        quitBtn = new Texture("assets/quitter.png");

        float totalWidth = buttonWidth * 3 + 40 * 2; // 3 boutons + 2 espacements
        startX = (Gdx.graphics.getWidth() - totalWidth) / 2f;
        btnY = Gdx.graphics.getHeight() / 2f - buttonHeight / 2f;

        elapsedTime = 0f;
    }

    @Override
    public void render(float delta) {
        if (player.isDead()) {
            game.setScreen(new DeathScreen(game, timer.getTime()));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isPaused) {
            timer.update(delta);
            player.handleInput(delta, projectiles);
            waveController.update(delta, enemies);
            updateProjectiles(delta);
            updateEnemies(delta);
            checkEnemyCollisions();
            itemManager.update(delta);
        }

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameMap.render(batch);
        player.draw(batch);

        for (Projectile projectile : projectiles) {
            projectile.draw(batch);
        }
        for (EnemyBase enemy : enemies) {
            enemy.draw(batch);
        }
        itemManager.draw(batch);

        font.draw(batch, timer.getFormattedTime(), Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 20);
        batch.end();

        if (waveController.isBossFight()) {
            drawBossHealthBar();
        }

        player.drawHealthBarCentered();

        if (!isPaused) {
            itemManager.checkPlayerCollision(player);
        }

        if (isPaused) {
            drawPauseMenu();
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
        int totalDamage = 0;

        for (EnemyBase enemy : enemies) {
            if (playerHitbox.overlaps(enemy.getHitbox()) && !player.isInvincible()) {
                totalDamage += Constants.ENEMY_CONTACT_DAMAGE;
            }
        }

        if (totalDamage > 0) {
            player.takeDamage(totalDamage);
        }
    }

    private void drawPauseMenu() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        batch.draw(resumeBtn, startX, btnY, buttonWidth, buttonHeight);
        batch.draw(menuBtn, startX + buttonWidth + 40, btnY, buttonWidth, buttonHeight);
        batch.draw(quitBtn, startX + 2 * (buttonWidth + 40), btnY, buttonWidth, buttonHeight);
        batch.end();

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (isClicked(mouseX, mouseY, startX, btnY, buttonWidth, buttonHeight)) {
                isPaused = false;
            } else if (isClicked(mouseX, mouseY, startX + buttonWidth + 40, btnY, buttonWidth, buttonHeight)) {
                game.returnToMenu();
            } else if (isClicked(mouseX, mouseY, startX + 2 * (buttonWidth + 40), btnY, buttonWidth, buttonHeight)) {
                Gdx.app.exit();
            }
        }
    }

    private boolean isClicked(float mx, float my, float x, float y, float width, float height) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }

    private void drawBossHealthBar() {
        float barWidth = Constants.BOSS_HEALTHBAR_WIDTH;
        float barHeight = Constants.BOSS_HEALTHBAR_HEIGHT;
        float x = (Gdx.graphics.getWidth() - barWidth) / 2f;
        float y = Gdx.graphics.getHeight() - Constants.BOSS_HEALTHBAR_Y_OFFSET;

        float healthRatio = waveController.getBoss().getDamageable().getHealthRatio();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.4f, 0f, 0f, 1f);
        shapeRenderer.rect(x, y, barWidth, barHeight);

        shapeRenderer.setColor(0f, 1f, 0f, 1f);
        shapeRenderer.rect(x, y, barWidth * healthRatio, barHeight);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        resumeBtn.dispose();
        menuBtn.dispose();
        quitBtn.dispose();
        font.dispose();
        shapeRenderer.dispose();
        gameMap.dispose();
    }
}
