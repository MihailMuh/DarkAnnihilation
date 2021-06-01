package ru.warfare.darkannihilation;

public class Minion extends Sprite {
    private static final int shootMinionTime = 900;
    private long lastShoot;

    public Minion(int x, int y) {
        super(ImageHub.minionImg.getWidth(), ImageHub.minionImg.getHeight());
        health = 2;
        damage = 5;

        this.x = x;
        this.y = y;
        speedX = randInt(-8, 8);
        speedY = randInt(2, 5);

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootMinionTime) {
            if (HardThread.job == 0) {
                lastShoot = now;
                HardThread.x = centerX();
                HardThread.y = centerY();
                HardThread.job = 1;
            }
        }
    }

    @Override
    public void intersection() {
        AudioPlayer.playBoom();
        Game.allSprites.remove(this);
        createLargeTripleExplosion();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMetal();
        Game.allSprites.remove(this);
        createSmallTripleExplosion();
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (getRect().intersect(bullet.getRect())) {
            bullet.intersection();
            intersection();
        }
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        shoot();

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight) {
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.minionImg, x, y, null);
    }
}