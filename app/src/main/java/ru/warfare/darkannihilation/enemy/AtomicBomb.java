package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.Constants.ATOMIC_BOMB_FRAME_TIME;
import static ru.warfare.darkannihilation.Constants.NUMBER_ATOMIC_BOMB_IMAGES;
import static ru.warfare.darkannihilation.Constants.ROCKET_DAMAGE;

public class AtomicBomb extends Sprite {
    private int frame = 0;
    private static final int len = NUMBER_ATOMIC_BOMB_IMAGES - 1;
    private long lastFrame = System.currentTimeMillis();
    private boolean BOOM;

    public AtomicBomb() {
        super(ImageHub.atomBombImage[0]);

        damage = ROCKET_DAMAGE;
        hide();

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    private void boom() {
        if (!BOOM) {
            BOOM = true;
            HardThread.newJob(() -> {
                for (int i = 0; i < Game.allSprites.size(); i++) {
                    Sprite sprite = Game.allSprites.get(i);
                    if (!sprite.lock) {
                        if ((!sprite.isPassive && !sprite.isBullet) | (sprite.status.equals("bulletEnemy"))) {
                            sprite.intersection();
                        }
                    }
                }
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
        BOOM = false;
        lock = true;
        speedY = 1;
        health = 20;
        x = Math.randInt(0, screenWidthWidth);
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
            health -= bullet.damage;
        }
    }

    @Override
    public void update() {
        y += speedY;
        long now = System.currentTimeMillis();
        if (now - lastFrame > ATOMIC_BOMB_FRAME_TIME) {
            lastFrame = now;
            if (frame != len) {
                frame++;
            } else {
                frame = 0;
            }
        }

        if (y > Game.screenHeight) {
            hide();
        }

        if (health <= 0) {
            boom();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.atomBombImage[frame], x, y, Game.alphaEnemy);
    }
}
