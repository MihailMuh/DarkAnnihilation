package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BUCKSHOT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Buckshot;
import com.warfare.darkannihilation.utils.PoolWrap;

public class BuckshotPool extends PoolWrap<BaseBullet> {
    private final Array<BaseBullet> bullets;

    public BuckshotPool(Array<BaseBullet> bullets) {
        super(NUMBER_MILLENNIUM_FALCON_BUCKSHOT);
        this.bullets = bullets;
    }

    public void obtain(float x, float y, int speedX) {
        Buckshot bullet = (Buckshot) obtain();
        bullet.start(x, y, speedX);
        Gdx.app.postRunnable(() -> bullets.add(bullet));
    }

    @Override
    protected Buckshot newObject() {
        return new Buckshot();
    }
}
