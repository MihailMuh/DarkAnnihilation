package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.SHOTGUN_KIT_SPEED;

public class ShotgunKit extends Sprite {
    public boolean picked = false;

    public ShotgunKit(Game g) {
        super(g, ImageHub.shotgunKitImg.getWidth(), ImageHub.shotgunKitImg.getHeight());
        speedY = SHOTGUN_KIT_SPEED;
        isPassive = true;
        isBullet = true;

        hide();
    }

    public void hide() {
        x = randInt(0, screenWidthWidth);
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

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.shotgunKitImg, x, y, null);
    }
}
