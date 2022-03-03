package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.warfare.darkannihilation.utils.Image;

public abstract class Shooter extends Opponent {
    protected float lastShot;
    protected float shootTime;

    public Shooter(Image image, int maxHealth, int damage, int killScore, float shootTime) {
        super(image, maxHealth, damage, killScore);

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
