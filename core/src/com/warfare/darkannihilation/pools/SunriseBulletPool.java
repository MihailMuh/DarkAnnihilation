package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_SUNRISE_BOMBS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.enemy.deathstar.SunriseBomb;
import com.warfare.darkannihilation.utils.PoolWrap;

public class SunriseBulletPool extends PoolWrap<BaseBullet> {
    private final Array<BaseBullet> bulletsEnemy;

    public SunriseBulletPool(Array<BaseBullet> bulletsEnemy) {
        super(NUMBER_SUNRISE_BOMBS);
        this.bulletsEnemy = bulletsEnemy;
    }

    public void obtain(float x, float y, float cos, float sin) {
        SunriseBomb bullet = (SunriseBomb) obtain();
        bullet.start(x, y, cos, sin);
        Gdx.app.postRunnable(() -> bulletsEnemy.add(bullet));
    }

    @Override
    protected SunriseBomb newObject() {
        return new SunriseBomb();
    }
}
