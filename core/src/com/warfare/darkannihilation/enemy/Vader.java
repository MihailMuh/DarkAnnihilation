package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.Constants.VADER_DAMAGE;
import static com.warfare.darkannihilation.Constants.VADER_HEALTH;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.PoolHub;

public class Vader extends Warrior {
    public Vader() {
        super(ImageHub.storage.vadersImages[random(0, 2)], VADER_HEALTH, VADER_DAMAGE);
    }

    @Override
    public void reset() {
        super.reset();
        x = random(SCREEN_WIDTH);

        speedX = random(-385f, 385f);
        speedY = random(230f, 770f);
    }

    @Override
    public void update() {
        x += speedX * delta;
        y -= speedY * delta;

        if (x < -width || x > SCREEN_WIDTH || y < -height) {
            reset();
        }
    }

    @Override
    protected void explode() {
        PoolHub.explosionPool.obtain().start(centerX(), centerY());
    }
}
