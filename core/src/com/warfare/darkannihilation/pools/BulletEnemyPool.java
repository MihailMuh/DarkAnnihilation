package com.warfare.darkannihilation.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.BulletEnemy;
import com.warfare.darkannihilation.utils.PoolSuper;

public class BulletEnemyPool extends PoolSuper<BaseBullet> {
    private final Array<BaseBullet> bulletsEnemy;

    public BulletEnemyPool(Array<BaseBullet> bulletsEnemy) {
        super(10);
        this.bulletsEnemy = bulletsEnemy;
    }

    public void obtain(float x, float y, float cos, float sin, float angle) {
        BulletEnemy bullet = (BulletEnemy) obtain();
        bullet.start(x, y, cos, sin, angle);
        Gdx.app.postRunnable(() -> bulletsEnemy.add(bullet));
    }

    @Override
    protected BulletEnemy newObject() {
        return new BulletEnemy();
    }
}