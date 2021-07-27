package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.bullet.BulletEnemy;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.SPIDER_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_HEALTH_BAR_HEIGHT;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_SPEED;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class Spider extends Sprite {
    private final Vector vector = new Vector();

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

    public Spider(Game game) {
        super(game, ImageHub.spiderImg);
        damage = SPIDER_DAMAGE;

        calculateBarriers();
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
    }

    private void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;
            HardThread.doInBackGround(() -> {
                if (!reload) {
                    int X = centerX();
                    int Y = centerY();
                    int[] values = vector.vector(X, Y, game.player.centerX(), game.player.centerY(), 13);
                    game.allSprites.add(new BulletEnemy(game, X, Y, values[2], values[0], values[1]));
                    AudioHub.playShotgun();
                    ammo++;
                    reload = false;
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
            });
        }
    }

    public void start() {
        lock = false;
        if (buff) {
            shootTripleTime /= 2;
        }
    }

    private void hide() {
        hp = SPIDER_HEALTH_BAR_LEN;
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
    public void check_intersectionBullet(Sprite bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.intersection();
            if (hp > 4) {
                hp = ((health / (float) SPIDER_HEALTH) * SPIDER_HEALTH_BAR_LEN);
            } else {
                hp = 4;
            }
            if (health <= 0) {
                intersection();
            }
        }
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
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);

        if (!isShoot) {
            Game.canvas.drawRect(centerX() - 75, y + 10, centerX() + 75, y + 25 , Game.scorePaint);
            Game.canvas.drawRect(centerX() - 73, y + 12, centerX() - 77 + hp, y + 23, Game.topPaintRed);
        } else {
            Game.canvas.drawRect(centerX() - 75, startBarWhiteY, centerX() + 75, endBarWhiteY, Game.scorePaint);
            Game.canvas.drawRect(centerX() - 73, startBarRedY, centerX() - 77 + hp, endBarRedY, Game.topPaintRed);
        }
    }
}
