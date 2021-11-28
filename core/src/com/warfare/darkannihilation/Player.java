package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_HEALTH;
import static com.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.hub.PoolHub;

public class Player extends Warrior {
    private float endX, endY;
    private final int speed;
    private float shootTime;

    public Player(AtlasRegion texture) {
        super(texture, MILLENNIUM_FALCON_HEALTH, 10000, HALF_SCREEN_HEIGHT);

        speed = 20;

        setIndents(20, 25, 20, 20);
    }

    protected void shoot() {
        if (time - shootTime >= MILLENNIUM_FALCON_SHOOT_TIME) {
            shootTime = time;

            float X = centerX();
            float Y = top() + 5;
            PoolHub.bulletPool.obtain().start(X - 7, Y);
            PoolHub.bulletPool.obtain().start(X, Y);
            PoolHub.bulletPool.obtain().start(X + 7, Y);
        }
    }

    @Override
    public void reset() {
        super.reset();
        x = HALF_SCREEN_WIDTH;

        endX = (x -= halfWidth);
        endY = (y -= halfHeight);
    }

    public boolean collidesWithEnemy(Warrior enemy) {
        if (intersect(enemy)) {
            enemy.boom();
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

        speedX = (endX - x) * speed * delta;
        speedY = (endY - y) * speed * delta;

        shoot();
    }

    @Override
    public void render() {
        draw();
    }

    @Override
    protected void explode() {

    }
}
