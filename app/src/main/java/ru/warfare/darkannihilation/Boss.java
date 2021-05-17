package ru.warfare.darkannihilation;

public class Boss extends Sprite {
    private final float maxHealth;
    private static final int shootBossTime = 350;
    private long lastShoot;

    public Boss(Game g) {
        super(g, ImageHub.bossImage.getWidth(), ImageHub.bossImage.getHeight());

        health = 150;
        maxHealth = health;
        speedY = 1;
        isPassive = true;

        x = Game.halfScreenWidth - halfWidth;
        y = -600;

        recreateRect(x + 20, y + 20, right() - 20, bottom() - 20);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootBossTime) {
            lastShoot = now;
            int r = right() - 65;
            int y20 = y + 20;
            Game.allSprites.add(new BulletBoss(r, y20, 1));
            Game.allSprites.add(new BulletBoss(r, y20, 2));
            Game.allSprites.add(new BulletBoss(r, y20, 3));
            AudioPlayer.playShoot();
        }
    }

    public void killAfterFight() {
        createSkullExplosion();
        AudioPlayer.playMegaBoom();
        Game.score += 150;
        Game.bosses.remove(this);
        Game.allSprites.remove(this);
        AudioPlayer.pauseBossMusic();
        if (game.portal.lock) {
            game.portal.start();
        }
        for (int i = 0; i < Game.numberVaders; i++) {
            if (Game.random.nextFloat() <= 0.1) {
                Game.allSprites.add(new TripleFighter());
            }
        }
        for (int i = 0; i < Game.allSprites.size(); i++) {
            Game.allSprites.get(i).empireStart();
        }
        Game.lastBoss += game.pauseTimer;
        game.pauseTimer = 0;
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 20, y + 20);
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
        x += speedX;
        y += speedY;

        if (y == -400) {
            AudioPlayer.restartBossMusic();
            AudioPlayer.pauseBackgroundMusic();
            Game.gameStatus = 5;
        }
        if (y >= 50) {
            speedY = 0;
            speedX = -8;
            shoot();
        }
        if (x < -width) {
            x = Game.screenWidth;
        }

        if (health <= 0) {
            killAfterFight();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bossImage, x, y, null);

        Game.canvas.drawRect(centerX() - 70, y - 10, centerX() + 70, y + 5, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 68, y - 8, centerX() - 72 + (health / maxHealth) * 140, y + 3, Game.fpsPaint);
    }
}
