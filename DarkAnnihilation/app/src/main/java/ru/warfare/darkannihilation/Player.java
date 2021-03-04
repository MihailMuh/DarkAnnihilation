package ru.warfare.darkannihilation;

public class Player extends Sprite {
    public int endX;
    public int endY;
    private static final int shootTime = 100_000_000;
    private long lastShoot;
    private static long now;
    public int ai = 1;
    public boolean dontmove = false;

    public Player(Game g) {
        super(g, ImageHub.playerImage.getWidth(), ImageHub.playerImage.getHeight());
        health = 50;
        speedX = get_random(3, 7);
        speedY = get_random(3, 7);

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        endX = x;
        endY = y;

        lastShoot = System.nanoTime();
    }

    public void damage(int dmg) {
        if (ai == 0) {
            health -= dmg;
            if (health <= 0) {
                game.vibrator.vibrate(2000);
            } else {
                game.vibrator.vibrate(150);
            }
        }
    }

    public void AI() {
        ai = 1;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        speedX = get_random(3, 7);
        speedY = get_random(3, 7);
    }

    public void PLAYER() {
        ai = 0;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        lock = true;
        health = 50;
    }

    @Override
    public void check_intersectionBullet(BulletEnemy bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 20 |
                bulletEnemy.x < x + 10 & x + 10 < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y + 10 & y + 10 < bulletEnemy.y + bulletEnemy.height) {
            game.player.damage(bulletEnemy.damage);
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bulletEnemy.x + bulletEnemy.halfWidth, bulletEnemy.y + bulletEnemy.halfHeight);
                    break;
                }
            }
            game.bulletEnemies.remove(bulletEnemy);
            game.numberBulletsEnemy -= 1;
        }
    }

    @Override
    public void check_intersectionBullet(BulletBoss bulletEnemy) {
        if (x + 15 < bulletEnemy.x & bulletEnemy.x < x + width - 15 &
                y + 15 < bulletEnemy.y & bulletEnemy.y < y + height - 15 |
                bulletEnemy.x < x + 15 & x + 15 < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y + 15 & y + 15 < bulletEnemy.y + bulletEnemy.height) {
            game.player.damage(bulletEnemy.damage);
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bulletEnemy.x + bulletEnemy.halfWidth, bulletEnemy.y + bulletEnemy.halfHeight);
                    break;
                }
            }
            game.bulletBosses.remove(bulletEnemy);
            game.numberBulletsBoss -= 1;
        }
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (!lock) {
            now = System.nanoTime();
            if (now - lastShoot > shootTime) {
                lastShoot = now;
                game.bullets.add(new Bullet(game, x + halfWidth - 6, y));
                game.bullets.add(new Bullet(game, x + halfWidth, y));
                game.numberBullets += 2;
            }
        }

        if (ai == 0) {
            speedX = (endX - x) / 5;
            speedY = (endY - y) / 5;
        } else {
            if (x < 30 | x > game.screenWidth - height - 30) {
                speedX = -speedX;
            }
            if (y < 30 | y > game.screenHeight - width - 30) {
                speedY = -speedY;
            }
        }

    }

    @Override
    public void render () {
        if (ai == 0) {
            switch (health) {
                case 50:
                    game.hearts[0].render("full");
                    game.hearts[1].render("full");
                    game.hearts[2].render("full");
                    game.hearts[3].render("full");
                    game.hearts[4].render("full");
                    break;
                case 45:
                    game.hearts[0].render("half");
                    game.hearts[1].render("full");
                    game.hearts[2].render("full");
                    game.hearts[3].render("full");
                    game.hearts[4].render("full");
                    break;
                case 40:
                    game.hearts[0].render("non");
                    game.hearts[1].render("full");
                    game.hearts[2].render("full");
                    game.hearts[3].render("full");
                    game.hearts[4].render("full");
                    break;
                case 35:
                    game.hearts[0].render("non");
                    game.hearts[1].render("half");
                    game.hearts[2].render("full");
                    game.hearts[3].render("full");
                    game.hearts[4].render("full");
                    break;
                case 30:
                    game.hearts[0].render("non");
                    game.hearts[1].render("non");
                    game.hearts[2].render("full");
                    game.hearts[3].render("full");
                    game.hearts[4].render("full");
                    break;
                case 25:
                    game.hearts[0].render("non");
                    game.hearts[1].render("non");
                    game.hearts[2].render("half");
                    game.hearts[3].render("full");
                    game.hearts[4].render("full");
                    break;
                case 20:
                    game.hearts[0].render("non");
                    game.hearts[1].render("non");
                    game.hearts[2].render("non");
                    game.hearts[3].render("full");
                    game.hearts[4].render("full");
                    break;
                case 15:
                    game.hearts[0].render("non");
                    game.hearts[1].render("non");
                    game.hearts[2].render("non");
                    game.hearts[3].render("half");
                    game.hearts[4].render("full");
                    break;
                case 10:
                    game.hearts[0].render("non");
                    game.hearts[1].render("non");
                    game.hearts[2].render("non");
                    game.hearts[3].render("non");
                    game.hearts[4].render("full");
                    break;
                case 5:
                    game.hearts[0].render("non");
                    game.hearts[1].render("non");
                    game.hearts[2].render("non");
                    game.hearts[3].render("non");
                    game.hearts[4].render("half");
                    break;
                case 0:
                    game.hearts[0].render("non");
                    game.hearts[1].render("non");
                    game.hearts[2].render("non");
                    game.hearts[3].render("non");
                    game.hearts[4].render("non");
                    break;
            }
        }
        game.canvas.drawBitmap(ImageHub.playerImage, x, y, null);
//        game.canvas.drawRect(x + 20, y + 30, x + width - 20, y + height - 20, paint);

    }
}
