package ru.warfare.darkannihilation;

import android.graphics.Color;

public class BulletThunder extends Sprite {
    private int frame;
    private long lastShoot = System.currentTimeMillis();
    private static final int shootTime = 35;

    public BulletThunder(int X, int Y) {
        super(ImageHub.thunderImage[0].getWidth(), ImageHub.thunderImage[0].getHeight());
        damage = 1;
        isPassive = true;
        isBullet = true;

        x = X - halfWidth;
        y = Y - height;
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            if (frame != 12) {
                frame++;
            } else {
                Game.bullets.remove(this);
                Game.allSprites.remove(this);
            }
        }
    }

    @Override
    public void render () {
        switch (frame)
        {
            case 2:
                Game.canvas.drawColor(Color.parseColor("#494d54"));
                break;
            case 3:
                Game.canvas.drawColor(Color.parseColor("#444664"));
                break;
            default:
                Game.canvas.drawBitmap(ImageHub.thunderImage[frame], x, y, null);
                break;
        }
    }
}
