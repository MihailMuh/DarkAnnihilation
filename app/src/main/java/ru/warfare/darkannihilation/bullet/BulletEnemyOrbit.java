package ru.warfare.darkannihilation.bullet;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_ENEMY_ORBIT_DAMAGE;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ORBIT;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;

public class BulletEnemyOrbit extends BaseBullet {
    private final Vector vector = new Vector();
    private final Matrix matrix = new Matrix();
    private float X;
    private float Y;
    private float fly;

    public BulletEnemyOrbit(Game game) {
        super(game, ImageHub.pauseButtonImg, BULLET_ENEMY_ORBIT_DAMAGE);
        name = BULLET_ORBIT;
    }

    @Override
    public void start(Object[] info) {
        if ((boolean) info[2]) {
            image = (Bitmap) info[6];
            makeParams();

            X = (float) info[0];
            Y = (float) info[1];
            fly = (float) info[4];

            left = (int) X;
            top = (int) Y;
            right = (int) (X + width);
            bottom = (int) (Y + height);

            vector.len = (float) info[5];
            vector.rads = (double) info[3];

            lock = false;
        } else {
            kill();
        }
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
    public void kill() {
        super.kill();
        lock = true;
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