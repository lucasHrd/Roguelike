// 📁 Fichier : screens/DeathScreen.java
package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.roguelikeproject.RoguelikeProject;

public class DeathScreen implements Screen {

    private final RoguelikeProject game;
    private final float timeSurvived;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private Texture rejouerBtn;
    private Texture accueilBtn;
    private Texture quitterBtn;

    private float centerX, startY;
    private float spacing = 20;

    public DeathScreen(RoguelikeProject game, float timeSurvived) {
        this.game = game;
        this.timeSurvived = timeSurvived;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();

        rejouerBtn = new Texture("assets/rejouer.png");
        accueilBtn = new Texture("assets/accueil.png");
        quitterBtn = new Texture("assets/quitter.png");

        centerX = Gdx.graphics.getWidth() / 2f - rejouerBtn.getWidth() / 2f;
        startY = Gdx.graphics.getHeight() / 2f + rejouerBtn.getHeight() + spacing;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Temps de survie : " + (int) timeSurvived + "s", centerX, startY + 100);
        batch.draw(rejouerBtn, centerX, startY);
        batch.draw(accueilBtn, centerX, startY - rejouerBtn.getHeight() - spacing);
        batch.draw(quitterBtn, centerX, startY - 2 * (rejouerBtn.getHeight() + spacing));
        batch.end();

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (isClicked(mouseX, mouseY, centerX, startY, rejouerBtn)) {
                game.startGame();
            } else if (isClicked(mouseX, mouseY, centerX, startY - rejouerBtn.getHeight() - spacing, accueilBtn)) {
                game.returnToMenu();
            } else if (isClicked(mouseX, mouseY, centerX, startY - 2 * (rejouerBtn.getHeight() + spacing), quitterBtn)) {
                Gdx.app.exit();
            }
        }
    }

    private boolean isClicked(float mx, float my, float x, float y, Texture btn) {
        return mx >= x && mx <= x + btn.getWidth() && my >= y && my <= y + btn.getHeight();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        rejouerBtn.dispose();
        accueilBtn.dispose();
        quitterBtn.dispose();
    }
}
