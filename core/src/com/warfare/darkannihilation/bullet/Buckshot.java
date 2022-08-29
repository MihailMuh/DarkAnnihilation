package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BUCKSHOT_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BUCKSHOT_SPEED;
import static com.warfare.darkannihilation.constants.Names.BUCKSHOT;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class Buckshot extends BaseBullet {
    public Buckshot() {
        super(getImages().buckshot, BUCKSHOT_DAMAGE, BUCKSHOT_SPEED);
        name = BUCKSHOT;
    }

    public void start(float x, float y, int speedX) {
        setCenter(x, y);

        this.speedX = speedX;
        visible = true;
    }

    @Override
    public void update() {
        translate(speedX, speedY);
    }

    @Override
    public void updateInThread() {
        if (getY() > SCREEN_HEIGHT) {
            visible = false;
        }
    }

    @Override
    public void kill() {
        visible = false;
        explodeSmallTriple();
    }
}
