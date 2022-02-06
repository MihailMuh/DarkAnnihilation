package com.warfare.darkannihilation.abstraction.sprite.movement;

import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.Image;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class Shooter extends Opponent {
    protected float lastShot;
    protected float shootTime;

    public Shooter(PoolWrap<Explosion> explosionPool, Image image, int maxHealth, int damage, int killScore, float shootTime) {
        super(explosionPool, image, maxHealth, damage, killScore);

        this.shootTime = shootTime;
    }

    protected abstract void shot();

    public void shooting() {
        if (time - lastShot >= shootTime) {
            lastShot = time;

            shot();
        }
    }
}
