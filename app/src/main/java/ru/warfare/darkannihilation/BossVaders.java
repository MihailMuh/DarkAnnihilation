package ru.warfare.darkannihilation;

public class BossVaders extends Sprite {
    private final float maxHealth;
    private static final Vector vector = new Vector();
    private static final int shootBossTime = 1500;
    private long lastShoot;

    private boolean field = false;
    private boolean left = false;

    public BossVaders(Game g) {
        super(g, ImageHub.bossVadersImg.getWidth(), ImageHub.bossVadersImg.getHeight());

        health = 250;
        maxHealth = health;
        speedY = 1;
        speedX = 5;
        if (randInt(0, 1) == 1) {
            left = true;
        }
        isPassive = true;

        x = randInt(width, screenWidth - width - width);
        y = -600;

        recreateRect(x + 35, y + 20, x + width - 35, y + height - 20);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootBossTime) {
            lastShoot = now;
            int X = x + halfWidth;
            int Y = y + halfHeight;
            vector.makeVector(X, Y, game.player.x + game.player.halfWidth,
                    game.player.y + game.player.halfHeight, 10);
            game.allSprites.add(new BulletBossVaders(game, X, Y, vector.getSpeedX(), vector.getSpeedY()));
            AudioPlayer.playBossShoot();
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
        game.score += 300;
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
            Service.restartBossMusic();
            Service.pauseBackgroundMusic();
            game.gameStatus = 5;
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
            if (x + width >= screenWidth) {
                left = false;
            }
            if ((y + height >= screenHeight) | (y <= 0)) {
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
        game.canvas.drawBitmap(ImageHub.bossVadersImg, x, y, Game.alphaPaint);

        game.canvas.drawRect(x + halfWidth - 70, y - 10, x + halfWidth + 70, y + 5, Game.scorePaint);
        game.canvas.drawRect(x + halfWidth - 68, y - 8, x + halfWidth - 72 + (health / maxHealth) * 140, y + 3, Game.fpsPaint);
    }
}
