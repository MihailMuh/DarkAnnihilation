package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;
import com.warfare.darkannihilation.bullet.Bullet;

public final class PoolHub {
    public static PoolWrap<Explosion> explosionPool;
    public static PoolWrap<Bullet> bulletPool;

    public static void init(Array<Explosion> explosions, Array<Bullet> bullets) {
        explosionPool = new PoolWrap<Explosion>(explosions) {
            @Override
            protected Explosion newObject() {
                return new Explosion(ImageHub.defaultExplosionAnim);
            }
        };

        bulletPool = new PoolWrap<Bullet>(bullets) {
            @Override
            protected Bullet newObject() {
                return new Bullet();
            }
        };
    }
}
