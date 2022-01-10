package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.AggressiveSprite;
import com.warfare.darkannihilation.abstraction.Shooter;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Player extends Shooter {
    private final Sound sound;
    private final PoolWrap<Bullet> bulletPool;
    private float endX, endY;

    public Player(AtlasRegion texture, Sound sound, PoolWrap<Bullet> bulletPool, PoolWrap<Explosion> explosionPool) {
        super(texture, MILLENNIUM_FALCON_HEALTH, 10000, HALF_SCREEN_HEIGHT, MILLENNIUM_FALCON_SHOOT_TIME, 0, explosionPool);
        this.bulletPool = bulletPool;
        this.sound = sound;

        setIndents(20, 25, 20, 20);
    }

    @Override
    protected void shot() {
        float X = centerX();
        float Y = top() + 5;
        bulletPool.obtain().start(X - 7, Y);
        bulletPool.obtain().start(X, Y);
        bulletPool.obtain().start(X + 7, Y);

        sound.play(0.17f);
    }

    @Override
    public void reset() {
        super.reset();
        x = HALF_SCREEN_WIDTH;

        endX = (x -= halfWidth);
        endY = (y -= halfHeight);
    }

    public boolean collidedWithEnemy(AggressiveSprite sprite) {
        if (intersect(sprite)) {
            sprite.boomFromPlayer();
            return true;
        }

        return false;
    }

    public void setCoordinates(float X, float Y) {
        endX = X - halfWidth;
        endY = Y - halfHeight;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        speedX = (endX - x) / 3f;
        speedY = (endY - y) / 3f;
    }

    @Override
    protected void explode() {

    }
}
