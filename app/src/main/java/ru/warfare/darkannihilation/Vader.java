package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

import static ru.warfare.darkannihilation.Constants.VADER_DAMAGE;
import static ru.warfare.darkannihilation.Constants.VADER_HEALTH;
import static ru.warfare.darkannihilation.MATH.randInt;

public class Vader extends Sprite {
    private final Bitmap img;

    public Vader() {
        super(ImageHub.eX75, ImageHub.eX75);

        img = ImageHub.vaderImage[randInt(0, 2)];

        init();
    }

    public Vader(boolean old) {
        super(ImageHub.eX75, ImageHub.eX75);

        img = ImageHub.vaderOldImage[randInt(0, 2)];

        init();
    }

    private void init() {
        damage = VADER_DAMAGE;

        newStatus();

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    public void newStatus() {
        if (Game.bosses.size() != 0) {
            lock = true;
        }
        health = VADER_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedX = randInt(-5, 5);
        speedY = randInt(3, 10);

        if (buff) {
            up();
        }
    }

    private void up() {
        speedX *= 3;
        speedY *= 3;
    }

    @Override
    public void buff() {
        buff = true;

        if (!lock) {
            up();
        }
    }

    @Override
    public void stopBuff() {
        speedX /= 3;
        speedY /= 3;
    }

    @Override
    public void intersection() {
        createLargeExplosion();
        if (Game.gameStatus == 0) {
            Game.score += 1;
        }
        newStatus();
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
        createSmallExplosion();
        newStatus();
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (goTO(x + 15, y + 15).intersect(bullet.getRect())) {
            if (bullet.damage < health) {
                health -= bullet.damage;
                bullet.intersection();
                if (health <= 0) {
                    intersection();
                }
            } else {
                intersection();
            }
        }
    }

    @Override
    public void empireStart() {
        lock = false;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight) {
            newStatus();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(img, x, y, Game.alphaPaint);
    }
}
