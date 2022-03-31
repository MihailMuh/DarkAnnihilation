package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.enemy.Minion;
import com.warfare.darkannihilation.enemy.TripleFighter;
import com.warfare.darkannihilation.enemy.Vader;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.pools.BombPool;
import com.warfare.darkannihilation.pools.BulletEnemyPool;
import com.warfare.darkannihilation.pools.BulletPool;
import com.warfare.darkannihilation.pools.ExplosionPool;
import com.warfare.darkannihilation.pools.MinionPool;
import com.warfare.darkannihilation.pools.OpponentPool;

public class PoolHub {
    public ExplosionPool explosionPool;
    public BombPool bombPool;
    public BulletEnemyPool bulletEnemyPool;
    public BulletPool bulletPool;
    public OpponentPool vaderPool, triplePool;
    public MinionPool minionPool;

    public void initPools(Array<Explosion> explosions, Array<BaseBullet> bulletsEnemy, Array<Bullet> bullets) {
        explosionPool = new ExplosionPool(explosions);
        bombPool = new BombPool(bulletsEnemy);
        bulletEnemyPool = new BulletEnemyPool(bulletsEnemy);
        bulletPool = new BulletPool(bullets);
    }

    public void initWithPlayer(Array<Opponent> empire, Player player) {
        vaderPool = new OpponentPool(empire, (int) (NUMBER_VADER * 2.5)) {
            @Override
            protected Vader newObject() {
                return new Vader();
            }
        };
        triplePool = new OpponentPool(empire, NUMBER_VADER) {
            @Override
            protected TripleFighter newObject() {
                return new TripleFighter(player);
            }
        };
        minionPool = new MinionPool(empire) {
            @Override
            protected Minion newObject() {
                return new Minion(player);
            }
        };
    }

    public void disposePools() {
        explosionPool.clear();
        bombPool.clear();
        bulletEnemyPool.clear();
        bulletPool.clear();
        vaderPool.clear();
        triplePool.clear();
        minionPool.clear();
    }
}
