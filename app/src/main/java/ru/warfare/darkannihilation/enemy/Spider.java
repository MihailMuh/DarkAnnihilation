package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.bullet.BulletEnemy;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.SPIDER_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.SPIDER_SPEED;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.Game.now;

public class Spider extends Sprite {
    private final Vector vector = new Vector();

    private int shootTripleTime;
    private long lastShoot = now;

    private int ammo;
    private boolean reload;
    private float hp = 10;

    public Spider(Game game) {
        super(game, ImageHub.spiderImg);
        damage = SPIDER_DAMAGE;

        calculateBarriers();
        lock = true;

        recreateRect(x + 25, y + 5, right() - 5, centerY() + (halfHeight / 2));
    }

    private void shoot() {
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;
            HardThread.doInBackGround(() -> {
                if (!reload) {
                    int X = centerX();
                    int Y = centerY();
                    int[] values = vector.dirtyVector(X, Y, game.player.centerX(),
                            game.player.centerY(), 13, randInt(-20, 20));
                    game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, values[2], values[0], values[1]));
                    AudioHub.playShotgun();
                    ammo++;
                    if (ammo == 30) {
                        reload = true;
                    }
                } else {
                    ammo--;
                    if (ammo == 0) {
                        reload = false;
                    }
                }
            });
        }
    }

    @Override
    public void start() {
        hide();
        lock = false;

        super.start();
    }

    @Override
    public void onBuff() {
        shootTripleTime /= 2;
    }

    @Override
    public void onStopBuff() {
        shootTripleTime = SPIDER_SHOOT_TIME;
    }

    @Override
    public void kill() {
        intersectionPlayer();
        Game.score += 50;
    }

    @Override
    public void hide() {
        hp = SPIDER_HEALTH_BAR_LEN;
        reload = false;
        ammo = 0;
        health = SPIDER_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        shootTripleTime = SPIDER_SHOOT_TIME;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 25, y + 5);
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        lock = true;
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.kill();
            if (hp > 4) {
                hp = ((health / (float) SPIDER_HEALTH) * SPIDER_HEALTH_BAR_LEN);
            } else {
                hp = 4;
            }
            if (health <= 0) {
                kill();
            }
        }
    }

    @Override
    public void update() {
        if (y < 35) {
            y += SPIDER_SPEED;
        } else {
            shoot();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);

        Game.canvas.drawRect(centerX() - 75, y + 10, centerX() + 75, y + 25 , Game.scorePaint);
        Game.canvas.drawRect(centerX() - 73, y + 12, centerX() - 77 + hp, y + 23, Game.fpsPaint);
    }
}