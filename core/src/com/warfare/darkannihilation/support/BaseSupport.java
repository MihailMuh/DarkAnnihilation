package com.warfare.darkannihilation.support;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;

public class BaseSupport extends Opponent {
    public BaseSupport(AtlasRegion region, byte name) {
        super(region, 0, 0, 0);

        kill();
        this.name = name;
    }

    @Override
    public void update() {
        translateY(speedY);
    }

    @Override
    public void updateInThread() {
        if (getY() < -getHeight()) kill();
    }

    @Override
    public void reset() {
        speedY = -MathUtils.random(1.7f, 3.4f);

        setPosition(random(SCREEN_WIDTH), SCREEN_HEIGHT);

        visible = true;
    }

    @Override
    public void kill() {
        visible = false;
    }
}