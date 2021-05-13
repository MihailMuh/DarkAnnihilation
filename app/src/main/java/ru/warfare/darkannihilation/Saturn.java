package ru.warfare.darkannihilation;

public class Saturn extends BaseCharacter {
    public Saturn(Game g) {
        super(g, ImageHub.saturnImg.getWidth(), ImageHub.saturnImg.getHeight());
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
                    BulletSaturn bulletSaturn = new BulletSaturn(game, x + halfWidth, y);
                    game.bullets.add(bulletSaturn);
                    game.allSprites.add(bulletSaturn);
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
        game.canvas.drawBitmap(ImageHub.saturnImg, x, y, null);
    }
}
