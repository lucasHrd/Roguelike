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
    private Texture background;

    // Paramètres pour les boutons
    private float btnWidth = 150;
    private float btnHeight = 60;
    private float btnSpacing = 20;
    private float startX;
    private float btnY;

    public DeathScreen(RoguelikeProject game, float timeSurvived) {
        this.game = game;
        this.timeSurvived = timeSurvived;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();

        background = new Texture("assets/death_screen.jpeg");
        rejouerBtn = new Texture("assets/replay.png");
        accueilBtn = new Texture("assets/menu.png");
        quitterBtn = new Texture("assets/quitter2.jpg");

        // Calcul des positions pour aligner les boutons horizontalement
        float totalWidth = 3 * btnWidth + 2 * btnSpacing;
        startX = (Gdx.graphics.getWidth() - totalWidth) / 2f;
        btnY = 50; // Bas de l'écran
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
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, "Temps de survie : " + (int) timeSurvived + "s",
            Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() - 80);

        // Dessiner les boutons alignés
        batch.draw(rejouerBtn, startX, btnY, btnWidth, btnHeight);
        batch.draw(accueilBtn, startX + btnWidth + btnSpacing, btnY, btnWidth, btnHeight);
        batch.draw(quitterBtn, startX + 2 * (btnWidth + btnSpacing), btnY, btnWidth, btnHeight);
        batch.end();

        // Gérer les clics
        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (isClicked(mouseX, mouseY, startX, btnY, btnWidth, btnHeight)) {
                game.startGame();
            } else if (isClicked(mouseX, mouseY, startX + btnWidth + btnSpacing, btnY, btnWidth, btnHeight)) {
                game.returnToMenu();
            } else if (isClicked(mouseX, mouseY, startX + 2 * (btnWidth + btnSpacing), btnY, btnWidth, btnHeight)) {
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
        shapeRenderer.dispose();
        font.dispose();
        rejouerBtn.dispose();
        accueilBtn.dispose();
        quitterBtn.dispose();
        background.dispose();
    }
}
