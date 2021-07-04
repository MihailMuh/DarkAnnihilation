package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.ATOMIC_BOMB_FRAME_TIME;
import static ru.warfare.darkannihilation.Constants.NUMBER_ATOMIC_BOMB_IMAGES;
import static ru.warfare.darkannihilation.Constants.ROCKET_DAMAGE;

public class AtomicBomb extends Sprite {
    private int frame = 0;
    private static final int len = NUMBER_ATOMIC_BOMB_IMAGES - 1;
    private long lastFrame = System.currentTimeMillis();
    private boolean boom = false;

    public AtomicBomb() {
        super(ImageHub.atomBombImage[0].getWidth(), ImageHub.atomBombImage[0].getHeight());

        damage = ROCKET_DAMAGE;
        hide();

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    private void boom() {
        if (HardThread.job == 0) {
            HardThread.job = 7;
            boom = false;
            intersection();
        }
    }

    public void start() {
        lock = false;
        if (buff) {
            up();
        }
    }

    private void hide() {
        lock = true;
        speedY = 1;
        health = 20;
        x = MATH.randInt(0, Game.screenWidth);
        y = -height;
    }

    private void up() {
        speedY = 3;
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
        speedY = 1;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersection() {
        intersectionPlayer();
        Game.score += 50;
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        hide();
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (intersect(bullet)) {
            bullet.intersection();
            if (!boom) {
                health -= bullet.damage;
                if (health <= 0) {
                    boom = true;
                }
            }
        }
    }

    @Override
    public void update() {
        if (boom) {
            boom();
        } else {
            long now = System.currentTimeMillis();
            if (now - lastFrame > ATOMIC_BOMB_FRAME_TIME) {
                lastFrame = now;
                if (frame != len) {
                    frame++;
                } else {
                    frame = 0;
                }
            }
            y += speedY;

            if (y > Game.screenHeight) {
                hide();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.atomBombImage[frame], x, y, Game.alphaEnemy);
    }
}
