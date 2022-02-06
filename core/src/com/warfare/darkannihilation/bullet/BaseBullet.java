package com.warfare.darkannihilation.bullet;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.MovementSprite;
import com.warfare.darkannihilation.utils.Image;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class BaseBullet extends MovementSprite {
    public BaseBullet(PoolWrap<Explosion> explosionPool, Image image, int damage) {
        super(explosionPool, image, 0, damage, 0);

        visible = false;
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;

        visible = true;
    }
}
