package ru.warfare.darkannihilation.support;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.Constants.HEALTH_KIT_SPEED;

public class HealthKit extends Sprite {
    public HealthKit(Game g) {
        super(g, ImageHub.healthKitImg);
        speedY = HEALTH_KIT_SPEED;
        isBullet = true;

        hide();
    }

    public void hide() {
        x = Math.randInt(0, screenWidthWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void intersectionPlayer() {
        hide();
        AudioHub.playHealSnd();
        game.player.heal();
    }

    @Override
    public void update() {
        y += speedY;

        if (y > Game.screenHeight) {
            hide();
        }
    }
}
