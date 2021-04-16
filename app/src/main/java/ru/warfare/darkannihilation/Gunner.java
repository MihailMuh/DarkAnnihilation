package ru.warfare.darkannihilation;

import android.graphics.Rect;

public class Gunner extends Character {
    public Gunner(Game g) {
        super(g, ImageHub.gunnerImg.getWidth(), ImageHub.gunnerImg.getHeight());
        health = 50;
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        endX = x;
        endY = y;

        shootTime = 130;
        shotgunTime = 270;
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
                    for (int i = 0; i < randInt(1, 6); i++) {
                        game.bullets.add(new BulletGunner(game, x + halfWidth, y));
                    }
                }
            }
        }
    }

    @Override
    public Rect getRect() {
        return new Rect(x + 25, y + 25, x + width - 25, y + height - 17);
    }

    @Override
    public void checkIntersections(Sprite enemy) {
        if (getRect().intersect(enemy.getRect())) {
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
            speedX = (endX - x) / 13;
            speedY = (endY - y) / 13;
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
