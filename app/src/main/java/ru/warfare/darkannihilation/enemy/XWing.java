package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.BaseEnemy;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.SickGameTask;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.XWING_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.XWING_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.XWING_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.getDistance;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;
import static ru.warfare.darkannihilation.systemd.service.Windows.calculate;

public class XWing extends BaseEnemy {
    private final SickGameTask gameTask = new SickGameTask(this::shoot, XWING_SHOOT_TIME);
    private static final int R = calculate(500);

    public XWing(Game game) {
        super(game, ImageHub.XWingImg, XWING_DAMAGE);
        calculateBarriers();
        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    @Override
    public void shoot() {
        if (getDistance(centerX() - game.player.centerX(),
                centerY() - game.player.centerY()) < R) {
            bulletEnemy(9);
            AudioHub.playShoot();
        }
    }

    @Override
    public void start() {
        if (game.boss != null) {
            lock = true;
            gameTask.stop();
        } else {
            health = XWING_HEALTH;
            x = randInt(0, screenWidthWidth);
            y = -height;
            speedX = randInt(-3, 3);
            speedY = randInt(1, 8);

            super.start();
        }
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