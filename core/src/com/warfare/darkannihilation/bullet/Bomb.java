package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BOMB_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BOMB_SPEED;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.hub.Resources.getImages;

public class Bomb extends BaseBullet {
    public Bomb() {
        super(getImages().bombImg, BOMB_DAMAGE);

        name = BOMB;
    }

    @Override
    public void update() {
        y -= BOMB_SPEED;

        if (y < -height) visible = false;
    }

    @Override
    public void kill() {
        explodeSmall();
        visible = false;
    }
}
