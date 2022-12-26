package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.ATOMIC_BOMB_FRAME_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_ATOMIC_BOMB_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.ROCKET_DAMAGE;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;
import static ru.warfare.darkannihilation.systemd.Game.now;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class AtomicBomb extends Sprite {
    private int frame = 0;
    private long lastFrame = now;
    private boolean BOOM;

    public AtomicBomb(Game game) {
        super(game, ImageHub.atomBombImage[0]);

        damage = ROCKET_DAMAGE;
        calculateBarriers();
        lock = true;

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    private void boom() {
        if (!BOOM) {
            BOOM = true;
            HardThread.createBlackHole(() -> {
                for (Sprite sprite : game.enemy) {
                    if (!sprite.lock) {
                        sprite.kill();
                    }
                }

                for (BaseBullet bullet : Game.bulletsEnemy) {
                    if (bullet.name == BULLET_ENEMY) {
                        bullet.kill();
                    }
                }
            });
        }
    }

    @Override
    public void start() {
        BOOM = false;
        speedY = 1;
        health = 20;
        x = Randomize.randInt(0, screenWidthWidth);
        y = -height;
        lock = false;

        super.start();
    }

    @Override
    public void onBuff() {
        speedY = 3;
    }

    @Override
    public void onStopBuff() {
        speedY = 1;
    }

    @Override
    public void kill() {
        intersectionPlayer();
        Game.score += 50;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        lock = true;
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            bullet.kill();
            health -= bullet.damage;
            if (health <= 0) {
                boom();
            }
        }
    }

    @Override
    public void update() {
        y += speedY;
        if (now - lastFrame > ATOMIC_BOMB_FRAME_TIME) {
            lastFrame = now;
            frame++;
            if (frame == NUMBER_ATOMIC_BOMB_IMAGES) {
                frame = 0;
            }
        }

        if (y > SCREEN_HEIGHT) {
            lock = true;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.atomBombImage[frame], x, y, Game.alphaEnemy);
    }
}
