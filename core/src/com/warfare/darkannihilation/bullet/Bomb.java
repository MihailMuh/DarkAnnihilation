package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BOMB_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BOMB_SPEED;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.hub.Resources.getImages;

public class Bomb extends BaseBullet {
    public Bomb() {
        super(getImages().bombImg, BOMB_DAMAGE, BOMB_SPEED);

        name = BOMB;
    }

    @Override
    public void update() {
        translateY(speedY);
    }

    @Override
    public void updateInThread() {
        if (getY() < -getHeight()) visible = false;
    }

    @Override
    public void kill() {
        visible = false;
        explodeSmall();
    }
}
