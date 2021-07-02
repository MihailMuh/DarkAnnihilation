package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

import static ru.warfare.darkannihilation.Constants.BULLET_BOSS_DAMAGE;
import static ru.warfare.darkannihilation.Constants.BULLET_BOSS_SPEED;

public class BulletBoss extends Sprite {
    private Bitmap img = ImageHub.laserImage;

    public BulletBoss(int X, int Y, int type) {
        super();
        damage = BULLET_BOSS_DAMAGE;
        status = "bulletEnemy";
        isBullet = true;

        switch (type)
        {
            case 2:
                speedX = 6;
                img = ImageHub.rotateImage(ImageHub.laserImage, 45);
                break;
            case 3:
                speedX = -6;
                img = ImageHub.rotateImage(ImageHub.laserImage, -45);
                break;
        }

        x = X;
        y = Y;

        width = img.getWidth();
        height = img.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
        return new Object[] {ImageHub.laserImage};
    }

    @Override
    public void intersectionPlayer() {
        createSmallTripleExplosion();
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y += BULLET_BOSS_SPEED;
        x -= speedX;

        if (y > Game.screenHeight | x < -height | x > Game.screenWidth) {
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(img, x, y, null);
    }
}
