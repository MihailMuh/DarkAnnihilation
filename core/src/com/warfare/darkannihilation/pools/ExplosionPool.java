package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_EXPLOSION;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public class ExplosionPool extends PoolWrap<Explosion> {
    private final Array<Explosion> explosions;

    public ExplosionPool(Array<Explosion> explosions) {
        super(NUMBER_EXPLOSION);
        this.explosions = explosions;
    }

    public void obtain(float x, float y, byte type) {
        Explosion explosion = obtain();
        explosion.start(x, y, type);
        Gdx.app.postRunnable(() -> explosions.add(explosion));
    }

    @Override
    protected Explosion newObject() {
        return new Explosion();
    }
}
