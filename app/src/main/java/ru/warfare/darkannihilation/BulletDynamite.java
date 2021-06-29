package ru.warfare.darkannihilation;

import android.graphics.Matrix;

import static ru.warfare.darkannihilation.Constants.BULLET_DYNAMITE_DAMAGE;

public class BulletDynamite extends Sprite {
    private final int rotSpeed = MATH.randInt(-10, 10);
    private final Matrix matrix = new Matrix();
    private boolean BOOM = false;
    private int frame;
    private long lastShoot = System.currentTimeMillis();

    public BulletDynamite(int X, int Y) {
        super(ImageHub.dynamiteImg.getWidth(), ImageHub.dynamiteImg.getHeight());
        damage = BULLET_DYNAMITE_DAMAGE;
        isPassive = true;
        isBullet = true;

        speedY = MATH.randInt(6, 9);

        x = X - halfWidth;
        y = Y - height;
    }

    @Override
    public void intersection() {
        if (!BOOM) {
            width = ImageHub.explosionLarge[0].getWidth();
            height = ImageHub.explosionLarge[0].getHeight();

            halfWidth = width / 2;
            halfHeight = height / 2;

            x -= halfWidth;
            y -= halfHeight;

            left = x;
            top = y;
            right = x + width;
            bottom = y + height;

            recreateRect(x + 40, y, right() - 40, bottom());

            frame = 0;

            damage = 2;

            BOOM = true;

            AudioHub.playDynamiteBoom();
        }
    }

    @Override
    public void update() {
        if (BOOM) {
            long now = System.currentTimeMillis();
            if (now - lastShoot > 33) {
                lastShoot = now;
                if (frame != 12) {
                    frame++;
                } else {
                    Game.bullets.remove(this);
                    Game.allSprites.remove(this);
                }
            }
        } else {
            y -= speedY;

            frame += rotSpeed;

            if (y < -height) {
                Game.bullets.remove(this);
                Game.allSprites.remove(this);
            }
        }
    }

    @Override
    public void render () {
        if (BOOM) {
            Game.canvas.drawBitmap(ImageHub.explosionLarge[frame], x, y, null);
        } else {
            matrix.reset();
            matrix.postRotate(frame, halfWidth, halfHeight);
            matrix.postTranslate(x, y);

            Game.canvas.drawBitmap(ImageHub.dynamiteImg, matrix, Game.scorePaint);
        }
    }
}
