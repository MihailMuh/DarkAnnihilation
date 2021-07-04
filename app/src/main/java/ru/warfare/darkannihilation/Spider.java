package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.SPIDER_DAMAGE;
import static ru.warfare.darkannihilation.Constants.SPIDER_HEALTH;
import static ru.warfare.darkannihilation.Constants.SPIDER_HEALTH_BAR_HEIGHT;
import static ru.warfare.darkannihilation.Constants.SPIDER_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.Constants.SPIDER_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.SPIDER_SPEED;
import static ru.warfare.darkannihilation.MATH.randInt;

public class Spider extends Sprite {
    private int shootTripleTime;
    private long lastShoot = System.currentTimeMillis();

    private int ammo;
    private boolean reload;
    private boolean isShoot = false;
    private float hp = 10;

    private static int startBarWhiteY;
    private static int startBarRedY;
    private static int endBarWhiteY;
    private static int endBarRedY;

    public Spider() {
        super(ImageHub.spiderImg.getWidth(), ImageHub.spiderImg.getHeight());
        new Thread(() -> {
            damage = SPIDER_DAMAGE;

            hide();

            recreateRect(x + 25, y + 5, right() - 5, centerY() + (halfHeight / 2));

            while (y < 35) {
                y += SPIDER_SPEED;
            }
            startBarWhiteY = y + 10;
            startBarRedY = startBarWhiteY + 2;
            endBarWhiteY = startBarWhiteY + SPIDER_HEALTH_BAR_HEIGHT;
            endBarRedY = endBarWhiteY - 2;

            y = -height;
        }).start();
    }

    private void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;
            if (!reload) {
                if (HardThread.job == 0) {
                    HardThread.x = centerX();
                    HardThread.y = centerY();
                    HardThread.job = 1;
                    ammo++;
                    reload = false;
                }
                if (ammo >= 20) {
                    reload = true;
                    shootTripleTime = SPIDER_SHOOT_TIME * 2;
                }
            } else {
                ammo--;
                if (ammo == 0) {
                    shootTripleTime = SPIDER_SHOOT_TIME;
                    reload = false;
                }
            }
        }
    }

    public void start() {
        lock = false;
        if (buff) {
            shootTripleTime /= 2;
        }
    }

    private void hide() {
        hp = 10;
        lock = true;
        reload = false;
        isShoot = false;
        ammo = 0;
        health = SPIDER_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        shootTripleTime = SPIDER_SHOOT_TIME;
    }

    @Override
    public void buff() {
        buff = true;
        if (!lock) {
            shootTripleTime /= 2;
        }
    }

    @Override
    public void stopBuff() {
        shootTripleTime = SPIDER_SHOOT_TIME;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 25, y + 5);
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
    public void update() {
        if (y < 35) {
            y += SPIDER_SPEED;
        } else {
            if (!isShoot) {
                isShoot = true;
            }
            shoot();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.spiderImg, x, y, Game.alphaEnemy);

        if (hp > 4) {
            hp = ((health / (float) SPIDER_HEALTH) * SPIDER_HEALTH_BAR_LEN);
        } else {
            hp = 4;
        }
        if (!isShoot) {
            Game.canvas.drawRect(centerX() - 75, y + 10, centerX() + 75, y + 25 , Game.scorePaint);
            Game.canvas.drawRect(centerX() - 73, y + 12, centerX() - 77 + hp, y + 23, Game.topPaintRed);
        } else {
            Game.canvas.drawRect(centerX() - 75, startBarWhiteY, centerX() + 75, endBarWhiteY, Game.scorePaint);
            Game.canvas.drawRect(centerX() - 73, startBarRedY, centerX() - 77 + hp, endBarRedY, Game.topPaintRed);
        }
    }
}
