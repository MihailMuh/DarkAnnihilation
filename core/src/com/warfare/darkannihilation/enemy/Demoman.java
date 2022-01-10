package com.warfare.darkannihilation.enemy;

import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_HEALTH;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.abstraction.Shooter;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Demoman extends Shooter {
    private final PoolWrap<BaseBullet> bombPool;
    private boolean goLeft;

    public Demoman(AtlasRegion texture, PoolWrap<Explosion> explosionPool, PoolWrap<BaseBullet> bombPool) {
        super(texture, DEMOMAN_HEALTH, DEMOMAN_DAMAGE, 0, 35, explosionPool);
        this.bombPool = bombPool;

        startY = HALF_SCREEN_HEIGHT - height;
        visible = false;

        setIndents(30, 25, 20, 50);
    }

    @Override
    public void reset() {
        health = maxHealth;
        y = MathUtils.random(startY, SCREEN_HEIGHT - height);
        speedX = MathUtils.random(385, 770);
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
    protected void shot() {
        bombPool.obtain().start(centerX(), centerY());
    }

    @Override
    public void boom() {
        boomFromPlayer();
    }

    @Override
    public void boomFromPlayer() {
        explode();
    }

    @Override
    protected void explode() {
        explodeHuge();
        visible = false;
    }

    @Override
    public void update() {
        super.update();
        x += speedX * delta;

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
