package ru.warfare.darkannihilation;

public class Bomb extends BulletBase {
    public Bomb(Game g, int X, int Y) {
        super(g, ImageHub.bombImg.getWidth(), ImageHub.bombImg.getHeight());
        speedY = 15;
        damage = 5;

        x = X;
        y = Y;

        AudioPlayer.playFallingBomb();
    }

    @Override
    public void update() {
        y += speedY;

        if (y > screenHeight) {
            game.bulletEnemies.remove(this);
            game.numberBulletsEnemy -= 1;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.bombImg, x, y, null);
    }
}