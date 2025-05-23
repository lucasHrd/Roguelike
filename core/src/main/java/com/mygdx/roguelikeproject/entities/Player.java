package com.mygdx.roguelikeproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.roguelikeproject.utils.*;
import com.mygdx.roguelikeproject.world.GameMap;

import java.util.List;

public class Player {

    private Position position;
    private Direction lastDirection;
    private float stateTime;

    private final GameMap gameMap;
    private final Damageable damageable;
    private final MovementSpeed speed;
    private final ShapeRenderer shapeRenderer;

    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;

    private final Texture walkUp1, walkUp2;
    private final Texture walkDown1, walkDown2;
    private final Texture walkLeft1, walkLeft2;
    private final Texture walkRight1, walkRight2;

    private float speedResetTimer = 0f;
    private boolean speedBoostActive = false;

    private boolean projectileBoostActive = false;
    private float projectileBoostTimer = 0f;

    public Player(GameMap gameMap) {
        this.gameMap = gameMap;
        this.damageable = new Damageable(Constants.PLAYER_HEALTH, Constants.PLAYER_INVINCIBILITY_DURATION);
        this.speed = new MovementSpeed(Constants.PLAYER_BASE_SPEED);
        this.position = new Position(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        this.lastDirection = Direction.UP;
        this.stateTime = 0f;
        this.shapeRenderer = new ShapeRenderer();

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

    public float getX() { return position.x(); }
    public float getY() { return position.y(); }
    public Damageable getDamageable() { return damageable; }
    public MovementSpeed getSpeed() { return speed; }

    public void heal(int amount) { damageable.heal(amount); }
    public void takeDamage(int dmg) { damageable.takeDamage(dmg); }
    public boolean isDead() { return damageable.isDead(); }
    public boolean isInvincible() { return damageable.isInvincible(); }

    public void setSpeedModifier(float modifier) {
        speed.setModifier(modifier);
    }

    public void scheduleSpeedReset(float delay) {
        speedResetTimer = delay;
        speedBoostActive = true;
    }

    public void boostProjectileFiring(float duration) {
        projectileBoostActive = true;
        projectileBoostTimer = duration;
    }

    public void setProjectileBoost(boolean boosted) {
        this.projectileBoostActive = boosted;
    }

    public void handleInput(float deltaTime, List<Projectile> projectiles) {
        boolean isMoving = false;
        float newX = position.x();
        float newY = position.y();
        float currentSpeed = speed.get();

        if (speedBoostActive) {
            speedResetTimer -= deltaTime;
            if (speedResetTimer <= 0f) {
                speed.resetModifier();
                speedBoostActive = false;
            }
        }

        if (projectileBoostActive) {
            projectileBoostTimer -= deltaTime;
            if (projectileBoostTimer <= 0f) {
                projectileBoostActive = false;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            newX -= currentSpeed * deltaTime;
            lastDirection = Direction.LEFT;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += currentSpeed * deltaTime;
            lastDirection = Direction.RIGHT;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.Z)) {
            newY += currentSpeed * deltaTime;
            lastDirection = Direction.UP;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= currentSpeed * deltaTime;
            lastDirection = Direction.DOWN;
            isMoving = true;
        }

        newX = Math.max(0, Math.min(newX, Gdx.graphics.getWidth() - 32));
        newY = Math.max(0, Math.min(newY, Gdx.graphics.getHeight() - 32));

        if (!gameMap.isWall((int) newX, (int) newY)) {
            position = new Position(newX, newY);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Projectile projectile = new Projectile(position.x(), position.y(), lastDirection);
            if (projectileBoostActive) {
                projectile.setBoosted(true);
            }
            projectiles.add(projectile);
        }

        stateTime = isMoving ? stateTime + deltaTime : 0;
        damageable.update(deltaTime);
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;
        switch (lastDirection) {
            case LEFT -> currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
            case RIGHT -> currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
            case UP -> currentFrame = walkUpAnimation.getKeyFrame(stateTime, true);
            default -> currentFrame = walkDownAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, position.x(), position.y());
    }

    public void drawHealthBarCentered() {
        float barWidth = Constants.HEALTHBAR_WIDTH;
        float barHeight = Constants.HEALTHBAR_HEIGHT;
        float x = (Gdx.graphics.getWidth() - barWidth) / 2f;
        float y = Constants.HEALTHBAR_Y_OFFSET;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.4f, 0, 0, 1);
        shapeRenderer.rect(x, y, barWidth, barHeight);

        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(x, y, barWidth * damageable.getHealthRatio(), barHeight);
        shapeRenderer.end();
    }

    public Hitbox getHitbox() {
        return new Hitbox(position.x(), position.y(), 32, 32);
    }

    public void dispose() {
        walkUp1.dispose(); walkUp2.dispose();
        walkDown1.dispose(); walkDown2.dispose();
        walkLeft1.dispose(); walkLeft2.dispose();
        walkRight1.dispose(); walkRight2.dispose();
        shapeRenderer.dispose();
    }
}
