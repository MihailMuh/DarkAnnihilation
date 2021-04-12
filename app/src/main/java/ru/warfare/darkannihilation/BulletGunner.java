package ru.warfare.darkannihilation;

public class BulletGunner extends BulletBase {
    public double damage = 0.5;
    private double speedx;

    public BulletGunner(Game g, int X, int Y) {
        super(g, ImageHub.bulletGunnerImg.getWidth(), ImageHub.bulletGunnerImg.getHeight());

        speedY = randInt(6, 13);
        speedx = random.nextDouble() * 4;
        if (randInt(1, 2) == 1) {
            speedx = -speedx;
        }

        x = X;
        y = Y;
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedx;

        if (y < -height | x < -width | x > screenWidth) {
            game.bullets.remove(this);
            game.numberBullets -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.bulletGunnerImg, x, y, null);
    }
}
