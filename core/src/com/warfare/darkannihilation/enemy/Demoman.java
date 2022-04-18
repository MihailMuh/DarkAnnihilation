package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.DEMOMAN_HEALTH;
import static com.warfare.darkannihilation.constants.Names.DEMOMAN;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.Shooter;
import com.warfare.darkannihilation.systemd.service.Processor;

public class Demoman extends Shooter {
    private boolean goLeft;

    public Demoman() {
        super(getImages().demomanImg, DEMOMAN_HEALTH, DEMOMAN_DAMAGE, 35, 0);

        visible = false;
        name = DEMOMAN;
        shrinkBorders(30, 25, 20, 50);
    }

    @Override
    public void reset() {
        health = maxHealth;
        y = random(HALF_SCREEN_HEIGHT - height, SCREEN_HEIGHT - height);
        speedX = random(5, 10);
        goLeft = randomBoolean();
        shootTime = random(0.1f, 0.2f);

        if (goLeft) {
            x = SCREEN_WIDTH;
            speedX = -speedX;
        } else {
            x = -width;
        }

        visible = true;
    }

    @Override
    public void kill() {
        explodeHuge();
    }

    @Override
    public void shot() {
        Processor.postToLooper(() -> getPools().bombPool.obtain(centerX(), centerY()));
    }

    @Override
    public void update() {
        x += speedX;
        shooting();

        if (goLeft) {
            if (x < -width) {
                visible = false;
            }
        } else {
            if (x > SCREEN_WIDTH) {
                visible = false;
            }
        }
    }

    @Override
    public void render() {
        if (visible) {
            if (goLeft) {
                super.render();
            } else {
                image.draw(x + width, y, -width, height);
            }
        }
    }
}
