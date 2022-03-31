package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_HEALTH_BAR_LEN;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_SPAWN_TIME;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_SPEED;
import static com.warfare.darkannihilation.constants.Names.FACTORY;
import static com.warfare.darkannihilation.constants.Names.PLAYER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.MovementSprite;
import com.warfare.darkannihilation.abstraction.sprite.Shooter;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Image;

public class Factory extends Shooter {
    private final Image red = getImages().redColor;
    private final Image white = getImages().whiteColor;

    private final int yToStop;
    private int healthBar;

    public Factory() {
        super(getImages().factoryImg, SCREEN_WIDTH - 300, getImages().factoryImg.height,
                FACTORY_HEALTH, 0, 100, FACTORY_SPAWN_TIME);

        visible = false;
        name = FACTORY;
        yToStop = SCREEN_HEIGHT - height - 100;

        shrinkBorders(20, 80, 20, halfHeight);
    }

    @Override
    public void reset() {
        health = maxHealth;
        healthBar = FACTORY_HEALTH_BAR_LEN - 3;

        y = topY;
        x = HALF_SCREEN_WIDTH - halfWidth;

        visible = true;
    }

    @Override
    public void update() {
        if (y >= yToStop) {
            y -= FACTORY_SPEED;
        } else {
            shooting();
        }
    }

    @Override
    public boolean killedBy(MovementSprite sprite) {
        boolean killed = false;
        if (sprite.name != PLAYER && intersect(sprite)) {
            sprite.damage(this);

            Processor.postToLooper(() -> {
                health -= sprite.damage;

                if (healthBar > 0) {
                    healthBar = (int) ((health / (float) FACTORY_HEALTH) * FACTORY_HEALTH_BAR_LEN) - 3;
                } else {
                    healthBar = 0;
                }
            });

            if (health <= 0) {
                kill();
                killed = true;
            }
        }
        return killed;
    }

    @Override
    public void kill() {
        explodeHuge();
        visible = false;
    }

    @Override
    protected void shot() {
        Processor.post(() -> {
            for (int i = 0; i < random(1, 3); i++) {
                getPools().minionPool.obtain(random(x, right()), y + 100);
            }
        });
    }

    @Override
    public void render() {
        if (visible) {
            super.render();

            float centerX = centerX();
            white.draw(centerX - 400, y + 390, FACTORY_HEALTH_BAR_LEN, 18);
            red.draw(centerX - 397, y + 392, healthBar, 14);
        }
    }
}
