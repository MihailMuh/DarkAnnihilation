package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.VADER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.VADER_HEALTH;
import static com.warfare.darkannihilation.constants.Names.VADER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Vader extends Opponent {
    private final float borderY;

    public Vader(PoolWrap<Explosion> explosionPool) {
        super(explosionPool, getImages().vadersImages[0], VADER_HEALTH, VADER_DAMAGE, 1);

        name = VADER;
        borderY = SCREEN_HEIGHT + height;
        reset();
    }

    @Override
    public void reset() {
        Processor.postToLooper(() -> {
            if (shouldKill) visible = false;

            health = maxHealth;
            image = getImages().vadersImages[random(0, 2)];

            x = random(SCREEN_WIDTH);
            y = borderY;

//            speedX = random(-385f, 385f);
//            speedY = random(230f, 770f);
            speedX = random(-6.5f, 6.5f);
            speedY = random(4f, 13f);
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
//        x += speedX * delta;
//        y -= speedY * delta;
        x += speedX;
        y -= speedY;

        if (x < -width || x > SCREEN_WIDTH || y < -height) reset();
    }
}
