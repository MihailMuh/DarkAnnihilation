package ru.warfare.darkannihilation;

public class BossVaders extends Sprite {
    private final float maxHealth;
    private static final Vector vector = new Vector();
    private int shootBossTime = 1200;
    private long lastShoot;

    private boolean field = false;
    private boolean left = false;

    public BossVaders(Game g) {
        super(g, ImageHub.bossVadersImg.getWidth(), ImageHub.bossVadersImg.getHeight());

        health = 350;
        maxHealth = health;
        speedY = 1;
        speedX = 5;
        if (randInt(0, 1) == 1) {
            left = true;
        }
        isPassive = true;

        x = randInt(width, screenWidthWidth - width);
        y = -600;

        recreateRect(x + 35, y + 20, right() - 35, bottom() - 20);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootBossTime) {
            lastShoot = now;
            int X = centerX();
            int Y = centerY();
            vector.makeVector(X, Y, game.player.centerX(), game.player.centerY(), 10);
            Game.allSprites.add(new BulletBossVaders(X, Y, vector.getSpeedX(), vector.getSpeedY()));
            AudioPlayer.playBossShoot();
        }
    }

    @Override
    public void buff() {
        speedX *= 2;
        speedY *= 2;
        shootBossTime /= 2;
        health *= 2;
    }

    @Override
    public void stopBuff() {
        speedX /= 2;
        speedY /= 2;
        shootBossTime = 1200;
    }

    public void killAfterFight() {
        createSkullExplosion();
        AudioPlayer.playMegaBoom();
        Game.score += 300;
        Game.bosses.remove(this);
        Game.allSprites.remove(this);
        for (int i = 0; i < Game.numberVaders; i++) {
            if (Game.random.nextFloat() <= 0.3) {
                Game.allSprites.add(new XWing(game));
            }
        }
        AudioPlayer.pauseBossMusic();
        if (game.portal.lock) {
            game.portal.start();
        }
        for (int i = 0; i < Game.allSprites.size(); i++) {
            Game.allSprites.get(i).empireStart();
        }
        Game.lastBoss += game.pauseTimer;
        game.pauseTimer = 0;
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
        game.pauseTimer += 20;
        if (y == -400) {
            AudioPlayer.restartBossMusic();
            AudioPlayer.pauseBackgroundMusic();
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
        y += speedY;

        if (health <= 0) {
            killAfterFight();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bossVadersImg, x, y, Game.alphaPaint);

        Game.canvas.drawRect(centerX() - 70, y - 10, centerX() + 70, y + 5, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 68, y - 8, centerX() - 72 + (health / maxHealth) * 140, y + 3, Game.fpsPaint);
    }
}
