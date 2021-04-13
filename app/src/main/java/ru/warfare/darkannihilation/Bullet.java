package ru.warfare.darkannihilation;

public class Bullet extends BulletBase {
    public Bullet(Game g, int X, int Y) {
        super(g, ImageHub.bulletImage.getWidth(), ImageHub.bulletImage.getHeight());
        speedY = 10;

        x = X;
        y = Y;
    }

    @Override
    public void intersection() {
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        game.bullets.remove(this);
        game.numberBullets -= 1;
    }

    @Override
    public void update() {
        y -= speedY;

        if (y < -50) {
            game.bullets.remove(this);
            game.numberBullets -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.bulletImage, x, y, null);
    }
}
