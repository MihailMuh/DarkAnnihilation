package com.warfare.darkannihilation.bullet;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.MovementSprite;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class BaseBullet extends MovementSprite {
    public BaseBullet(PoolWrap<Explosion> explosionPool, AtlasRegion texture, int maxHealth, int damage) {
        super(explosionPool, texture, maxHealth, damage, 0);

        visible = false;
    }

    public BaseBullet(PoolWrap<Explosion> explosionPool, AtlasRegion texture, int damage) {
        super(explosionPool, texture, 0, damage, 0);
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;

        visible = true;
    }
}
