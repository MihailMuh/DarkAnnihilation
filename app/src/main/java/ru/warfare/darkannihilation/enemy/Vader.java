package ru.warfare.darkannihilation.enemy;

import static ru.warfare.darkannihilation.constant.Constants.VADER_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.VADER_HEALTH;
import static ru.warfare.darkannihilation.constant.Modes.GAME;
import static ru.warfare.darkannihilation.constant.NamesConst.SUPER;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.HardThread;

public class Vader extends Sprite {
    public Vader(Game game) {
        super(game, ImageHub.vaderImages[randInt(0, 2)]);

        damage = VADER_DAMAGE;

        makeParams();
        calculateBarriers();
        lock = true;
    }

    @Override
    public void start() {
        if (game.boss != null) {
            lock = true;
        } else {
            health = VADER_HEALTH;
            x = randInt(0, screenWidthWidth);
            y = -height;
            speedX = randInt(-5, 5);
            speedY = randInt(3, 10);

            super.start();
        }
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
    public void hide() {
        if (Game.gameStatus == GAME) {
            Game.score++;
        }
        start();
    }

    @Override
    public void kill() {
        HardThread.doInBackGround(() -> {
            createLargeExplosion();
            hide();
        });
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

        if (x < -width || x > SCREEN_WIDTH || y > SCREEN_HEIGHT) {
            HardThread.doInBackGround(this::start);
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);
    }
}
