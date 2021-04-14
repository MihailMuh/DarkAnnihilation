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
    public void intersection() {
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        game.bulletEnemies.remove(this);
    }

    @Override
    public void update() {
        y += speedY;

        if (y > screenHeight) {
            game.bulletEnemies.remove(this);
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.bombImg, x, y, null);
    }
}