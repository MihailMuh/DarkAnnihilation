package ru.warfare.darkannihilation;

public class Rocket extends Sprite{
    public Rocket() {
        super(ImageHub.rocketImg.getWidth(), ImageHub.rocketImg.getHeight());
        speedY = 35;
        lock = true;
        damage = 100;
        isBullet = true;
        status = "rocket";

        y = -height;
    }

    public void hide() {
        lock = true;
        y = -height;
    }

    public void start(int X) {
        x = X - halfWidth;
        lock = false;
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        AudioHub.playMegaBoom();
        hide();
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
        Game.canvas.drawBitmap(ImageHub.rocketImg, x, y, null);
    }
}
