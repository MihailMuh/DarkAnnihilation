package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_ENEMY_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_ENEMY_SPEED;
import static com.warfare.darkannihilation.constants.Names.BULLET_ENEMY;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import static java.lang.Math.max;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.PoolWrap;

public class BulletEnemy extends BaseBullet {
    private float angle;
    private float shrinkWidth, shrinkHeight;

    public BulletEnemy(PoolWrap<Explosion> explosionPool) {
        super(explosionPool, getImages().laserImg, BULLET_ENEMY_DAMAGE);
        name = BULLET_ENEMY;
    }

    public void start(float X, float Y, float cos, float sin, float angle) {
        this.speedX = BULLET_ENEMY_SPEED * cos;
        this.speedY = BULLET_ENEMY_SPEED * sin;
        this.angle = angle;
        shrinkWidth = max(cos * width, width);
        shrinkHeight = max(sin * height, height);

        start(X, Y);
    }

    @Override
    public boolean intersect(BaseSprite r) {
        return x < r.right() && x + shrinkWidth > r.x() && y < r.top() && y + shrinkHeight > r.y();
    }

    @Override
    public void kill() {
        explodeSmallTriple();
        visible = false;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (x < -width || x > SCREEN_WIDTH || y < -height) visible = false;
    }

    @Override
    public void render() {
        image.draw(x, y, angle);
    }
}
