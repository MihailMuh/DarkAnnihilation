package com.warfare.darkannihilation.support;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Names.HEALTH_KIT;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;

public class HealthKit extends Opponent {
    public HealthKit() {
        super(getImages().healthKitImg, 0, 0, 0);

        kill();
        name = HEALTH_KIT;
    }

    @Override
    public void update() {
        y -= speedY;

        if (y < -height) kill();
    }

    @Override
    public void render() {
        if (visible) super.render();
    }

    @Override
    public void reset() {
        speedY = MathUtils.random(1.7f, 3.4f);
        y = topY;
        x = random(SCREEN_WIDTH);

        visible = true;
    }

    @Override
    public void kill() {
        visible = false;
    }
}
