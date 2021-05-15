package ru.warfare.darkannihilation;

public class Bullet extends Sprite {
    public Bullet(Game g, int X, int Y) {
        super(g, ImageHub.bulletImage.getWidth(), ImageHub.bulletImage.getHeight());
        speedY = 10;
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

        if (y < -50) {
            Game.bullets.remove(this);
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.bulletImage, x, y, null);
    }
}
