package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Names.ATTENTION;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class Attention extends Opponent {
    private final Rocket rocket;

    public Attention(Rocket rocket) {
        super(getImages().attentionImg, 0, 0, 0);
        this.rocket = rocket;

        setY(SCREEN_HEIGHT - getHeight() - 10);
        visible = false;
        name = ATTENTION;
    }

    @Override
    public void reset() {
        setX(random(SCREEN_WIDTH));

        Processor.postTask(() -> {
            getSounds().attentionSound.play();

            Service.sleep(1750);

            visible = false;
            rocket.start(getX(), SCREEN_HEIGHT);
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
