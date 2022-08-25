package com.warfare.darkannihilation.bullet;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;

public abstract class BaseBullet extends Opponent {
    public BaseBullet(AtlasRegion region, int damage, float speedY) {
        this(region, damage, 0, speedY);
    }

    public BaseBullet(AtlasRegion region, int damage, int maxHealth, float speedY) {
        super(region, maxHealth, damage, 0);

        visible = false;
        this.speedY = speedY;
    }

    public void start(float x, float y) {
        setCenter(x, y);
        visible = true;
    }
}
