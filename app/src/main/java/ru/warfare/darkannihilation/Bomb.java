package ru.warfare.darkannihilation;

public class Bomb extends Sprite {
    public Bomb(int X, int Y) {
        super(ImageHub.bombImg.getWidth(), ImageHub.bombImg.getHeight());
        speedY = 15;
        damage = 5;
        status = "bulletEnemy";
        isBullet = true;

        x = X;
        y = Y;

        AudioPlayer.playFallingBomb();
    }

    @Override
    public void intersectionPlayer() {
        createSmallExplosion();
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y += speedY;

        if (y > Game.screenHeight) {
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bombImg, x, y, null);
    }
}