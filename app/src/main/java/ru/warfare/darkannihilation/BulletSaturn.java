package ru.warfare.darkannihilation;

public class BulletSaturn extends Sprite {
    public BulletSaturn(Game g, int X, int Y) {
        super(g, ImageHub.bulletSaturnImg.getWidth(), ImageHub.bulletSaturnImg.getHeight());

        speedY = randInt(6, 13);
        speedX = randInt(-6, 6);
        damage = 1;
        isPassive = true;
        isBullet = true;

        x = X;
        y = Y;
    }

    @Override
    public void intersection() {
        createSmallExplosion();
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
        Game.canvas.drawBitmap(ImageHub.bulletSaturnImg, x, y, null);
    }
}
