package ru.warfare.darkannihilation;

public class BulletBossVaders extends Sprite {
    public BulletBossVaders(int X, int Y, int spdx, int spdy) {
        super(ImageHub.bulletBossVadersImg.getWidth(), ImageHub.bulletBossVadersImg.getHeight());
        damage = 25;

        speedX = spdx;
        speedY = spdy;

        x = X - halfWidth;
        y = Y -  halfWidth;

        recreateRect(x + 25, y + 25, x + width - 25, y + height - 25);
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 25, y + 25);
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        AudioHub.playMegaBoom();
        Game.allSprites.remove(this);
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (getRect().intersect(bullet.getRect())) {
            bullet.intersection();
        }
    }

    @Override
    public void update() {
        y += speedY;
        x += speedX;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight | y < -height) {
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.bulletBossVadersImg, x, y, null);
    }
}
