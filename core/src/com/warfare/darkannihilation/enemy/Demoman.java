package com.warfare.darkannihilation.enemy;

import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_HEALTH;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Demoman extends Opponent {
    private final PoolWrap<BaseBullet> bombPool;
    private boolean goLeft;
    private float lastShot, shootTime;

    public Demoman(PoolWrap<Explosion> explosionPool, PoolWrap<BaseBullet> bombPool, AtlasRegion texture) {
        super(explosionPool, texture, DEMOMAN_HEALTH, DEMOMAN_DAMAGE, 35);

        this.bombPool = bombPool;

        visible = false;
        setIndents(30, 25, 20, 50);
    }

    @Override
    public void reset() {
        health = maxHealth;
        y = MathUtils.random(0, SCREEN_HEIGHT - height);
        speedX = MathUtils.random(5, 10);
        goLeft = MathUtils.randomBoolean();
        shootTime = MathUtils.random(0.1f, 0.2f);

        if (goLeft) {
            x = SCREEN_WIDTH;
            speedX = -speedX;
        } else {
            x = -width;
        }

        visible = true;
    }

    @Override
    public void kill() {
        explodeHuge();
        visible = false;
    }

    private void shoot() {
        if (time - lastShot >= shootTime) {
            lastShot = time;

            bombPool.obtain().start(centerX(), centerY());
        }
    }

    @Override
    public void update() {
        x += speedX;
        shoot();

        if (goLeft) {
            if (x < -width) {
                visible = false;
            }
        } else {
            if (x > SCREEN_WIDTH) {
                visible = false;
            }
        }
    }

    @Override
    public void render() {
        if (visible) {
            if (goLeft) {
                spriteBatch.draw(image, x, y, width, height);
            } else {
                spriteBatch.draw(image, x + width, y, -width, height);
            }
        }
    }
}
