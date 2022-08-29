package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.utils.PoolWrap;

public class BulletPool extends PoolWrap<BaseBullet> {
    private final Array<BaseBullet> bullets;

    public BulletPool(Array<BaseBullet> bullets) {
        super(NUMBER_MILLENNIUM_FALCON_BULLETS);
        this.bullets = bullets;
    }

    public void obtain(float x, float y) {
        BaseBullet bullet = obtain();
        bullet.start(x, y);
        Gdx.app.postRunnable(() -> bullets.add(bullet));
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
