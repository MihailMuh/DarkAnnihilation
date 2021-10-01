package ru.warfare.darkannihilation.enemy;

import static ru.warfare.darkannihilation.constant.Constants.TRIPLE_FIGHTER_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.TRIPLE_FIGHTER_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.TRIPLE_FIGHTER_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseEnemy;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.SickGameTask;

public class TripleFighter extends BaseEnemy {
    private final SickGameTask gameTask = new SickGameTask(this::shoot, TRIPLE_FIGHTER_SHOOT_TIME);

    public TripleFighter(Game game) {
        super(game, ImageHub.tripleFighterImg, TRIPLE_FIGHTER_DAMAGE);
        calculateBarriers();

        recreateRect(x + 5, y + 5, right() - 5, bottom() - 5);
    }

    @Override
    public void shoot() {
        if (x > 0 && x < screenWidthWidth && y > 0 && y < screenHeightHeight) {
            bulletEnemy(game.player.centerX(), game.player.centerY(), 13);
            AudioHub.playShotgun();
        }
    }

    @Override
    public void hide() {
        if (game.boss != null) {
            lock = true;
            gameTask.stop();
        } else {
            health = TRIPLE_FIGHTER_HEALTH;
            x = randInt(0, screenWidthWidth);
            y = -height;
            speedX = randInt(-3, 3);
            speedY = randInt(1, 10);
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 5, y + 5);
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
        createSmallExplosion();
        hide();
    }

    @Override
    public void kill() {
        createLargeTripleExplosion();
        Game.score += 5;
        hide();
    }

    @Override
    public void empireStart() {
        hide();
        lock = false;
        gameTask.start();
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (x < -width || x > SCREEN_WIDTH || y > SCREEN_HEIGHT) {
            hide();
        }
    }
}
