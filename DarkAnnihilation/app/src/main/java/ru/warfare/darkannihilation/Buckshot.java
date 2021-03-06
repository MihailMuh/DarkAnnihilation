package ru.warfare.darkannihilation;

public class Buckshot extends Sprite {
    public int damage = 2;

    public Buckshot(Game g, int X, int Y, int speed) {
        super(g, ImageHub.buckshotImg.getWidth(), ImageHub.buckshotImg.getHeight());

        x = X - halfWidth;
        y = Y - halfHeight;

        speedX = speed;
        speedY = 8;
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > screenWidth) {
            game.buckshots.remove(this);
            game.numberBuckshots -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.buckshotImg, x, y, null);
    }
}
