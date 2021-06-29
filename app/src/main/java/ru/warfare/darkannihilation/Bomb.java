package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

import static ru.warfare.darkannihilation.Constants.BOMB_DAMAGE;
import static ru.warfare.darkannihilation.Constants.BOMB_SPEED;

public class Bomb extends Sprite {
    public Bomb(int X, int Y) {
        super(ImageHub.bombImg.getWidth(), ImageHub.bombImg.getHeight());
        damage = BOMB_DAMAGE;
        status = "bulletEnemy";
        isBullet = true;

        x = X;
        y = Y;

        AudioHub.playFallingBomb();
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
        return new Object[] {ImageHub.rotateImage(ImageHub.bombImg, 180)};
    }

    @Override
    public void intersectionPlayer() {
        createSmallExplosion();
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y += BOMB_SPEED;

        if (y > Game.screenHeight) {
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bombImg, x, y, null);
    }
}