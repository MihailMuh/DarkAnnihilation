package ru.warfare.darkannihilation;


public class Buckshot extends Sprite {
    public int damage = 2;

    public Buckshot(Game g, int X, int Y, int speed) {
        super(g, ImageHub.buckshotImg.getWidth(), ImageHub.buckshotImg.getHeight());

        x = X - halfWidth;
        y = Y;

        speedX = speed;
        speedY = 8;

//        Log.e("shoot", "" + speedX + " " + x + " " + (game.player.x + game.player.halfWidth));
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height) {
            game.buckshots.remove(this);
            game.numberBuckshots -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.buckshotImg, x, y, null);
    }
}
