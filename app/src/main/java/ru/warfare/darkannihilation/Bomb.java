package ru.warfare.darkannihilation;

public class Bomb extends BaseBullet {
    public Bomb(Game g, int X, int Y) {
        super(g, ImageHub.bombImg.getWidth(), ImageHub.bombImg.getHeight());
        speedY = 15;
        damage = 5;

        x = X;
        y = Y;

        AudioPlayer.playFallingBomb();
    }

    @Override
    public void intersectionPlayer() {
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y += speedY;

        if (y > screenHeight) {
            game.allSprites.remove(this);
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.bombImg, x, y, null);
    }
}