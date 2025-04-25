// 📁 Fichier : screens/GameScreen.java
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

    // Variable qui gère l'état de pause
    private boolean isPaused = false;
    // Bouton pour reprendre la partie
    private Texture resumeBtn;
    private float resumeX, resumeY;

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

        // Chargement de l'image "Reprendre" et calcul de ses coordonnées centrées
        resumeBtn = new Texture("assets/jouer2.jpg");
        resumeX = Gdx.graphics.getWidth() / 2f - resumeBtn.getWidth() / 2f;
        resumeY = Gdx.graphics.getHeight() / 2f - resumeBtn.getHeight() / 2f;
        elapsedTime = 0f;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        if (player.getDamageable().isDead()) {
            game.setScreen(new DeathScreen(game, elapsedTime));
            return;
        }

        // Détection de la touche Échap pour basculer l'état de pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Mise à jour du jeu uniquement si on n'est pas en pause
        if (!isPaused) {
            player.handleInput(delta, projectiles);
            waveManager.update(delta, enemies);
            updateProjectiles(delta);
            updateEnemies(delta);
            checkEnemyCollisions();
        }

        // Affichage
        batch.begin();
        gameMap.render(batch);
        player.draw(batch);
        for (Projectile projectile : projectiles) {
            projectile.draw(batch);
        }
        for (EnemyBase enemy : enemies) {
            enemy.draw(batch);
        }
        // Si le jeu est en pause, affiche le bouton "Reprendre"
        if (isPaused) {
            batch.draw(resumeBtn, resumeX, resumeY);
        }
        batch.end();

        player.drawHealthBarCentered();

        // Si en pause et que l'on clique, vérifie si le bouton "Reprendre" a été cliqué
        if (isPaused && Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= resumeX && mouseX <= resumeX + resumeBtn.getWidth()
                && mouseY >= resumeY && mouseY <= resumeY + resumeBtn.getHeight()) {
                isPaused = false; // Reprend le jeu
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
        Hitbox playerHitbox = new Hitbox(player.getX(), player.getY(), 32, 32); // ajuster la taille si besoin
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
        resumeBtn.dispose();
    }
}
