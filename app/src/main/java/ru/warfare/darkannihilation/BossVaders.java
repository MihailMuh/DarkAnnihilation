package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.BOSS_VADERS_HEALTH;
import static ru.warfare.darkannihilation.Constants.BOSS_VADERS_SHOOT_TIME;

public class BossVaders extends Sprite {
    private final Vector vector = new Vector();
    private long lastShoot;

    private boolean field = false;
    private boolean left = false;

    public BossVaders(Game g) {
        super(g, ImageHub.bossVadersImg.getWidth(), ImageHub.bossVadersImg.getHeight());

        health = BOSS_VADERS_HEALTH;
        speedY = 1;
        speedX = 5;
        if (MATH.randInt(0, 1) == 1) {
            left = true;
        }
        isPassive = true;

        x = MATH.randInt(width, screenWidthWidth);
        y = -800;

        recreateRect(x + 35, y + 20, right() - 35, bottom() - 20);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > BOSS_VADERS_SHOOT_TIME) {
            lastShoot = now;
            final int[] values = vector.easyVector(centerX(), centerY(), game.player.centerX(), game.player.centerY(), 10);
            Game.allSprites.add(new BulletBossVaders(centerX(), centerY(), values[0], values[1]));
            AudioHub.playBossShoot();
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
        new Thread(() -> {
            createSkullExplosion();
            Game.score += 400;
            Game.bosses.remove(this);
            Game.allSprites.remove(this);
            AudioHub.pauseBossMusic();
            if (game.portal == null) {
                game.portal = new Portal(game);
            }
            final int len = Game.numberVaders / 2;
            for (int i = 0; i < len; i++) {
                if (Game.random.nextFloat() <= 0.3) {
                    Game.allSprites.add(new XWing());
                } else {
                    Game.allSprites.add(new Vader(true));
                }
            }

            for (int i = 0; i < Game.allSprites.size(); i++) {
                Game.allSprites.get(i).empireStart();
            }

            Game.gameStatus = 6;

            game.lastBoss = System.currentTimeMillis();
        }).start();
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 35, y + 20);
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (getRect().intersect(bullet.getRect())) {
            health -= bullet.damage;
            bullet.intersection();
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

            if (health <= 0) {
                killAfterFight();
            }
        }
        y += (speedY + 0.5);
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bossVadersImg, x, y, Game.alphaPaint);

        Game.canvas.drawRect(centerX() - 70, y - 10, centerX() + 70, y + 5, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 68, y - 8, centerX() - 72 + ((health / (float) BOSS_VADERS_HEALTH) * 140), y + 3, Game.fpsPaint);
    }
}
