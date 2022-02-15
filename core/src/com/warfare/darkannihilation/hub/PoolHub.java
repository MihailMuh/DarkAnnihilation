package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.pools.BombPool;
import com.warfare.darkannihilation.pools.BulletEnemyPool;
import com.warfare.darkannihilation.pools.BulletPool;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.pools.ExplosionPool;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;

public class PoolHub {
    public ExplosionPool explosionPool;
    public BombPool bombPool;
    public BulletEnemyPool bulletEnemyPool;
    public BulletPool bulletPool;

    public void initPools(Array<Explosion> explosions, Array<BaseBullet> bulletsEnemy, Array<Bullet> bullets)  {
        explosionPool = new ExplosionPool(explosions);
        bombPool = new BombPool(bulletsEnemy);
        bulletEnemyPool = new BulletEnemyPool(bulletsEnemy);
        bulletPool = new BulletPool(bullets);
    }

    public void disposePools() {
        explosionPool.dispose();
        explosionPool = null;

        bombPool.dispose();
        bombPool = null;
    }
}
