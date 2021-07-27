package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.VADER_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.VADER_HEALTH;
import static ru.warfare.darkannihilation.math.Math.randInt;

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
        newStatus();

        recreateRect(x + 10, y + 10, right() - 10, bottom() - 10);
    }

    public void newStatus() {
        if (game.bosses.size() != 0) {
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
        if (intersect(bullet)) {
            if (bullet.damage != BUCKSHOT_DAMAGE) {
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
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);
    }
}
