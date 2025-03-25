package com.mygdx.roguelikeproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.List;

public class Player {
    private float x, y;
    private float speed = 150;
    private Direction lastDirection;
    private float stateTime;
    private GameMap gameMap; // Référence à la carte
    private int health;
    private int maxHealth = 100;
    private boolean isInvincible;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1.0f;
    private ShapeRenderer shapeRenderer;

    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private TextureRegion idleFrame;

    private Texture walkUp1, walkUp2, walkDown1, walkDown2, walkLeft1, walkLeft2, walkRight1, walkRight2;

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

        // Charger les textures
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

        idleFrame = new TextureRegion(walkDown1);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void handleInput(float deltaTime, List<Projectile> projectiles) {
        boolean isMoving = false;
        float newX = x;
        float newY = y;

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

        if (!gameMap.isWall((int)newX, (int)newY)) {
            x = newX;
            y = newY;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            projectiles.add(new Projectile(x, y, lastDirection));
        }

        if (isMoving) {
            stateTime += deltaTime;
        } else {
            stateTime = 0;
        }

        if (isInvincible) {
            invincibilityTimer -= deltaTime;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
            }
        }
    }

    public void takeDamage(int damage) {
        if (!isInvincible) {
            health -= damage;
            if (health < 0) health = 0;
            isInvincible = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
        }
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;
        switch (lastDirection) {
            case LEFT:
                currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
                break;
            case UP:
                currentFrame = walkUpAnimation.getKeyFrame(stateTime, true);
                break;
            case DOWN:
            default:
                currentFrame = walkDownAnimation.getKeyFrame(stateTime, true);
                break;
        }
        batch.draw(currentFrame, x, y);
    }

    public void drawHealthBar(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(x - 10, y + 40, 50, 5);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(x - 10, y + 40, (50 * health) / maxHealth, 5);
        shapeRenderer.end();
    }

    public void dispose() {
        walkUp1.dispose();
        walkUp2.dispose();
        walkDown1.dispose();
        walkDown2.dispose();
        walkLeft1.dispose();
        walkLeft2.dispose();
        walkRight1.dispose();
        walkRight2.dispose();
        shapeRenderer.dispose();
    }
}
