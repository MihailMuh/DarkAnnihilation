package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BulletBoss extends Sprite {
    private Bitmap img;
    public int damage = 5;

    public BulletBoss(Game g, int X, int Y, int type) {
        super(g, ImageHub.laserImage.getWidth(), ImageHub.laserImage.getHeight());
        speedY = 6;

        Matrix matrix = new Matrix();
        switch (type)
        {
            case 1:
                img = ImageHub.laserImage;
                break;
            case 2:
                speedX = 4;
                matrix.postRotate(30);
                img = Bitmap.createBitmap(ImageHub.laserImage, 0, 0, width, height, matrix, ImageHub.isFilter);
                break;
            case 3:
                speedX = -4;
                matrix.postRotate(330);
                img = Bitmap.createBitmap(ImageHub.laserImage, 0, 0, width, height, matrix, ImageHub.isFilter);
                break;
        }

        x = X;
        y = Y;
    }

    @Override
    public void update() {
        y += speedY;
        x -= speedX;

        if (y > screenHeight | x < -100 | x > screenWidth) {
            game.bulletBosses.remove(this);
            game.numberBulletsBoss -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(img, x, y, null);
    }
}
