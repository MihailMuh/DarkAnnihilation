package ru.warfare.darkannihilation;

public class Minion extends Sprite {
    private static final int shootMinionTime = 900;
    private long lastShoot;
    private long now;

    public Minion(Game g, int FX) {
        super(g, ImageHub.minionImg.getWidth(), ImageHub.minionImg.getHeight());
        health = 2;

        x = randInt(FX, FX + ImageHub.factoryImg.getWidth());
        y = ImageHub.factoryImg.getHeight() - 100;
        speedX = randInt(-8, 8);
        speedY = randInt(2, 5);
        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootMinionTime) {
            lastShoot = now;
            HardWorker.x = x;
            HardWorker.y = y;
            HardWorker.halfHeight = halfHeight;
            HardWorker.halfWidth = halfWidth;
            HardWorker.typeWork = 1;
        }
    }

    @Override
    public void check_intersectionBullet(Bullet bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            AudioPlayer.playBoom();
            game.minions.remove(this);
            game.numberMinions -= 1;
            for (int i = 0; i < numberDefaultExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
        }
    }

    public void check_intersectionBullet(Buckshot bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            AudioPlayer.playBoom();
            game.minions.remove(this);
            game.numberMinions -= 1;
            for (int i = 0; i < numberDefaultExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
        }
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        shoot();

        if (x < -width | x > game.screenWidth | y > game.screenHeight) {
            game.minions.remove(this);
            game.numberMinions -= 1;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.minionImg, x, y, null);
    }
}