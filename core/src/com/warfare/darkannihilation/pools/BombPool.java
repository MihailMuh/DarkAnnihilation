package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_BOMBS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bomb;
import com.warfare.darkannihilation.utils.PoolWrap;

public class BombPool extends PoolWrap<BaseBullet> {
    private final Array<BaseBullet> bulletsEnemy;

    public BombPool(Array<BaseBullet> bulletsEnemy) {
        super(NUMBER_BOMBS);
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
