package ru.warfare.darkannihilation;

import android.graphics.Rect;

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
        health = maxHealth;
        int c = 370;
        for (int i = 0; i < 5; i++) {
            Heart heart = new Heart(game, c, 10);
            hearts[i] = heart;
            c -= 90;
        }
    }

    @Override
    public void shoot() {
        if (!lock) {
            now = System.currentTimeMillis();
            if (gun.equals("shotgun")) {
                if (now - lastShoot > shotgunTime) {
                    lastShoot = now;
                    AudioPlayer.playShotgun();
                    Buckshot buckshot = new Buckshot(game, x + halfWidth, y, -5);
                    game.bullets.add(buckshot);
                    game.allSprites.add(buckshot);

                    buckshot = new Buckshot(game, x + halfWidth, y, -2);
                    game.bullets.add(buckshot);
                    game.allSprites.add(buckshot);

                    buckshot = new Buckshot(game, x + halfWidth, y, 0);
                    game.bullets.add(buckshot);
                    game.allSprites.add(buckshot);

                    buckshot = new Buckshot(game, x + halfWidth, y, 2);
                    game.bullets.add(buckshot);
                    game.allSprites.add(buckshot);

                    buckshot = new Buckshot(game, x + halfWidth, y, 5);
                    game.bullets.add(buckshot);
                    game.allSprites.add(buckshot);
                }
            } else {
                if (now - lastShoot > shootTime) {
                    lastShoot = now;
                    AudioPlayer.playShoot();
                    Bullet bullet = new Bullet(game, x + halfWidth - 6, y);
                    game.bullets.add(bullet);
                    game.allSprites.add(bullet);

                    bullet = new Bullet(game, x + halfWidth, y);
                    game.bullets.add(bullet);
                    game.allSprites.add(bullet);
                }
            }
        }
    }

    @Override
    public Rect getRect() {
        return new Rect(x + 20, y + 25, x + width - 20, y + height - 20);
    }

    @Override
    public void checkIntersections(Sprite sprite) {
        if (getRect().intersect(sprite.getRect())) {
            damage(sprite.damage);
            sprite.intersectionPlayer();
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
            renderHearts();
        }
        game.canvas.drawBitmap(ImageHub.playerImage, x, y, null);
    }
}
