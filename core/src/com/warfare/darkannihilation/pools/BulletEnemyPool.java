package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_BULLETS_ENEMY;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.BulletEnemy;
import com.warfare.darkannihilation.utils.PoolWrap;

public class BulletEnemyPool extends PoolWrap<BaseBullet> {
    private final Array<BaseBullet> bulletsEnemy;

    public BulletEnemyPool(Array<BaseBullet> bulletsEnemy) {
        super(NUMBER_BULLETS_ENEMY);
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