package ru.warfare.darkannihilation;

public class Gunner extends BaseCharacter {
    public Gunner(Game g) {
        super(g, ImageHub.gunnerImg.getWidth(), ImageHub.gunnerImg.getHeight());
        health = 50;
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        endX = x;
        endY = y;

        recreateRect(x + 25, y + 25, x + width - 25, y + height - 17);

        shootTime = 130;
        shotgunTime = 270;
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void shoot() {
        now = System.currentTimeMillis();
        if (gun.equals("shotgun")) {
            if (now - lastShoot > shotgunTime) {
                lastShoot = now;
                AudioPlayer.playShotgun();
                for (int i = -4; i <= 4; i+=2) {
                    Buckshot buckshot = new Buckshot(game, x + halfWidth, y, i);
                    game.bullets.add(buckshot);
                    game.allSprites.add(buckshot);
                }
            }
        } else {
            if (now - lastShoot > shootTime) {
                lastShoot = now;
                AudioPlayer.playShoot();
                for (int i = 0; i < randInt(1, 6); i++) {
                    BulletGunner bulletGunner = new BulletGunner(game, x + halfWidth, y);
                    game.bullets.add(bulletGunner);
                    game.allSprites.add(bulletGunner);
                }
            }
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 25, y + 25);
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
        if (!lock) {
            shoot();
        }

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
            renderHearts();
        }
        game.canvas.drawBitmap(ImageHub.gunnerImg, x, y, null);
    }
}
