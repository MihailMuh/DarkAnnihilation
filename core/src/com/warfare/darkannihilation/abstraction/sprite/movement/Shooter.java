package com.warfare.darkannihilation.abstraction.sprite.movement;

import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.MovementSprite;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class Shooter extends MovementSprite {
    protected float lastShot;
    protected float shootTime;

    public Shooter(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture, int maxHealth, int damage, int killScore, float shootTime) {
        super(explosionPool, texture, maxHealth, damage, killScore);

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
