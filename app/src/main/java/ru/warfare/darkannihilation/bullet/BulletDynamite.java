package ru.warfare.darkannihilation.bullet;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_DYNAMITE_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SKULL_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.Game.now;

import android.graphics.Matrix;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.HardThread;

public class BulletDynamite extends BaseBullet {
    private int rotSpeed;
    private final Matrix matrix = new Matrix();
    private boolean BOOM;
    private boolean ready;
    private int frame;
    private long lastShoot = now;

    public BulletDynamite(Game game) {
        super(game, ImageHub.dynamiteImg, BULLET_DYNAMITE_DAMAGE);
    }

    @Override
    public void start(int X, int Y) {
        if (damage != 1) {
            image = ImageHub.dynamiteImg;
            makeParams();

            ready = false;
            BOOM = false;

            damage = 1;
        }

        x = X - halfWidth;
        y = Y - height;

        speedY = randInt(6, 9);
        rotSpeed = randInt(-10, 10);

        lock = false;
    }

    @Override
    public void kill() {
        if (!BOOM) {
            BOOM = true;
            HardThread.createBlackHole(() -> {
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

                recreateRect(x + 10, y, right() - 10, bottom());

                damage = 2;
                frame = 0;

                AudioHub.playDynamiteBoom();

                ready = true;
            });
        }
    }

    @Override
    public void update() {
        if (!BOOM) {
            y -= speedY;
            frame += rotSpeed;

            if (y < -height) {
                lock = true;
            }
        }
        if (ready) {
            if (now - lastShoot > 33) {
                lastShoot = now;
                frame++;
                if (frame == NUMBER_SKULL_EXPLOSION_IMAGES) {
                    HardThread.closeBlackHole();
                    lock = true;
                }
            }
        }
    }

    @Override
    public void render() {
        if (!BOOM) {
            matrix.reset();
            matrix.postRotate(frame, halfWidth, halfHeight);
            matrix.postTranslate(x, y);

            Game.canvas.drawBitmap(ImageHub.dynamiteImg, matrix, null);
        }
        if (ready) {
            Game.canvas.drawBitmap(ImageHub.explosionLarge[frame], x, y, null);
        }
    }
}
