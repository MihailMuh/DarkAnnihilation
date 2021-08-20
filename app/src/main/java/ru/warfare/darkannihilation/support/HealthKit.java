package ru.warfare.darkannihilation.support;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.HEALTH_KIT_SPEED;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class HealthKit extends Sprite {
    public HealthKit(Game g) {
        super(g, ImageHub.healthKitImg);

        calculateBarriers();
        hide();
    }

    @Override
    public void hide() {
        x = randInt(0, screenWidthWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void intersectionPlayer() {
        kill();
        AudioHub.playHealSnd();
        game.player.heal();
    }

    @Override
    public void kill() {
        hide();
        game.intersectOnlyPlayer.remove(this);
    }

    @Override
    public void update() {
        y += HEALTH_KIT_SPEED;

        if (y > SCREEN_HEIGHT) {
            kill();
        }
    }
}
