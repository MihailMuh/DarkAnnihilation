package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.BULLET_SATURN_DAMAGE;

public class BulletSaturn extends Sprite {
    public BulletSaturn(int X, int Y) {
        super(ImageHub.bulletSaturnImg.getWidth(), ImageHub.bulletSaturnImg.getHeight());

        speedY = randInt(6, 13);
        speedX = randInt(-6, 6);
        damage = BULLET_SATURN_DAMAGE;
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
