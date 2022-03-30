package com.warfare.darkannihilation.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bomb;

public class BombPool extends Pool<BaseBullet> {
    private final Array<BaseBullet> bulletsEnemy;

    public BombPool(Array<BaseBullet> bulletsEnemy) {
        super(10);
        this.bulletsEnemy = bulletsEnemy;
    }

    public void obtain(float x, float y) {
        BaseBullet bomb = obtain();
        bomb.start(x, y);
        Gdx.app.postRunnable(() -> bulletsEnemy.add(bomb));
    }

    @Override
    protected Bomb newObject() {
        return new Bomb();
    }
}
