package com.mygdx.roguelikeproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.roguelikeproject.RoguelikeProject;
import com.mygdx.roguelikeproject.utils.BestScoreManager;

public class DeathScreen implements Screen {

    private final RoguelikeProject game;
    private final float timeSurvived;
    private final float bestScore;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private GlyphLayout layout;

    private Texture rejouerBtn;
    private Texture accueilBtn;
    private Texture quitterBtn;
    private Texture background;
    private Texture trophyTexture;

    private float btnWidth = 150;
    private float btnHeight = 60;
    private float btnSpacing = 20;
    private float startX;
    private float btnY;

    public DeathScreen(RoguelikeProject game, float timeSurvived) {
        this.game = game;
        this.timeSurvived = timeSurvived;
        this.bestScore = BestScoreManager.loadBestScore();
        BestScoreManager.saveBestScore(timeSurvived);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        layout = new GlyphLayout();

        font.getData().setScale(2f);

        background = new Texture("assets/death_background.png");
        rejouerBtn = new Texture("assets/jouer.png");
        accueilBtn = new Texture("assets/menu.png");
        quitterBtn = new Texture("assets/quitter.png");
        trophyTexture = new Texture("assets/trophy_2.png");

        float totalWidth = 3 * btnWidth + 2 * btnSpacing;
        startX = (Gdx.graphics.getWidth() - totalWidth) / 2f;
        btnY = 50;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        String timeText = "Temps de survie : " + formatTime(timeSurvived);
        String bestText = "RECORD  : " + formatTime(bestScore);

        layout.setText(font, timeText);
        float textX = (Gdx.graphics.getWidth() - layout.width) / 2f;

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, timeText, textX, Gdx.graphics.getHeight() - 200);
        font.draw(batch, bestText, textX + 10, Gdx.graphics.getHeight() - 250);

        // 🔥 Afficher le trophée à côté du meilleur temps
        batch.draw(trophyTexture,
            textX - 70, Gdx.graphics.getHeight() - 280,
            48, 48); // Taille du trophée forcée à 48x48 pixels propre

        batch.draw(rejouerBtn, startX, btnY, 250, 150);
        batch.draw(quitterBtn, startX +250, btnY, 250, 150);
        batch.end();

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (isClicked(mouseX, mouseY, startX, btnY, 250, 150)) {
                game.startGame();
            } else if (isClicked(mouseX, mouseY, startX + btnWidth + btnSpacing, btnY, btnWidth, btnHeight)) {
                game.returnToMenu();
            } else if (isClicked(mouseX, mouseY, startX +250, btnY, 250, 150)) {
                Gdx.app.exit();
            }
        }
    }

    private String formatTime(float time) {
        int totalSeconds = (int) time;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
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
        trophyTexture.dispose();
    }
}
