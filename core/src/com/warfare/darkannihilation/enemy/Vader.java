package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.Constants.VADER_DAMAGE;
import static com.warfare.darkannihilation.Constants.VADER_HEALTH;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.systemd.service.ImageHub;

public class Vader extends Warrior {
    public Vader() {
        super(ImageHub.vadersImages[random(0, 2)], VADER_HEALTH, VADER_DAMAGE);
    }

    @Override
    public void reset() {
        super.reset();
        x = random(SCREEN_WIDTH);

        speedX = random(-295f, 295f);
        speedY = random(175f, 590f);
    }

    @Override
    public void update() {
        x += speedX * delta;
        y -= speedY * delta;

        if (x < -width || x > SCREEN_WIDTH || y < -height) {
            reset();
        }
    }
}
