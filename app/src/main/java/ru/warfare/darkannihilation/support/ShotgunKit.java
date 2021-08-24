package ru.warfare.darkannihilation.support;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.SHOTGUN_KIT_SPEED;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class ShotgunKit extends Sprite {
    public boolean picked = false;

    public ShotgunKit(Game g) {
        super(g, ImageHub.shotgunKitImg);

        calculateBarriers();
        kill();
    }

    @Override
    public void intersectionPlayer() {
        kill();
        game.intersectOnlyPlayer.remove(this);
        picked = true;
        game.changerGuns.make();
    }

    @Override
    public void kill() {
        x = randInt(0, screenWidthWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void update() {
        y += SHOTGUN_KIT_SPEED;
        if (y > SCREEN_HEIGHT) {
            kill();
        }
    }
}
