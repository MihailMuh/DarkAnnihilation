package ru.warfare.darkannihilation;

public class BulletGunner extends BulletBase {
    public double damage = 0.5;
    private final double speedx;

    public BulletGunner(Game g, int X, int Y) {
        super(g, ImageHub.bulletGunnerImg.getWidth(), ImageHub.bulletGunnerImg.getHeight());

        speedY = 6;
        speedx = (random.nextDouble() * 2) - 1;

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
        game.canvas.drawBitmap(ImageHub.buttonGunnerImg, x, y, null);
    }
}
