package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.BaseEnemy;
import ru.warfare.darkannihilation.thread.GameTask;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.SUNRISE_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.SUNRISE_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.SUNRISE_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randBoolean;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

public class Sunrise extends BaseEnemy {
    private final GameTask gameTask = new GameTask(this::shoot, SUNRISE_SHOOT_TIME);
    private boolean field;
    private boolean left = false;

    public Sunrise(Game game) {
        super(game, ImageHub.sunriseImg, SUNRISE_DAMAGE);
        vector.len = 11;

        calculateBarriers();
        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    @Override
    public void shoot() {
        int X = centerX();
        int Y = centerY();

        for (int i = 0; i < 360; i += 18) {
            int[] values = vector.rotateVector(i);
            bulletEnemy(X, Y, new int[] {values[0], values[1], i + 90});
        }

//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 0, 0, -10));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 90, 10, 0));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 180, 0, 10));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, -90, -10, 0));
//
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 45, 7, -7));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 135, 7, 7));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, -45, -7, -7));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, -135, -7, 7));
//
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 67, 10, -4));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 22, 4, -10));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, -67, -10, -4));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, -22, -4, -10));
//
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 157, 4, 10));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, 113, 10, 4));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, -157, -4, 10));
//        game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, -113, -10, 4));

        AudioHub.playDeagle();
    }

    @Override
    public void start() {
        field = false;
        health = SUNRISE_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedX = randInt(2, 4);
        speedY = randInt(2, 4);
        left = randBoolean();
        lock = false;

        super.start();

        gameTask.start();
    }

    @Override
    public void onBuff() {
        speedX *= 3;
        speedY *= 3;
    }

    @Override
    public void onStopBuff() {
        speedX /= 3;
        speedY /= 3;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        gameTask.stop();
        createSkullExplosion();
        lock = true;
    }

    @Override
    public void kill() {
        intersectionPlayer();
        Game.score += 100;
    }

    @Override
    public void update() {
        if (y > 0 && !field) {
            field = true;
        }
        if (x <= 0) {
            left = true;
        }
        if (x >= screenWidthWidth) {
            left = false;
        }
        if ((y >= screenHeightHeight) || (field && y <= 0)) {
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