package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.systemd.service.Processor;

public abstract class Shooter extends Opponent {
    protected float lastShot;
    protected float shootTime;
    protected boolean shotInThread = true;

    public Shooter(AtlasRegion region, int maxHealth, int damage, int killScore, float shootTime) {
        super(region, maxHealth, damage, killScore);

        this.shootTime = shootTime;
    }

    protected abstract void shot();

    public void shooting() {
        if (time - lastShot >= shootTime) {
            lastShot = time;

            if (shotInThread) {
                Processor.postToLooper(this::shot);
            } else {
                shot();
            }
        }
    }
}
