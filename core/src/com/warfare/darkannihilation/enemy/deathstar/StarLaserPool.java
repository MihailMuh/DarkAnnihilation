package com.warfare.darkannihilation.enemy.deathstar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.utils.PoolWrap;

public class StarLaserPool extends PoolWrap<BaseBullet> {
    private final Array<BaseBullet> bulletsEnemy;

    public StarLaserPool(Array<BaseBullet> bulletsEnemy) {
        super(3);
        this.bulletsEnemy = bulletsEnemy;
    }

    public void obtain(float x, float y, float degrees) {
        Laser laser = (Laser) obtain();
        laser.start(x, y, degrees);
        Gdx.app.postRunnable(() -> bulletsEnemy.add(laser));
    }

    @Override
    protected Laser newObject() {
        return new Laser();
    }
}
