package ru.warfare.darkannihilation.enemy.boss;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletBossVaders;
import ru.warfare.darkannihilation.enemy.Portal;
import ru.warfare.darkannihilation.enemy.Vader;
import ru.warfare.darkannihilation.enemy.XWing;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.Constants.BOSS_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.Constants.BOSS_VADERS_HEALTH;
import static ru.warfare.darkannihilation.Constants.BOSS_VADERS_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class BossVaders extends Sprite {
    private final Vector vector = new Vector();
    private long lastShoot = System.currentTimeMillis();
    private float hp = BOSS_HEALTH_BAR_LEN;

    private boolean field = false;
    private boolean left = false;
    private boolean BOOM = false;

    public BossVaders(Game g) {
        super(g, ImageHub.bossVadersImg);

        health = BOSS_VADERS_HEALTH;
        speedY = 1;
        speedX = 5;
        if (randInt(0, 1) == 1) {
            left = true;
        }
        isPassive = true;

        x = randInt(0, screenWidthWidth);
        y = -800;

        recreateRect(x + 35, y + 20, right() - 35, bottom() - 20);
    }

    public void shoot() {
        if (System.currentTimeMillis() - lastShoot > BOSS_VADERS_SHOOT_TIME) {
            lastShoot = System.currentTimeMillis();
            HardThread.newJob(() -> {
                int X = centerX();
                int Y = centerY();
                int[] values = vector.easyVector(X, Y, game.player.centerX(),
                        game.player.centerY(), 10);
                Game.allSprites.add(new BulletBossVaders(X, Y, values[0], values[1]));
                AudioHub.playBossShoot();
            });
        }
    }

    @Override
    public void buff() {
        speedX *= 2;
        speedY *= 2;
    }

    @Override
    public void stopBuff() {
        speedX /= 2;
        speedY /= 2;
    }

    public void killAfterFight() {
        if (!BOOM) {
            BOOM = true;
            HardThread.newJob(() -> {
                Game.score += 400;
                AudioHub.pauseBossMusic();
                int len = Game.numberVaders / 4;
                for (int i = 0; i < len; i++) {
                    if (Game.random.nextFloat() <= 0.3) {
                        Game.allSprites.add(new XWing(game));
                    } else {
                        Game.allSprites.add(new Vader(true));
                    }
                }

                for (int i = 0; i < Game.allSprites.size(); i++) {
                    Game.allSprites.get(i).empireStart();
                }

                if (game.portal == null) {
                    game.portal = new Portal(game);
                }

                createSkullExplosion();
                Game.bosses.remove(this);
                Game.allSprites.remove(this);

                game.lastBoss = System.currentTimeMillis();

                Game.gameStatus = 6;
            });
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 35, y + 20);
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.intersection();
            if (hp > 4) {
                hp = (health / (float) BOSS_VADERS_HEALTH) * BOSS_HEALTH_BAR_LEN;
            } else {
                hp = 4;
            }
            if (health <= 0) {
                killAfterFight();
            }
        }
    }

    @Override
    public void update() {
        if (y == -600) {
            ImageHub.loadPortalImages();
            AudioHub.restartBossMusic();
            AudioHub.pauseBackgroundMusic();
            Game.gameStatus = 5;
        }
        if (y > 0 & !field) {
            field = true;
        }
        if (y > -halfHeight) {
            shoot();
        }
        if (field) {
            if (x <= 0) {
                left = true;
            }
            if (x >= screenWidthWidth) {
                left = false;
            }
            if ((y >= screenHeightHeight) | (y <= 0)) {
                speedY = -speedY;
            }

            if (left) {
                x += speedX;
            } else {
                x -= speedX;
            }
        }
        y += (speedY + 0.5);
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);

        Game.canvas.drawRect(centerX() - 70, y - 10, centerX() + 70, y + 5, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 68, y - 8, centerX() - 72 + hp, y + 3, Game.topPaintRed);
    }
}
