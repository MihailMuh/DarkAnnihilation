package ru.warfare.darkannihilation;

public class Player extends Character {
    public Player(Game g) {
        super(g, ImageHub.playerImage.getWidth(), ImageHub.playerImage.getHeight());
        health = 50;
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        endX = x;
        endY = y;

        shootTime = 110;
        shotgunTime = 535;
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void PLAYER() {
        gun = "gun";
        ai = 0;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        lock = true;
        health = 50;
    }

    @Override
    public void shoot() {
        if (!lock) {
            now = System.currentTimeMillis();
            if (gun.equals("shotgun")) {
                if (now - lastShoot > shotgunTime) {
                    lastShoot = now;
                    AudioPlayer.playShotgun();
                    game.bullets.add(new Buckshot(game, x + halfWidth, y, -5));
                    game.bullets.add(new Buckshot(game, x + halfWidth, y, -2));
                    game.bullets.add(new Buckshot(game, x + halfWidth, y, 0));
                    game.bullets.add(new Buckshot(game, x + halfWidth, y, 2));
                    game.bullets.add(new Buckshot(game, x + halfWidth, y, 5));
                }
            } else {
                if (now - lastShoot > shootTime) {
                    lastShoot = now;
                    AudioPlayer.playShoot();
                    game.bullets.add(new Bullet(game, x + halfWidth - 6, y));
                    game.bullets.add(new Bullet(game, x + halfWidth, y));
                }
            }
        }
    }

    @Override
    public void check_intersectionPortal(Portal portal) {
        if (portal.x + 15 < x + 20 & x + 20 < portal.x + portal.width - 15 &
                portal.y + 15 < y + 25 & y + 25 < portal.y + portal.height - 15 |
                x + 20 < portal.x + 15 & portal.x + 15 < x + width - 20 &
                        y + 25 < portal.y + 15 & portal.y + 15 < y + height - 20) {
            portal.intersection();
        }
    }

    @Override
    public void check_intersectionDemoman(Demoman demoman) {
        if (demoman.x + 30 < x + 20 & x + 20 < demoman.x + demoman.width - 15 &
                demoman.y < y + 25 & y + 25 < demoman.y + demoman.height - 50 |
                x + 20 < demoman.x + 30 & demoman.x + 30 < x + width - 20 &
                        y + 25 < demoman.y & demoman.y < y + height - 20) {
            damage(demoman.damage);
            demoman.intersectionPlayer();
        }
    }

    @Override
    public void check_intersectionShotgunKit(ShotgunKit shotgunKit) {
        if (shotgunKit.x + 5 < x + 10 & x + 10 < shotgunKit.x + shotgunKit.width - 5 &
                shotgunKit.y + 5 < y + 10 & y + 10 < shotgunKit.y + shotgunKit.height - 5 |
                x + 10 < shotgunKit.x + 5 & shotgunKit.x + 5 < x + width - 10 &
                        y + 10 < shotgunKit.y + 5 & shotgunKit.y + 5 < y + height - 10) {
            shotgunKit.intersection();
        }
    }

    @Override
    public void check_intersectionHealthKit(HealthKit healthKit) {
        if (healthKit.x + 5 < x + 5 & x + 5 < healthKit.x + healthKit.width - 5 &
                healthKit.y + 5 < y + 5 & y + 5 < healthKit.y + healthKit.height - 5 |
                x + 5 < healthKit.x + 5 & healthKit.x + 5 < x + width - 5 &
                        y + 5 < healthKit.y + 5 & healthKit.y + 5 < y + height - 5) {
            healthKit.intersection();
            if (health < 30) {
                health += 20;
            } else {
                health = 50;
            }
        }
    }

    @Override
    public void check_intersectionRocket(Rocket rocket) {
        if (x + 5 < rocket.x & rocket.x < x + width - 5 &
                y < rocket.y & rocket.y < y + height |
                rocket.x < x + 5 & x + 5 < rocket.x + rocket.width &
                        rocket.y < y & y < rocket.y + rocket.height) {
            damage(rocket.damage);
            rocket.intersection();
        }
    }

    @Override
    public void check_intersectionBullet(BulletBase bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 10 |
                bulletEnemy.x < x + 10 & x + 10 < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y + 10 & y + 10 < bulletEnemy.y + bulletEnemy.height) {
            damage(bulletEnemy.damage);
            bulletEnemy.intersection();
        }
    }

    @Override
    public void checkIntersections(Sprite enemy) {
        if (enemy.x + 15 < x + 20 & x + 20 < enemy.x + enemy.width - 15 &
                enemy.y + 15 < y + 25 & y + 25 < enemy.y + enemy.height - 15 |
                x + 20 < enemy.x + 15 & enemy.x + 15 < x + width - 20 &
                        y + 25 < enemy.y + 15 & enemy.y + 15 < y + height - 20) {
            damage(enemy.damage);
            enemy.intersectionPlayer();
        }
    }

    @Override
    public void update() {
        shoot();

        x += speedX;
        y += speedY;

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
    }
}
