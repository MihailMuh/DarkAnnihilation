package ru.warfare.darkannihilation;

public class Saturn extends BaseCharacter {
    public Saturn(Game g) {
        super(g, ImageHub.gunnerImg.getWidth(), ImageHub.gunnerImg.getHeight());
        health = 50;

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        endX = x;
        endY = y;

        recreateRect(x + 25, y + 25, x + width - 25, y + height - 17);

        shootTime = 130;
        shotgunTime = 40;
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void PLAYER() {
        god = false;

        shootTime = 130;
        shotgunTime = 40;
        switch (game.level)
        {
            case 1:
                gun = "gun";
                break;
            case 2:
                shootTime = 90;
                shotgunTime = 33;
                break;
        }
        ai = false;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        lock = true;
        health = maxHealth;
        int c = 370;
        for (int i = 0; i < 5; i++) {
            hearts[i] = new Heart(game, c, 10);
            c -= 90;
        }
    }

    @Override
    public void shoot() {
        now = System.currentTimeMillis();
        if (gun.equals("shotgun")) {
            if (now - lastShoot > shotgunTime) {
                lastShoot = now;
                if (HardWorker.job == 0) {
                    HardWorker.job = 2;
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

        speedX = (endX - x) / 20;
        speedY = (endY - y) / 20;
    }

    @Override
    public void render () {
        renderHearts();
        game.canvas.drawBitmap(ImageHub.gunnerImg, x, y, null);
    }
}
