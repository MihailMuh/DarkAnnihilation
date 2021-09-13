package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.bullet.BulletEnemy;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.GameTask;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_LARGE_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.XWING_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.XWING_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.XWING_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.getDistance;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;
import static ru.warfare.darkannihilation.systemd.service.Windows.calculate;

public class XWing extends Sprite {
    private final GameTask gameTask = new GameTask(this::shoot, XWING_SHOOT_TIME);
    private static final int R = calculate(500);
    private final Vector vector = new Vector();

    public XWing(Game game) {
        super(game, ImageHub.XWingImg);
        damage = XWING_DAMAGE;

        calculateBarriers();

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    private void shoot() {
        int P_X = game.player.centerX();
        int P_Y = game.player.centerY();
        int X = centerX();
        int Y = centerY();
        if (getDistance(X - P_X, Y - P_Y) < R) {
            int[] values = vector.vector(X, Y, P_X, P_Y, 9);
            game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, values[2], values[0], values[1]));
            AudioHub.playShoot();
        }
    }

    @Override
    public void start() {
        if (game.boss != null) {
            lock = true;
            gameTask.stop();
        }
        health = XWING_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 8);

        super.start();
    }

    @Override
    public void onBuff() {
        speedX *= 2;
        speedY *= 2;
    }

    @Override
    public void onStopBuff() {
        speedX /= 2;
        speedY /= 2;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
        createSmallExplosion();
        start();
    }

    @Override
    public void kill() {
        createLargeTripleExplosion();
        Game.score += 10;
        start();
    }

    @Override
    public void killInBack() {
        AudioHub.playBoom();
        for (int i = 0; i < NUMBER_TRIPLE_LARGE_EXPLOSION; i++) {
            if (game.tripleLargeExplosion[i].lock) {
                game.tripleLargeExplosion[i].start(centerX(), centerY());
                break;
            }
        }

        Game.score += 10;
        start();
    }

    @Override
    public void empireStart() {
        start();
        lock = false;
        gameTask.start();
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (x < -width || x > SCREEN_WIDTH || y > SCREEN_HEIGHT) {
            start();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);
    }
}