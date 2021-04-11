package ru.warfare.darkannihilation;

public class Gunner extends Character{
    public Gunner(Game g) {
        super(g, ImageHub.gunnerImg.getWidth(), ImageHub.gunnerImg.getHeight());
        health = 50;
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        endX = x;
        endY = y;

        shootTime = 50;
        shotgunTime = 270;
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void AI() {
        gun = "gun";
        ai = 1;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);
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
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, -5));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, -2));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, 0));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, 2));
                    game.buckshots.add(new Buckshot(game, x + halfWidth, y, 5));
                    game.numberBuckshots += 5;
                }
            } else {
                if (now - lastShoot > shootTime) {
                    lastShoot = now;
                    AudioPlayer.playShoot();
                    game.bullets.add(new Bullet(game, x + halfWidth - 6, y));
                    game.bullets.add(new Bullet(game, x + halfWidth, y));
                    game.numberBullets += 2;
                }
            }
        }
    }

    @Override
    public void check_intersectionVader(Vader vader) {
        if (vader.x + 15 < x + 20 & x + 20 < vader.x + vader.width - 15 &
                vader.y + 15 < y + 25 & y + 25 < vader.y + vader.height - 15 |
                x + 20 < vader.x + 15 & vader.x + 15 < x + width - 20 &
                        y + 25 < vader.y + 15 & vader.y + 15 < y + height - 20) {
            AudioPlayer.playMetal();
            damage(5);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(vader.x + vader.halfWidth, vader.y + vader.halfHeight);
                    break;
                }
            }
            vader.newStatus();
        }
    }

    @Override
    public void check_intersectionTripleFighter(TripleFighter tripleFighter) {
        if (tripleFighter.x + 5 < x + 20 & x + 20 < tripleFighter.x + tripleFighter.width - 5 &
                tripleFighter.y + 5 < y + 25 & y + 25 < tripleFighter.y + tripleFighter.height - 5 |
                x + 20 < tripleFighter.x + 5 & tripleFighter.x + 5 < x + width - 20 &
                        y + 25 < tripleFighter.y + 5 & tripleFighter.y + 5 < y + height - 20) {
            AudioPlayer.playMetal();
            damage(10);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(tripleFighter.x + tripleFighter.halfWidth,
                            tripleFighter.y + tripleFighter.halfHeight);
                    break;
                }
            }
            tripleFighter.newStatus();
        }
    }

    @Override
    public void check_intersectionPortal(Portal portal) {
        if (portal.x + 15 < x + 20 & x + 20 < portal.x + portal.width - 15 &
                portal.y + 15 < y + 25 & y + 25 < portal.y + portal.height - 15 |
                x + 20 < portal.x + 15 & portal.x + 15 < x + width - 20 &
                        y + 25 < portal.y + 15 & portal.y + 15 < y + height - 20) {
            game.gameStatus = 7;
            AudioPlayer.portalSound.pause();
            if (AudioPlayer.bossMusic.isPlaying()) {
                AudioPlayer.bossMusic.pause();
            }
            AudioPlayer.winMusic.seekTo(0);
            AudioPlayer.winMusic.start();
            game.winScreen = new WinScreen(game);
            portal.hide();
        }
    }

    @Override
    public void check_intersectionMinion(Minion minion) {
        if (minion.x + 15 < x + 20 & x + 20 < minion.x + minion.width - 15 &
                minion.y + 15 < y + 25 & y + 25 < minion.y + minion.height - 15 |
                x + 20 < minion.x + 15 & minion.x + 15 < x + width - 20 &
                        y + 25 < minion.y + 15 & minion.y + 15 < y + height - 20) {
            AudioPlayer.playMetal();
            game.minions.remove(minion);
            game.numberMinions -= 1;
            damage(5);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(minion.x + minion.halfWidth, minion.y + minion.halfHeight);
                    break;
                }
            }
        }
    }

    @Override
    public void check_intersectionDemoman(Demoman demoman) {
        if (demoman.x + 30 < x + 20 & x + 20 < demoman.x + demoman.width - 15 &
                demoman.y < y + 25 & y + 25 < demoman.y + demoman.height - 50 |
                x + 20 < demoman.x + 30 & demoman.x + 30 < x + width - 20 &
                        y + 25 < demoman.y & demoman.y < y + height - 20) {
            AudioPlayer.playMetal();
            damage(20);
            demoman.health = 0;
        }
    }

    @Override
    public void check_intersectionShotgunKit(ShotgunKit shotgunKit) {
        if (shotgunKit.x + 5 < x + 10 & x + 10 < shotgunKit.x + shotgunKit.width - 5 &
                shotgunKit.y + 5 < y + 10 & y + 10 < shotgunKit.y + shotgunKit.height - 5 |
                x + 10 < shotgunKit.x + 5 & shotgunKit.x + 5 < x + width - 10 &
                        y + 10 < shotgunKit.y + 5 & shotgunKit.y + 5 < y + height - 10) {
            shotgunKit.hide();
            shotgunKit.picked = true;
            game.changerGuns.setCoords(game.changerGuns.x + 50, game.changerGuns.y + 50, 2);
        }
    }

    @Override
    public void check_intersectionHealthKit(HealthKit healthKit) {
        if (healthKit.x + 5 < x + 5 & x + 5 < healthKit.x + healthKit.width - 5 &
                healthKit.y + 5 < y + 5 & y + 5 < healthKit.y + healthKit.height - 5 |
                x + 5 < healthKit.x + 5 & healthKit.x + 5 < x + width - 5 &
                        y + 5 < healthKit.y + 5 & healthKit.y + 5 < y + height - 5) {
            healthKit.hide();
            AudioPlayer.healSnd.start();
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

    @Override
    public void check_intersectionBullet(Bomb bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 10 |
                bulletEnemy.x < x + 10 & x + 10 < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y + 10 & y + 10 < bulletEnemy.y + bulletEnemy.height) {
            damage(bulletEnemy.damage);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bulletEnemy.x + bulletEnemy.halfWidth, bulletEnemy.y + bulletEnemy.halfHeight);
                    break;
                }
            }
            game.bombs.remove(bulletEnemy);
            game.numberBombs -= 1;
        }
    }

    @Override
    public void check_intersectionBullet(BulletEnemy bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 10 |
                bulletEnemy.x < x + 10 & x + 10 < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y + 10 & y + 10 < bulletEnemy.y + bulletEnemy.height) {
            damage(bulletEnemy.damage);
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
            damage(bulletEnemy.damage);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
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
        game.canvas.drawBitmap(ImageHub.gunnerImg, x, y, null);
    }
}
