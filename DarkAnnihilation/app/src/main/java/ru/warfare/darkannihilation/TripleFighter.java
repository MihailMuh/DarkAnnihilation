package ru.warfare.darkannihilation;

public class TripleFighter extends Sprite {
    public int X;
    public int Y;
    private double angle;
    private static final int shootTime = 1_500;
    private long lastShoot;
    private static long now;

    public TripleFighter(Game g) {
        super(g, ImageHub.tripleFighterImg.getWidth(), ImageHub.tripleFighterImg.getHeight());
        lock = true;
        health = 6;

        x = randInt(0, screenWidth);
        y = -150;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 10);
        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootTime) {
            lastShoot = now;

            X = ((game.player.x + game.player.halfWidth) - (x + halfWidth)) / 50;
            Y = ((game.player.y + game.player.halfHeight) - (y + halfHeight)) / 50;
            angle = Math.toDegrees(Math.atan2(Y, X) + (Math.PI / 2));

            game.bulletEnemies.add(new BulletEnemy(game, x + halfWidth, y + halfHeight, angle, X, Y));
            game.numberBulletsEnemy += 1;
        }
    }

    public void newStatus() {
        if (game.numberBosses != 0) {
            lock = true;
        }
        health = 6;
        x = randInt(0, screenWidth);
        y = -150;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 10);
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void check_intersectionBullet(Bullet bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.halfWidth, bullet.y + bullet.halfHeight);
                    break;
                }
            }
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            if (health <= 0) {
                for (int i = 0; i < numberDefaultExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playBoom();
                game.score += 5;
                newStatus();
            }
        }
    }

    public void check_intersectionBullet(Buckshot bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.halfWidth, bullet.y + bullet.halfHeight);
                    break;
                }
            }
            game.buckshots.remove(bullet);
            game.numberBuckshots -= 1;
            if (health <= 0) {
                for (int i = 0; i < numberDefaultExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playBoom();
                game.score += 5;
                newStatus();
            }
        }
    }

    @Override
    public void check_intersectionPlayer() {
        if (x + 5 < game.player.x + 20 & game.player.x + 20 < x + width - 5 &
                y + 5 < game.player.y + 25 & game.player.y + 25 < y + height - 5 |
                game.player.x + 20 < x + 5 & x + 5 < game.player.x + game.player.width - 20 &
                        game.player.y + 25 < y + 5 & y + 5 < game.player.y + game.player.height - 20) {
            AudioPlayer.playMetal();
            game.player.damage(10);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
            newStatus();
        }
    }

    @Override
    public void update() {
        if (!lock) {
            check_intersectionPlayer();

            x += speedX;
            y += speedY;

            shoot();

            if (x < -width | x > game.screenWidth | y > game.screenHeight) {
                newStatus();
            }
        } else {
            if (game.numberBosses == 0 & game.gameStatus != 2 & game.gameStatus != 4) {
                lock = false;
            }
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.tripleFighterImg, x, y, null);
    }
}
