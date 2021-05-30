package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BulletBoss extends Sprite {
    private Bitmap img;

    public BulletBoss(int X, int Y, int type) {
        super(ImageHub.laserImage.getWidth(), ImageHub.laserImage.getHeight());
        speedY = 6;
        damage = 5;
        status = "bulletEnemy";
        isBullet = true;

        Matrix matrix = new Matrix();
        switch (type)
        {
            case 1:
                img = ImageHub.laserImage;
                break;
            case 2:
                speedX = 6;
                matrix.postRotate(45);
                img = Bitmap.createBitmap(ImageHub.laserImage, 0, 0, width, height, matrix, ImageHub.isFilter);
                break;
            case 3:
                speedX = -6;
                matrix.postRotate(-45);
                img = Bitmap.createBitmap(ImageHub.laserImage, 0, 0, width, height, matrix, ImageHub.isFilter);
                break;
        }

        x = X;
        y = Y;
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
        y += speedY;
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
