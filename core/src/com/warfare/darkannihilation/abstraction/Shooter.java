package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class Shooter extends Warrior {
    protected float lastShot;
    protected float shootTime;

    public Shooter(TextureAtlas.AtlasRegion texture, int maxHealth, int damage, float shootTime, int killSCore, PoolWrap<Explosion> explosionPool) {
        super(texture, maxHealth, damage, killSCore, explosionPool);
        this.shootTime = shootTime;
    }

    public Shooter(TextureAtlas.AtlasRegion texture, int maxHealth, int damage, float startY, float shootTime, int killSCore, PoolWrap<Explosion> explosionPool) {
        super(texture, maxHealth, damage, startY, killSCore, explosionPool);
        this.shootTime = shootTime;
    }

    protected abstract void shot();

    public void shooting() {
        if (time - lastShot >= shootTime) {
            lastShot = time;

            shot();
        }
    }

    @Override
    public void update() {
        shooting();
    }
}
