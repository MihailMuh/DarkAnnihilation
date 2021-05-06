package ru.warfare.darkannihilation;

public class BulletBossVaders extends Sprite {
    public BulletBossVaders(Game g, int X, int Y, int spdx, int spdy) {
        super(g, ImageHub.bulletBossVadersImg.getWidth(), ImageHub.bulletBossVadersImg.getHeight());
        damage = 25;

        speedX = spdx;
        speedY = spdy;

        x = X - halfWidth;
        y = Y -  halfWidth;

        recreateRect(x + 15, y + 15, x + width - 15, y + height - 15);
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playMegaBoom();
        game.allSprites.remove(this);
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

        if (x < -width | x > screenWidth | y > screenHeight | y < -height) {
            game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.bulletBossVadersImg, x, y, null);
    }
}
