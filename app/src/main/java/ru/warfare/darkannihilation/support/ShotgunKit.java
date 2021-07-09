package ru.warfare.darkannihilation.support;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.Constants.SHOTGUN_KIT_SPEED;

public class ShotgunKit extends Sprite {
    public boolean picked = false;

    public ShotgunKit(Game g) {
        super(g, ImageHub.shotgunKitImg);
        speedY = SHOTGUN_KIT_SPEED;
        isPassive = true;
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
        picked = true;
        game.changerGuns.make();
    }


    @Override
    public void update() {
        y += speedY;
        if (y > Game.screenHeight) {
            hide();
        }
    }
}
