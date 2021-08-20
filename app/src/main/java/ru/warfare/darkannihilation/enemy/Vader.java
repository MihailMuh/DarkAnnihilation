package ru.warfare.darkannihilation.enemy;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_LARGE_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.VADER_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.VADER_HEALTH;
import static ru.warfare.darkannihilation.constant.Modes.GAME;
import static ru.warfare.darkannihilation.constant.NamesConst.SUPER;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;

public class Vader extends Sprite {
    public Vader(Game game, boolean old) {
        super(game);

        if (old) {
            image = ImageHub.vaderOldImage[randInt(0, 2)];
        } else {
            image = ImageHub.vaderImage[randInt(0, 2)];
        }

        damage = VADER_DAMAGE;

        makeParams();
        calculateBarriers();
        start();

        recreateRect(x + 10, y + 10, right() - 10, bottom() - 10);
    }

    @Override
    public void start() {
        if (game.boss != null) {
            lock = true;
        }
        health = VADER_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedX = randInt(-5, 5);
        speedY = randInt(3, 10);

        super.start();
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
    public void intersectionPlayer() {
        AudioHub.playMetal();
        createSmallExplosion();
        start();
    }

    @Override
    public void kill() {
        createLargeExplosion();
        if (Game.gameStatus == GAME) {
            Game.score++;
        }
        start();
    }

    @Override
    public void killInBack() {
        AudioHub.playBoom();
        for (int i = 0; i < NUMBER_DEFAULT_LARGE_EXPLOSION; i++) {
            if (game.defaultLargeExplosion[i].lock) {
                game.defaultLargeExplosion[i].start(centerX(), centerY());
                break;
            }
        }

        if (Game.gameStatus == GAME) {
            Game.score++;
        }
        start();
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            if (bullet.power != SUPER) {
                health -= bullet.damage;
                bullet.kill();
                if (health <= 0) {
                    kill();
                }
            } else {
                kill();
            }
        }
    }

    @Override
    public void empireStart() {
        start();
        lock = false;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (x < -width | x > SCREEN_WIDTH | y > SCREEN_HEIGHT) {
            start();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);
    }
}
