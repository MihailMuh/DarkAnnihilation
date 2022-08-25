package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.VADER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.VADER_HEALTH;
import static com.warfare.darkannihilation.constants.Names.VADER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.systemd.service.Processor;

public class Vader extends Opponent {
    public Vader() {
        super(getImages().vadersImages[0], VADER_HEALTH, VADER_DAMAGE, 1);

        name = VADER;
        reset();
    }

    @Override
    public void reset() {
        visible = !shouldKill;

        Processor.postToLooper(() -> {
            health = maxHealth;

            setRegion(getImages().vadersImages[random(0, 2)]);
            setPosition(random(SCREEN_WIDTH), SCREEN_HEIGHT);

            speedX = random(-6.5f, 6.5f);
            speedY = -random(4f, 13f);
        });
    }

    @Override
    public void kill() {
        explodeDefault();
        reset();
    }

    @Override
    public void killFromPlayer() {
        explodeSmall();
        reset();
    }

    @Override
    public void update() {
        translate(speedX, speedY);

        if (getX() < -getWidth() || getX() > SCREEN_WIDTH || getY() < -getHeight()) reset();
    }
}
