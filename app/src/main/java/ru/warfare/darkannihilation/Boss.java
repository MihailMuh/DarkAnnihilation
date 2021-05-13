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

        x = halfScreenWidth - halfWidth;
        y = -600;

        recreateRect(x + 20, y + 20, x + width - 20, y + height - 20);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootBossTime) {
            lastShoot = now;
            game.allSprites.add(new BulletBoss(game, x + width - 65, y + 20, 1));
            game.allSprites.add(new BulletBoss(game, x + width - 65, y + 20, 2));
            game.allSprites.add(new BulletBoss(game, x + width - 65, y + 20, 3));
            AudioPlayer.playShoot();
        }
    }

    public void killAfterFight() {
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playMegaBoom();
        game.score += 150;
        game.bosses.remove(this);
        game.allSprites.remove(this);
        switch (Game.level)
        {
            case 1:
                for (int i = 0; i < game.numberVaders; i++) {
                    if (Game.random.nextFloat() <= 0.1) {
                        game.allSprites.add(new TripleFighter(game));
                    }
                }
                break;
            case 2:
                for (int i = 0; i < game.numberVaders; i++) {
                    if (Game.random.nextFloat() <= 0.1) {
                        game.allSprites.add(new XWing(game));
                    }
                }
                break;
        }
        Service.pauseBossMusic();
        if (game.portal.lock) {
            game.portal.start();
        }
        for (int i = 0; i < game.allSprites.size(); i++) {
            game.allSprites.get(i).empireStart();
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
            Service.restartBossMusic();
            Service.pauseBackgroundMusic();
            game.gameStatus = 5;
        }
        if (y >= 50) {
            speedY = 0;
            speedX = -8;
            shoot();
        }
        if (x < -width) {
            x = game.screenWidth;
        }

        if (health <= 0) {
            killAfterFight();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.bossImage, x, y, null);

        game.canvas.drawRect(x + halfWidth - 70, y - 10, x + halfWidth + 70, y + 5, Game.scorePaint);
        game.canvas.drawRect(x + halfWidth - 68, y - 8, x + halfWidth - 72 + (health / maxHealth) * 140, y + 3, Game.fpsPaint);
    }
}
