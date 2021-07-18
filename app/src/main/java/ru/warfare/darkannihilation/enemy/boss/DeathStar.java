package ru.warfare.darkannihilation.enemy.boss;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletBoss;
import ru.warfare.darkannihilation.enemy.Portal;
import ru.warfare.darkannihilation.enemy.TripleFighter;
import ru.warfare.darkannihilation.enemy.Vader;
import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.Constants.BOSS_HEALTH;
import static ru.warfare.darkannihilation.Constants.BOSS_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.Constants.BOSS_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class DeathStar extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private float hp = BOSS_HEALTH_BAR_LEN;
    private boolean BOOM = false;

    public DeathStar(Game g) {
        super(g, ImageHub.bossImage);

        health = BOSS_HEALTH;
        speedY = 1;
        speedX = 10;
        isPassive = true;

        x = randInt(0, screenWidthWidth);
        y = -800;

        ImageHub.loadPortalImages();

        recreateRect(x + 20, y + 20, right() - 20, bottom() - 20);
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > BOSS_SHOOT_TIME) {
            lastShoot = now;
            HardThread.newJob(() -> {
                int ri = right() - 115;
                int y40 = y + 40;
                for (int i = 1; i < 4; i++) {
                    Game.allSprites.add(new BulletBoss(ri, y40, i));
                }
                AudioHub.playShoot();
            });
        }
    }

    public void killAfterFight() {
        if (!BOOM) {
            BOOM = true;
            HardThread.newJob(() -> {
                Game.score += 325;
                AudioHub.pauseBossMusic();
                int len = Game.numberVaders / 4;
                for (int i = 0; i < len; i++) {
                    if (Game.random.nextFloat() <= 0.1) {
                        Game.allSprites.add(new TripleFighter(game));
                    } else {
                        Game.allSprites.add(new Vader(false));
                    }
                }

                for (int i = 0; i < Game.allSprites.size(); i++) {
                    Sprite sprite = Game.allSprites.get(i);
                    if (sprite != null) {
                        sprite.empireStart();
                    }
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
    public void check_intersectionBullet(Sprite bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.intersection();
            if (hp > 4) {
                hp = (health / (float) BOSS_HEALTH) * BOSS_HEALTH_BAR_LEN;
            } else {
                hp = 4;
            }
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 20, y + 20);
    }

    @Override
    public void update() {
        if (y >= 35) {
            x += speedX;
            if (x < -width) {
                if (randInt(1, 2) == 1) {
                    speedX = -speedX;
                } else {
                    x = Game.screenWidth;
                }
            }
            if (x > Game.screenWidth) {
                if (randInt(1, 2) == 1) {
                    speedX = -speedX;
                } else {
                    x = -width;
                }
            }
            shoot();

        } else {
            if (y == -600) {
                AudioHub.restartBossMusic();
                AudioHub.pauseBackgroundMusic();
                Game.gameStatus = 5;
            }
            y += speedY;
        }

        if (health <= 0) {
            killAfterFight();
        }
    }

    @Override
    public void render() {
        super.render();

        Game.canvas.drawRect(centerX() - 70, y - 10, centerX() + 70, y + 5, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 68, y - 8, centerX() - 72 + hp, y + 3, Game.topPaintRed);
    }
}
