package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.RoguelikeProject;

public class PauseScreen extends ScreenAdapter {
    private final RoguelikeProject game;
    private SpriteBatch batch;

    private Texture pauseBtn;
    private Texture replayBtn;
    private Texture quitterBtn;

    private float pauseX, pauseY;
    private float replayX, replayY;
    private float quitterX, quitterY;

    private boolean isPaused = false;

    public PauseScreen(RoguelikeProject game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        pauseBtn = new Texture("assets/pause.png");
        replayBtn = new Texture("assets/replay.png");
        quitterBtn = new Texture("assets/quitter.png");

        pauseX = Gdx.graphics.getWidth() - pauseBtn.getWidth() - 20;
        pauseY = Gdx.graphics.getHeight() - pauseBtn.getHeight() - 20;

        replayX = Gdx.graphics.getWidth() / 2f - replayBtn.getWidth() / 2f;
        replayY = Gdx.graphics.getHeight() / 2f + 50;

        quitterX = Gdx.graphics.getWidth() / 2f - quitterBtn.getWidth() / 2f;
        quitterY = Gdx.graphics.getHeight() / 2f - 50;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Toujours afficher le bouton pause
        batch.draw(pauseBtn, pauseX, pauseY);

        // Si on est en pause, afficher les boutons replay et quitter
        if (isPaused) {
            batch.draw(replayBtn, replayX, replayY);
            batch.draw(quitterBtn, quitterX, quitterY);
        }

        batch.end();

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // conversion

            if (!isPaused) {
                // Cliqué sur pause ?
                if (mouseX >= pauseX && mouseX <= pauseX + pauseBtn.getWidth()
                    && mouseY >= pauseY && mouseY <= pauseY + pauseBtn.getHeight()) {
                    isPaused = true;
                }
            } else {
                // Cliqué sur replay ?
                if (mouseX >= replayX && mouseX <= replayX + replayBtn.getWidth()
                    && mouseY >= replayY && mouseY <= replayY + replayBtn.getHeight()) {
                    isPaused = false; // reprendre le jeu
                }

                // Cliqué sur quitter ?
                if (mouseX >= quitterX && mouseX <= quitterX + quitterBtn.getWidth()
                    && mouseY >= quitterY && mouseY <= quitterY + quitterBtn.getHeight()) {
                    game.setScreen(new MenuScreen(game));
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        pauseBtn.dispose();
        replayBtn.dispose();
        quitterBtn.dispose();
    }
}
