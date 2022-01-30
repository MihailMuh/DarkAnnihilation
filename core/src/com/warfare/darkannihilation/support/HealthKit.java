package com.warfare.darkannihilation.support;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.service.Processor;

public class HealthKit extends Opponent {
    public HealthKit(TextureAtlas.AtlasRegion texture) {
        super(null, texture, 0, 15, 0);

        y = SCREEN_HEIGHT + height;
        visible = false;
    }

    @Override
    public void update() {
        y -= delta * speedY;

        if (y < -height) visible = false;
    }

    @Override
    public void render() {
        if (visible) super.render();
    }

    @Override
    public void reset() {
        speedY = MathUtils.random(100, 200);
        y = SCREEN_HEIGHT + height;
        x = random(SCREEN_WIDTH);

        visible = true;
    }

    @Override
    public boolean killedByBullet(BaseBullet bullet) {
        return false;
    }

    @Override
    public boolean killedByPlayer(Player player) {
        if (intersect(player)) {
            visible = false;
            Processor.post(() -> player.heal(damage));
            return true;
        }
        return false;
    }
}
