package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletEnemy;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.SUNRISE_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.SUNRISE_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.SUNRISE_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class Sunrise extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private boolean field;
    private boolean left = false;

    public Sunrise(Game game) {
        super(game, ImageHub.sunriseImg);
        damage = SUNRISE_DAMAGE;

        calculateBarriers();
        hide();

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > SUNRISE_SHOOT_TIME) {
            lastShoot = now;
            HardThread.doInBackGround(() -> {
                int X = centerX();
                int Y = centerY();

                game.allSprites.add(new BulletEnemy(game, X, Y, 0, 0, -10));
                game.allSprites.add(new BulletEnemy(game, X, Y, 90, 10, 0));
                game.allSprites.add(new BulletEnemy(game, X, Y, 180, 0, 10));
                game.allSprites.add(new BulletEnemy(game, X, Y, -90, -10, 0));

                game.allSprites.add(new BulletEnemy(game, X, Y, 45, 7, -7));
                game.allSprites.add(new BulletEnemy(game, X, Y, 135, 7, 7));
                game.allSprites.add(new BulletEnemy(game, X, Y, -45, -7, -7));
                game.allSprites.add(new BulletEnemy(game, X, Y, -135, -7, 7));

                game.allSprites.add(new BulletEnemy(game, X, Y, 67, 10, -4));
                game.allSprites.add(new BulletEnemy(game, X, Y, 22, 4, -10));
                game.allSprites.add(new BulletEnemy(game, X, Y, -67, -10, -4));
                game.allSprites.add(new BulletEnemy(game, X, Y, -22, -4, -10));

                game.allSprites.add(new BulletEnemy(game, X, Y, 157, 4, 10));
                game.allSprites.add(new BulletEnemy(game, X, Y, 113, 10, 4));
                game.allSprites.add(new BulletEnemy(game, X, Y, -157, -4, 10));
                game.allSprites.add(new BulletEnemy(game, X, Y, -113, -10, 4));

                AudioHub.playDeagle();
            });
        }
    }

    public void start() {
        lock = false;
        if (buff) {
            up();
        }
    }

    private void hide() {
        field = false;
        health = SUNRISE_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedX = randInt(2, 4);
        speedY = randInt(2, 4);
        left = randInt(0, 1) == 1;
        lock = true;
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
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersection() {
        intersectionPlayer();
        Game.score += 100;
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        hide();
    }

    @Override
    public void update() {
        if (y > 0 & !field) {
            field = true;
        }
        if (y > -halfHeight) {
            shoot();
        }
        if (x <= 0) {
            left = true;
        }
        if (x >= screenWidthWidth) {
            left = false;
        }
        if ((y >= screenHeightHeight) | (field & y <= 0)) {
            speedY = -speedY;
        }
        if (left) {
            x += speedX;
        } else {
            x -= speedX;
        }
        y += speedY;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);
    }
}