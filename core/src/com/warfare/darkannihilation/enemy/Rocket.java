package com.warfare.darkannihilation.enemy;

import static com.warfare.darkannihilation.constants.Constants.ROCKET_DAMAGE;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Rocket extends Opponent {
    public Rocket(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture) {
        super(explosionPool, texture, 0, ROCKET_DAMAGE, 0);

        speedY = 2700;
        visible = false;
    }

    public void start(float X) {
        x = X;
        y = SCREEN_HEIGHT;

        visible = true;
    }

    @Override
    public boolean killedByBullet(BaseBullet bullet) {
        if (intersect(bullet)) bullet.kill();
        return false;
    }

    @Override
    public void killFromPlayer() {
        explodeHuge();
        visible = false;
    }

    @Override
    public void update() {
        y -= 2700 * delta;

        if (y <= -height) visible = false;
    }

    @Override
    public void render() {
        if (visible) super.render();
    }

    @Override
    public void kill() {
    }
}
