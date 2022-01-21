package com.warfare.darkannihilation.support;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.MovementSprite;
import com.warfare.darkannihilation.utils.PoolWrap;

public class HealthKit extends MovementSprite {
    public HealthKit(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture, int maxHealth, int damage, int killScore) {
        super(explosionPool, texture, maxHealth, damage, killScore);
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }
}
