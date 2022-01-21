package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.VADER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.VADER_HEALTH;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Vader extends Opponent {
    private AtlasRegion img;
    private final float borderY;

    public Vader(PoolWrap<Explosion> explosionPool) {
        super(explosionPool, ImageHub.vadersImages[0], VADER_HEALTH, VADER_DAMAGE, 1);

        borderY = SCREEN_HEIGHT + height;
        reset();
    }

    @Override
    public void reset() {
        visible = true;

        health = maxHealth;
        img = ImageHub.vadersImages[random(0, 2)];

        x = random(SCREEN_WIDTH);
        y = borderY;

        speedX = random(-385f, 385f);
        speedY = random(230f, 770f);
    }

    @Override
    public void kill() {
        explodeDefault();
        reset();
    }

    @Override
    public void killFromPlayer() {
        explodeSmall();
        reset();
    }

    @Override
    public void update() {
        x += speedX * delta;
        y -= speedY * delta;

        if (x < -width || x > SCREEN_WIDTH || y < -height) reset();
    }

    @Override
    public void render() {
        if (visible) spriteBatch.draw(img, x, y, width, height);
    }
}
