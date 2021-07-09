package ru.warfare.darkannihilation.bullet;

import android.graphics.Color;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.LIGHTNING_DAMAGE;
import static ru.warfare.darkannihilation.Constants.LIGHTNING_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.NUMBER_LIGHTNING_IMAGES;

public class BulletThunder extends Sprite {
    private int frame;
    private long lastShoot = System.currentTimeMillis();
    private static final int len = NUMBER_LIGHTNING_IMAGES - 1;

    public BulletThunder(int X, int Y) {
        super(ImageHub.thunderImage[0]);
        damage = LIGHTNING_DAMAGE;
        isPassive = true;
        isBullet = true;

        x = X - halfWidth;
        y = Y - height;
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > LIGHTNING_SHOOT_TIME) {
            lastShoot = now;
            if (frame != len) {
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
                Game.canvas.drawBitmap(ImageHub.thunderImage[frame],
                        x, y, Game.nicePaint);
                break;
        }
    }
}
