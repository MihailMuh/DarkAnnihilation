package ru.warfare.darkannihilation;

public class Bullet extends Sprite {
    public int damage = 1;

    public Bullet(Game g, int X, int Y) {
        super(g, ImageHub.bulletImage.getWidth(), ImageHub.bulletImage.getHeight());
        speedY = 10;

        x = X;
        y = Y;
    }

    @Override
    public void update() {
        y -= speedY;

        if (y < -50) {
            game.bullets.remove(this);
            game.numberBullets -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.bulletImage, x, y, null);
    }
}
