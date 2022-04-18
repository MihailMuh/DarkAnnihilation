package com.warfare.darkannihilation.enemy;

import static com.warfare.darkannihilation.constants.Names.ATTENTION;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class Attention extends Opponent {
    private final Rocket rocket;

    public Attention(Rocket rocket) {
        super(getImages().attentionImg, 0, 0, 0);
        this.rocket = rocket;

        y = SCREEN_HEIGHT - height - 10;
        visible = false;
        name = ATTENTION;
    }

    @Override
    public void reset() {
        x = MathUtils.random(SCREEN_WIDTH);

        getSounds().attentionSound.play();
        Processor.post(() -> {
            Service.sleep(1750);

            visible = false;
            rocket.start(x);
        });

        visible = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void kill() {

    }
}
