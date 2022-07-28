package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_HEALTH_BAR_LEN;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_SPAWN_TIME;
import static com.warfare.darkannihilation.constants.Constants.FACTORY_SPEED;
import static com.warfare.darkannihilation.constants.Constants.ULTIMATE_DAMAGE;
import static com.warfare.darkannihilation.constants.Names.FACTORY;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.Shooter;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.HealthBar;

public class Factory extends Shooter {
    private final HealthBar healthBar;
    private final int yToStop;

    public Factory() {
        super(getImages().factoryImg, FACTORY_HEALTH, ULTIMATE_DAMAGE, 100, FACTORY_SPAWN_TIME);

        visible = false;
        name = FACTORY;
        yToStop = SCREEN_HEIGHT - height - 100;
        healthBar = new HealthBar(FACTORY_HEALTH_BAR_LEN, maxHealth, 22, 18);

        shrinkBorders(20, 80, 20, halfHeight);
    }

    @Override
    public void reset() {
        health = maxHealth;

        y = SCREEN_HEIGHT;
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

        healthBar.setOutlineBarCoords(centerX(), top() - 100);
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);

        Processor.postToLooper(() -> healthBar.updateHealthBar(health));
    }

    @Override
    public boolean killedByPlayer(Player player) {
        return false;
    }

    @Override
    public void kill() {
        explodeHuge();
        healthBar.hide();
    }

    @Override
    protected void shot() {
        for (int i = 0; i < random(1, 3); i++) {
            getPools().minionPool.obtain(random(x, right()), y + 100);
        }
    }

    @Override
    public void render() {
        super.render();
        if (visible) healthBar.render();
    }
}
