package com.warfare.darkannihilation.enemy.boss;

import static com.warfare.darkannihilation.constants.Constants.BOSS_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.BOSS_SHOOT_TIME;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.Shooter;
import com.warfare.darkannihilation.utils.Image;
import com.warfare.darkannihilation.utils.PoolWrap;

public class DeathStar extends Shooter {
    public DeathStar(PoolWrap<Explosion> explosionPool, Image image) {
        super(explosionPool, image, BOSS_HEALTH, 0, 325, BOSS_SHOOT_TIME);

        speedY = 77;
        x = MathUtils.random(SCREEN_WIDTH);
        y = SCREEN_HEIGHT + 800;
    }

    @Override
    public void update() {

    }

    @Override
    protected void shot() {

    }
}
