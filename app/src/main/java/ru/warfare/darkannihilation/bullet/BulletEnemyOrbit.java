package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Vector;

import static ru.warfare.darkannihilation.Constants.BULLET_ENEMY_ORBIT_DAMAGE;

public class BulletEnemyOrbit extends Sprite {
    private final Vector vector = new Vector();
    private final Matrix matrix = new Matrix();
    private float X;
    private float Y;
    private float fly;

    public BulletEnemyOrbit(Object[] info) {
        super((Game) info[0]);

        damage = BULLET_ENEMY_ORBIT_DAMAGE;
        isPassive = true;
        isBullet = true;
        status = "fakeSaturn";

        X = (int) info[1];
        Y = (int) info[2];
        fly = (float) info[5];
        image = (Bitmap) info[7];

        width = image.getWidth();
        height = image.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = (int) X;
        top = (int) Y;
        right = (int) (X + width);
        bottom = (int) (Y + height);

        vector.len = (float) info[6];
        vector.rads = (float) info[4];
    }

    @Override
    public int centerX() {
        return (int) (X + halfWidth);
    }

    @Override
    public int centerY() {
        return (int) (Y + halfHeight);
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
        float[] speeds = vector.rotateVector();

        X += speeds[0] + game.player.speedX;
        Y += speeds[1] + game.player.speedY;

        vector.rads -= 0.03 - fly;
        fly += 0.000008;
    }

    @Override
    public void render() {
        matrix.reset();
        matrix.postRotate(vector.getAngle(), halfWidth, halfHeight);
        matrix.postTranslate(X, Y);

        Game.canvas.drawBitmap(image, matrix, null);
    }
}