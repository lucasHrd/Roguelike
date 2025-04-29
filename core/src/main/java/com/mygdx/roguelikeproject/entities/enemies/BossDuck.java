// 📁 Fichier : entities/enemies/BossDuck.java
package com.mygdx.roguelikeproject.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.utils.Constants;
import com.mygdx.roguelikeproject.utils.Damageable;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.utils.MovementSpeed;
import com.mygdx.roguelikeproject.utils.Position;

public class BossDuck implements EnemyBase {

    private Position position;
    private final Player target;
    private final MovementSpeed speed;
    private final Damageable damageable;

    private final Texture textureLeft;
    private final Texture textureRight;
    private boolean facingRight = true;

    private static final float WIDTH = 48;
    private static final float HEIGHT = 48;

    // ✅ Constructeur avec position personnalisée
    public BossDuck(float x, float y, Player target) {
        this.target = target;
        this.speed = new MovementSpeed(Constants.BOSS_SPEED);
        this.damageable = new Damageable(Constants.BOSS_MAX_HEALTH, Constants.BOSS_INVINCIBILITY_DURATION);
        this.textureLeft = new Texture("assets/bossduck1.png");
        this.textureRight = new Texture("assets/bossduck2.png");
        this.position = new Position(x, y);
    }

    // ✅ Constructeur centré automatiquement sur l’écran
    public BossDuck(Player target) {
        this(
            Gdx.graphics.getWidth() / 2f - WIDTH / 2f,
            Gdx.graphics.getHeight() / 2f - HEIGHT / 2f,
            target
        );
    }

    @Override
    public void update(float deltaTime) {
        damageable.update(deltaTime);

        float deltaX = target.getX() - position.x();
        float deltaY = target.getY() - position.y();
        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (length > 0) {
            float moveX = (deltaX / length) * speed.get() * deltaTime;
            float moveY = (deltaY / length) * speed.get() * deltaTime;
            position = new Position(position.x() + moveX, position.y() + moveY);

            facingRight = moveX >= 0;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        Texture currentTexture = facingRight ? textureRight : textureLeft;
        batch.draw(currentTexture, position.x(), position.y(), WIDTH, HEIGHT);
    }

    @Override
    public void takeDamage(int dmg) {
        damageable.takeDamage(dmg);
    }

    @Override
    public boolean isDead() {
        return damageable.isDead();
    }

    @Override
    public float getX() {
        return position.x();
    }

    @Override
    public float getY() {
        return position.y();
    }

    @Override
    public Hitbox getHitbox() {
        return new Hitbox(position.x(), position.y(), WIDTH, HEIGHT);
    }

    public Damageable getDamageable() {
        return damageable;
    }
}
