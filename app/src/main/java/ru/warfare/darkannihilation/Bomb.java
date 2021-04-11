package ru.warfare.darkannihilation;

public class Bomb extends Sprite {
    public int damage = 5;

    public Bomb(Game g, int X, int Y) {
        super(g, ImageHub.bombImg.getWidth(), ImageHub.bombImg.getHeight());
        speedY = 15;

        x = X;
        y = Y;

        AudioPlayer.playFallingBomb();
    }

    @Override
    public void update() {
        y += speedY;

        if (y > screenHeight) {
            game.bombs.remove(this);
            game.numberBombs -= 1;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.bombImg, x, y, null);
    }
}