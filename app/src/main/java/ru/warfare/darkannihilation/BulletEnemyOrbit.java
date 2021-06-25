package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BulletEnemyOrbit extends Sprite {
    private final Vector vector = new Vector();
    private final Matrix matrix = new Matrix();
    private final Bitmap image;
    private float X;
    private float Y;
    private float fly;
    private double deg;

    public BulletEnemyOrbit(Object[] info) {
        super((Game) info[0], ImageHub.bulletEnemyImage.getWidth(), ImageHub.bulletEnemyImage.getHeight());

        damage = 1;
        isPassive = true;
        isBullet = true;

        X = (float) info[1];
        Y = (float) info[2];
        deg = (double) info[4];
        fly = (float) info[5];
        image = (Bitmap) info[7];

        left = (int) X;
        top = (int) Y;
        right = (int) (X + width);
        bottom = (int) (Y + height);

        vector.makeVector((int) X, (int) Y, (int) X, 0, (double) info[6]);
    }

    @Override
    public int centerX() {
        return (int) (X + halfWidth);
    }

    @Override
    public int centerY() {
        return (int) (Y + halfHeight);
    }

    private float getAngle() {
        double yy = game.player.y - Y;
        double xx = game.player.x - X;
        double R = getDistance(yy, xx);
        double b = getDistance(xx + R, yy);
        double a = 2 * R * R;
        double cos = (a - (b * b)) / a;
        if (cos <= -1) {
            return 180;
        }
        if (yy < 0) {
            b = getDistance(xx - R, yy);
            return (float) Math.toDegrees(Math.acos((a - (b * b)) / a)) + 180;
        }
        return (float) Math.toDegrees(Math.acos(cos));
    }

    @Override
    public Sprite getRect() {
        right += X - left;
        bottom += Y - top;
        left = (int) X;
        top = (int) Y;
        return this;
    }

    @Override
    public void intersection() {
        createSmallExplosion();
        Game.bullets.remove(this);
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        double[] speeds = vector.rotateVector(deg);

        X += speeds[0] + game.player.speedX;
        Y += speeds[1] + game.player.speedY;

        deg += (0.035 - fly);
        fly += 0.000007;
    }

    @Override
    public void render () {
        matrix.reset();
        matrix.postRotate(-getAngle(), halfWidth, halfHeight);
        matrix.postTranslate(X, Y);

        Game.canvas.drawBitmap(image, matrix, null);
    }
}