package ru.warfare.darkannihilation;

public class ShotgunKit extends Sprite {
    public boolean picked = false;

    public ShotgunKit(Game g) {
        super(g, ImageHub.shotgunKitImg.getWidth(), ImageHub.shotgunKitImg.getHeight());
        speedY = 2;
        lock = true;
        isPassive = true;
        isBullet = true;

        x = randInt(0, screenWidth);
        y = -height;
    }

    public void hide() {
        x = randInt(0, screenWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void intersectionPlayer() {
        hide();
        picked = true;
        game.changerGuns.changeGun();
    }


    @Override
    public void update() {
        y += speedY;
        if (y > screenHeight) {
            hide();
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.shotgunKitImg, x, y, null);
    }
}
