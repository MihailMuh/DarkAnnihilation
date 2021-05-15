package ru.warfare.darkannihilation;

public class Buckshot extends Sprite {
    public Buckshot(int X, int Y, int speed) {
        super(ImageHub.buckshotImg.getWidth(), ImageHub.buckshotImg.getHeight());

        x = X - halfWidth;
        y = Y;
        damage = 3;
        isPassive = true;
        isBullet = true;

        speedX = speed;
        speedY = 8;
    }

    @Override
    public void intersection() {
        createSmallTripleExplosion();
        Game.bullets.remove(this);
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > Game.screenWidth) {
            Game.bullets.remove(this);
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.buckshotImg, x, y, null);
    }
}
