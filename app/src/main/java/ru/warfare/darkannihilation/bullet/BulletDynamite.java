package ru.warfare.darkannihilation.bullet;

import android.graphics.Matrix;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_DYNAMITE_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SKULL_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.NamesConst.SUPER;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

public class BulletDynamite extends BaseBullet {
    private final int rotSpeed = randInt(-10, 10);
    private final Matrix matrix = new Matrix();
    private boolean BOOM = false;
    private boolean ready = false;
    private int frame;
    private long lastShoot = System.currentTimeMillis();

    public BulletDynamite(Game game, int X, int Y) {
        super(game, ImageHub.dynamiteImg, X, Y, BULLET_DYNAMITE_DAMAGE);

        speedY = randInt(6, 9);

        y = Y - height;
    }

    @Override
    public void kill() {
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

                AudioHub.playDynamiteBoom();

                ready = true;
            });
        }
    }

    @Override
    public void hide() {
        game.bullets.remove(this);
    }

    @Override
    public void update() {
        if (!BOOM) {
            y -= speedY;
            frame += rotSpeed;

            if (y < -height) {
                hide();
            }
        }
        if (ready) {
            long now = System.currentTimeMillis();
            if (now - lastShoot > 33) {
                lastShoot = now;
                frame++;
                if (frame == NUMBER_SKULL_EXPLOSION_IMAGES) {
                    hide();
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
