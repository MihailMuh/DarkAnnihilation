package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.enemy.TripleFighter;
import com.warfare.darkannihilation.enemy.Vader;
import com.warfare.darkannihilation.enemy.deathstar.StarLaserPool;
import com.warfare.darkannihilation.pools.BombPool;
import com.warfare.darkannihilation.pools.BuckshotPool;
import com.warfare.darkannihilation.pools.BulletEnemyPool;
import com.warfare.darkannihilation.pools.BulletPool;
import com.warfare.darkannihilation.pools.ExplosionPool;
import com.warfare.darkannihilation.pools.MinionPool;
import com.warfare.darkannihilation.pools.OpponentPool;
import com.warfare.darkannihilation.pools.SunriseBulletPool;

public class PoolHub implements Disposable {
    private Array<BaseBullet> bulletsEnemy;

    public ExplosionPool explosionPool;
    public BombPool bombPool;
    public BulletEnemyPool bulletEnemyPool;
    public BulletPool bulletPool;
    public BuckshotPool buckshotPool;
    public OpponentPool vaderPool, triplePool;
    public MinionPool minionPool;
    public SunriseBulletPool sunriseBulletPool;
    public StarLaserPool starLaserPool;

    public void initPools(Array<Explosion> explosions, Array<BaseBullet> bulletsEnemy, Array<BaseBullet> bullets, Array<Opponent> empire) {
        this.bulletsEnemy = bulletsEnemy;

        explosionPool = new ExplosionPool(explosions);
        bombPool = new BombPool(bulletsEnemy);
        bulletEnemyPool = new BulletEnemyPool(bulletsEnemy);
        bulletPool = new BulletPool(bullets);
        buckshotPool = new BuckshotPool(bullets);
        minionPool = new MinionPool(empire);
        sunriseBulletPool = new SunriseBulletPool(bulletsEnemy);

        vaderPool = new OpponentPool(empire, (int) (NUMBER_VADER * 2.5)) {
            @Override
            protected Vader newObject() {
                return new Vader();
            }
        };
        triplePool = new OpponentPool(empire, NUMBER_VADER) {
            @Override
            protected TripleFighter newObject() {
                return new TripleFighter();
            }
        };
    }

    public void iniStarLaserPool() {
        starLaserPool = new StarLaserPool(bulletsEnemy);
    }

    @Override
    public void dispose() {
        explosionPool.clear();
        bombPool.clear();
        bulletEnemyPool.clear();
        bulletPool.clear();
        vaderPool.clear();
        triplePool.clear();
        minionPool.clear();
        sunriseBulletPool.clear();

        if (starLaserPool != null) starLaserPool.clear();
    }
}
