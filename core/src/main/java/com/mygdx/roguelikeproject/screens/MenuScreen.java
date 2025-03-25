package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.RoguelikeProject;

public class MenuScreen implements Screen {

    private final RoguelikeProject game;
    private SpriteBatch batch;

    private Texture jouerBtn;
    private Texture quitterBtn;

    private float jouerX, jouerY;
    private float quitterX, quitterY;

    public MenuScreen(RoguelikeProject game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        jouerBtn = new Texture("assets/jouer.png");
        quitterBtn = new Texture("assets/quitter.png");

        // Centrage horizontal, espacement vertical
        jouerX = Gdx.graphics.getWidth() / 2f - jouerBtn.getWidth() / 2f;
        jouerY = Gdx.graphics.getHeight() / 2f + 50;

        quitterX = Gdx.graphics.getWidth() / 2f - quitterBtn.getWidth() / 2f;
        quitterY = Gdx.graphics.getHeight() / 2f - 50;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(jouerBtn, jouerX, jouerY);
        batch.draw(quitterBtn, quitterX, quitterY);
        batch.end();

        // Clic gauche détecté
        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // conversion coord écran

            // Bouton Jouer
            if (mouseX >= jouerX && mouseX <= jouerX + jouerBtn.getWidth()
                && mouseY >= jouerY && mouseY <= jouerY + jouerBtn.getHeight()) {
                game.startGame();
            }

            // Bouton Quitter
            if (mouseX >= quitterX && mouseX <= quitterX + quitterBtn.getWidth()
                && mouseY >= quitterY && mouseY <= quitterY + quitterBtn.getHeight()) {
                Gdx.app.exit();
            }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        jouerBtn.dispose();
        quitterBtn.dispose();
    }
}
