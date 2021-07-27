package ru.warfare.darkannihilation.bullet;

import android.graphics.Matrix;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_DYNAMITE_DAMAGE;

public class BulletDynamite extends Sprite {
    private final int rotSpeed = Math.randInt(-10, 10);
    private final Matrix matrix = new Matrix();
    private boolean BOOM = false;
    private boolean ready = false;
    private int frame;
    private long lastShoot = System.currentTimeMillis();

    public BulletDynamite(Game game, int X, int Y) {
        super(game, ImageHub.dynamiteImg);
        damage = BULLET_DYNAMITE_DAMAGE;
        isPassive = true;
        isBullet = true;

        speedY = Math.randInt(6, 9);

        x = X - halfWidth;
        y = Y - height;
    }

    @Override
    public void intersection() {
        if (!BOOM) {
            BOOM = true;
            HardThread.doInBackGround(() -> {
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

                damage = 2;
                frame = 0;
                ready = true;

                AudioHub.playDynamiteBoom();
            });
        }
    }

    @Override
    public void kill() {
        super.kill();
        game.bullets.remove(this);
    }

    @Override
    public void update() {
        if (!ready) {
            y -= speedY;

            frame += rotSpeed;

            if (y < -height) {
                kill();
            }
        } else {
            long now = System.currentTimeMillis();
            if (now - lastShoot > 33) {
                lastShoot = now;
                if (frame != 12) {
                    frame++;
                } else {
                    kill();
                }
            }
        }
    }

    @Override
    public void render() {
        if (!ready) {
            matrix.reset();
            matrix.postRotate(frame, halfWidth, halfHeight);
            matrix.postTranslate(x, y);

            Game.canvas.drawBitmap(ImageHub.dynamiteImg, matrix, null);
        } else {
            Game.canvas.drawBitmap(ImageHub.explosionLarge[frame], x, y, null);
        }
    }
}
