// 📁 Fichier : entities/enemies/BlueDuckEnemy.java
package com.mygdx.roguelikeproject.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.roguelikeproject.entities.Player;
import com.mygdx.roguelikeproject.utils.Constants;
import com.mygdx.roguelikeproject.utils.Damageable;
import com.mygdx.roguelikeproject.utils.Hitbox;
import com.mygdx.roguelikeproject.utils.MovementSpeed;
import com.mygdx.roguelikeproject.utils.Position;

public class BlueDuckEnemy implements EnemyBase {

    private Position position;
    private final Player target;
    private final MovementSpeed speed;
    private final Damageable damageable;

    private final Texture textureLeft;
    private final Texture textureRight;
    private boolean facingRight = true;

    public BlueDuckEnemy(float x, float y, Player target) {
        this.position = new Position(x, y);
        this.target = target;
        this.speed = new MovementSpeed(Constants.ENEMY_BASE_SPEED);
        this.damageable = new Damageable(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_INVINCIBILITY_DURATION);
        this.textureLeft = new Texture("assets/blueduck1.png");
        this.textureRight = new Texture("assets/blueduck2.png");
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
        batch.draw(currentTexture, position.x(), position.y(), 32, 32);
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
        return new Hitbox(position.x(), position.y(), 32, 32);
    }
}
