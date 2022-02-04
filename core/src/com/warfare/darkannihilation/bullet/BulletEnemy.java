package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_ENEMY_DAMAGE;
import static com.warfare.darkannihilation.constants.Names.BULLET_ENEMY;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public class BulletEnemy extends BaseBullet {
    private float angle;

    public BulletEnemy(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture) {
        super(explosionPool, texture, BULLET_ENEMY_DAMAGE);
        name = BULLET_ENEMY;
    }

    public void start(float X, float Y, float speedX, float speedY, float angle) {
        this.speedX = speedX;
        this.speedY = speedY;

        x = X - halfWidth;
        y = Y - halfHeight;

        this.angle = angle;
        visible = true;
    }

    @Override
    public void kill() {
        explodeSmallTriple();
        visible = false;
    }

    @Override
    public void update() {
        x += speedX * delta;
        y += speedY * delta;

        if (x < -width || x > SCREEN_WIDTH || y < -height) visible = false;
    }

    @Override
    public void render() {
        spriteBatch.draw(image, x, y, halfWidth, halfHeight, width, height, 1, 1, angle);
    }
}
