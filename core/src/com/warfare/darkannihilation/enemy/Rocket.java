package com.warfare.darkannihilation.enemy;

import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.ROCKET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.ROCKET_SPEED;
import static com.warfare.darkannihilation.hub.Resources.getImages;

import com.warfare.darkannihilation.bullet.BaseBullet;

public class Rocket extends BaseBullet {
    public Rocket() {
        super(getImages().rocketImg, ROCKET_DAMAGE, DEMOMAN_HEALTH, ROCKET_SPEED);
    }

    @Override
    public void start(float X, float Y) {
        super.start(X, Y);
        health = maxHealth;
    }

    @Override
    public void update() {
        y -= speedY;

        if (y <= -height) {
            visible = false;
        }
    }

    @Override
    public void kill() {
        explodeHuge();
    }
}
