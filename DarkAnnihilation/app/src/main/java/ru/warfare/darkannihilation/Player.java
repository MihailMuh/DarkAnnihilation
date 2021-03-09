package ru.warfare.darkannihilation;

public class Player extends Sprite {
    public int endX;
    public int endY;
    private static final int shootTime = 110;
    private static final int shotgunTime = 535;
    private static long lastShoot;
    private static long now;
    public int ai = 1;
    public boolean dontmove = false;
    public String gun = "gun";

    public Player(Game g) {
        super(g, ImageHub.playerImage.getWidth(), ImageHub.playerImage.getHeight());
        health = 50;
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        endX = x;
        endY = y;

        lastShoot = System.currentTimeMillis();
    }

    public void damage(int dmg) {
        if (ai == 0) {
            health -= dmg;
            if (health <= 0) {
                for (int i = numberSmallExplosions; i < numberLargeExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playMegaBoom();
                game.vibrator.vibrate(1800);
            } else {
                game.vibrator.vibrate(70);
            }
        }
    }

    public void AI() {
        gun = "gun";
        ai = 1;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);
    }

    public void PLAYER() {
        gun = "gun";
        ai = 0;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        lock = true;
        health = 50;
    }

    public void check_intersectionRocket(Rocket rocket) {
        if (x + 5 < rocket.x & rocket.x < x + width - 5 &
                y < rocket.y & rocket.y < y + height |
                rocket.x < x + 5 & x + 5 < rocket.x + rocket.width &
                        rocket.y < y & y < rocket.y + rocket.height) {
            game.player.damage(rocket.damage);
            for (int i = numberSmallExplosions; i < numberLargeExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(rocket.x + rocket.halfWidth, rocket.y + rocket.halfHeight);
                    break;
                }
            }
            AudioPlayer.playMegaBoom();
            rocket.hide();
        }
    }

    public void check_intersectionBullet(Bomb bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 10 |
                bulletEnemy.x < x + 10 & x + 10 < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y + 10 & y + 10 < bulletEnemy.y + bulletEnemy.height) {
            game.player.damage(bulletEnemy.damage);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
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
    public void check_intersectionBullet(BulletEnemy bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 10 |
                bulletEnemy.x < x + 10 & x + 10 < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y + 10 & y + 10 < bulletEnemy.y + bulletEnemy.height) {
            game.player.damage(bulletEnemy.damage);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
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
            for (int i = numberDefaultExplosions; i < numberSmallExplosions + numberDefaultExplosions; i++) {
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
            if (gun.equals("gun")) {
                now = System.currentTimeMillis();
                if (now - lastShoot > shootTime) {
                    lastShoot = now;
                    AudioPlayer.playShoot();
                    if (gun.equals("gun")) {
                        game.bullets.add(new Bullet(game, x + halfWidth - 6, y));
                        game.bullets.add(new Bullet(game, x + halfWidth, y));
                        game.numberBullets += 2;
                    }
                }
            } else {
                now = System.currentTimeMillis();
                if (now - lastShoot > shotgunTime) {
                    lastShoot = now;
                    AudioPlayer.playShotgun();
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, -5));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, -2));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, -0));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, 2));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, 5));
                    game.numberBuckshots += 5;
                }
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
