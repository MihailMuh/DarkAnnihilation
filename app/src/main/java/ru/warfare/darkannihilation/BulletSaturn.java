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
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        game.bullets.remove(this);
        game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > screenWidth) {
            game.bullets.remove(this);
            game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.bulletSaturnImg, x, y, null);
    }
}
