package ru.warfare.darkannihilation;


public class Buckshot extends BulletBase {
    public Buckshot(Game g, int X, int Y, int speed) {
        super(g, ImageHub.buckshotImg.getWidth(), ImageHub.buckshotImg.getHeight());

        x = X - halfWidth;
        y = Y;
        damage = 2;

        speedX = speed;
        speedY = 8;
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > screenWidth) {
            game.bullets.remove(this);
            game.numberBullets -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.buckshotImg, x, y, null);
    }
}
