package com.mygdx.roguelikeproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.roguelikeproject.world.GameMap;

import java.util.List;

public class Player {
    private float x, y;
    private final float speed = 150f;
    private Direction lastDirection;
    private float stateTime;
    private final GameMap gameMap;
    private int health;
    private final int maxHealth = 100;
    private boolean isInvincible;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1f;

    private final ShapeRenderer shapeRenderer;

    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;

    private final Texture walkUp1, walkUp2, walkDown1, walkDown2, walkLeft1, walkLeft2, walkRight1, walkRight2;

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public Player(GameMap gameMap) {
        this.gameMap = gameMap;
        x = Gdx.graphics.getWidth() / 2f;
        y = Gdx.graphics.getHeight() / 2f;
        lastDirection = Direction.UP;
        stateTime = 0;
        health = maxHealth;
        isInvincible = false;
        invincibilityTimer = 0;
        shapeRenderer = new ShapeRenderer();

        walkUp1 = new Texture("assets/walk_up1.png");
        walkUp2 = new Texture("assets/walk_up2.png");
        walkDown1 = new Texture("assets/walk_down1.png");
        walkDown2 = new Texture("assets/walk_down2.png");
        walkLeft1 = new Texture("assets/walk_left1.png");
        walkLeft2 = new Texture("assets/walk_left2.png");
        walkRight1 = new Texture("assets/walk_right1.png");
        walkRight2 = new Texture("assets/walk_right2.png");

        walkUpAnimation = new Animation<>(0.3f, new TextureRegion(walkUp1), new TextureRegion(walkUp2));
        walkDownAnimation = new Animation<>(0.3f, new TextureRegion(walkDown1), new TextureRegion(walkDown2));
        walkLeftAnimation = new Animation<>(0.3f, new TextureRegion(walkLeft1), new TextureRegion(walkLeft2));
        walkRightAnimation = new Animation<>(0.3f, new TextureRegion(walkRight1), new TextureRegion(walkRight2));
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void handleInput(float deltaTime, List<Projectile> projectiles) {
        boolean isMoving = false;
        float newX = x, newY = y;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            newX -= speed * deltaTime;
            lastDirection = Direction.LEFT;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += speed * deltaTime;
            lastDirection = Direction.RIGHT;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.Z)) {
            newY += speed * deltaTime;
            lastDirection = Direction.UP;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= speed * deltaTime;
            lastDirection = Direction.DOWN;
            isMoving = true;
        }

        if (!gameMap.isWall((int) newX, (int) newY)) {
            x = newX;
            y = newY;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            projectiles.add(new Projectile(x, y, lastDirection));
        }

        stateTime = isMoving ? stateTime + deltaTime : 0;

        if (isInvincible) {
            invincibilityTimer -= deltaTime;
            if (invincibilityTimer <= 0) isInvincible = false;
        }
    }

    public void takeDamage(int damage) {
        if (!isInvincible) {
            health -= damage;
            health = Math.max(0, health);
            isInvincible = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
        }
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;
        switch (lastDirection) {
            case LEFT -> currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
            case RIGHT -> currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
            case UP -> currentFrame = walkUpAnimation.getKeyFrame(stateTime, true);
            case DOWN -> currentFrame = walkDownAnimation.getKeyFrame(stateTime, true);
            default -> currentFrame = new TextureRegion(walkDown1);
        }
        batch.draw(currentFrame, x, y);
    }

    public void drawHealthBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(x - 10, y + 40, 50, 5);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(x - 10, y + 40, (50f * health) / maxHealth, 5);
        shapeRenderer.end();
    }

    public void dispose() {
        walkUp1.dispose(); walkUp2.dispose(); walkDown1.dispose(); walkDown2.dispose();
        walkLeft1.dispose(); walkLeft2.dispose(); walkRight1.dispose(); walkRight2.dispose();
        shapeRenderer.dispose();
    }
}
