// 📁 Fichier : screens/MenuScreen.java
package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.RoguelikeProject;

public class MenuScreen implements Screen {

    private final RoguelikeProject game;
    private SpriteBatch batch;
    private Texture background;
    private Texture jouerBtn;
    private Texture quitterBtn;

    private float jouerX, quitterX;
    private float jouerY, quitterY;
    private final float spacing = 30;
    private final float scaleX = 0.33f; // largeur réduite
    private final float scaleY = 0.25f; // hauteur encore plus réduite

    public MenuScreen(RoguelikeProject game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("assets/background.png");
        jouerBtn = new Texture("assets/jouer.png");
        quitterBtn = new Texture("assets/quitter.png");

        float jouerWidth = jouerBtn.getWidth() * scaleX;
        float quitterWidth = quitterBtn.getWidth() * scaleX;

        float totalWidth = jouerWidth + spacing + quitterWidth;
        float centerX = (Gdx.graphics.getWidth() - totalWidth) / 2f;

        jouerX = centerX;
        quitterX = centerX + jouerWidth + spacing;

        jouerY = quitterY = Gdx.graphics.getHeight() / 3f;
    }

    @Override
    public void render(float delta) {
        float jouerWidth = jouerBtn.getWidth() * scaleX;
        float jouerHeight = jouerBtn.getHeight() * scaleY;
        float quitterWidth = quitterBtn.getWidth() * scaleX;
        float quitterHeight = quitterBtn.getHeight() * scaleY;

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(jouerBtn, jouerX, jouerY, jouerWidth, jouerHeight);
        batch.draw(quitterBtn, quitterX, quitterY, quitterWidth, quitterHeight);
        batch.end();

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (isClicked(mouseX, mouseY, jouerX, jouerY, jouerWidth, jouerHeight)) {
                game.startGame();
            } else if (isClicked(mouseX, mouseY, quitterX, quitterY, quitterWidth, quitterHeight)) {
                Gdx.app.exit();
            }
        }
    }

    private boolean isClicked(float mx, float my, float x, float y, float width, float height) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        jouerBtn.dispose();
        quitterBtn.dispose();
    }
}
