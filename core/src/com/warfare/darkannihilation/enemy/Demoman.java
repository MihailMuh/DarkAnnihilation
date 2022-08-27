package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_HEALTH;
import static com.warfare.darkannihilation.constants.Names.DEMOMAN;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.Shooter;

public class Demoman extends Shooter {
    private boolean goLeft;

    public Demoman() {
        super(getImages().demomanImg, DEMOMAN_HEALTH, DEMOMAN_DAMAGE, 35, 0);

        visible = false;
        name = DEMOMAN;
        shrinkBounds(30, 25, 20, 50);
    }

    @Override
    public void reset() {
        health = maxHealth;
        setY(random(HALF_SCREEN_HEIGHT - getHeight(), SCREEN_HEIGHT - getHeight()));
        speedX = random(5, 10);
        goLeft = randomBoolean();
        shootTime = random(0.1f, 0.2f);

        setFlip(!goLeft, false);
        if (goLeft) {
            setX(SCREEN_WIDTH);
            speedX = -speedX;
        } else {
            setX(-getWidth());
        }

        visible = true;
    }

    @Override
    public void kill() {
        visible = false;
        explodeHuge();
    }

    @Override
    public void shot() {
        getPools().bombPool.obtain(centerX(), centerY());
        getSounds().fallingBombSound.play();
    }

    @Override
    public void update() {
        translateX(speedX);
    }

    @Override
    public void updateInThread() {
        shooting();

        if (goLeft) {
            if (getX() < -getWidth()) {
                visible = false;
            }
        } else {
            if (getX() > SCREEN_WIDTH) {
                visible = false;
            }
        }
    }
}
