package com.warfare.darkannihilation.support;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Names.PLAYER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.MovementSprite;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.service.Processor;

public class HealthKit extends Opponent {
    public HealthKit() {
        super(getImages().healthKitImg, 0, 15, 0);

        visible = false;
    }

    @Override
    public void update() {
        y -= speedY;

        if (y < -height) visible = false;
    }

    @Override
    public void render() {
        if (visible) super.render();
    }

    @Override
    public void reset() {
        speedY = MathUtils.random(1.7f, 3.4f);
        y = topY;
        x = random(SCREEN_WIDTH);

        visible = true;
    }

    @Override
    public boolean killedBy(MovementSprite sprite) {
        if (intersect(sprite)) {
            if (sprite.name == PLAYER) {
                visible = false;
                Processor.post(() -> ((Player) sprite).heal(damage));
            }
        }
        return true;
    }
}
