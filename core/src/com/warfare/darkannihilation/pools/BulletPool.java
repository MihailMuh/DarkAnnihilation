package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.warfare.darkannihilation.bullet.Bullet;

public class BulletPool extends Pool<Bullet> {
    private final Array<Bullet> bullets;

    public BulletPool(Array<Bullet> bullets) {
        super(NUMBER_MILLENNIUM_FALCON_BULLETS);
        this.bullets = bullets;
    }

    public void obtain(float x, float y) {
        Bullet bullet = obtain();
        bullet.start(x, y);
        Gdx.app.postRunnable(() -> bullets.add(bullet));
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
