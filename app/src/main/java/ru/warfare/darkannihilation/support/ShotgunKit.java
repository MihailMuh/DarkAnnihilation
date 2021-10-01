package ru.warfare.darkannihilation.support;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.SHOTGUN_KIT_SPEED;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class ShotgunKit extends Sprite {
    public volatile boolean picked = false;

    public ShotgunKit(Game g) {
        super(g, ImageHub.shotgunKitImg);

        calculateBarriers();
        hide();
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
    }

    @Override
    public void intersectionPlayer() {
        lock = true;
        picked = true;
        game.changerGuns.make();
    }

    @Override
    public void hide() {
        x = randInt(0, screenWidthWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void kill() {
    }

    @Override
    public void update() {
        y += SHOTGUN_KIT_SPEED;

        if (y > SCREEN_HEIGHT) {
            hide();
        }
    }
}
