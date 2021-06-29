package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.BUCKSHOT_DAMAGE;
import static ru.warfare.darkannihilation.Constants.BUCKSHOT_SPEED;

public class Buckshot extends Sprite {
    public Buckshot(int X, int Y, int speed) {
        super(ImageHub.buckshotImg.getWidth(), ImageHub.buckshotImg.getHeight());

        x = X - halfWidth;
        y = Y;
        damage = BUCKSHOT_DAMAGE;
        isPassive = true;
        isBullet = true;

        speedX = speed;
    }

    @Override
    public void intersection() {
        createSmallTripleExplosion();
        Game.bullets.remove(this);
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y -= BUCKSHOT_SPEED;
        x += speedX;

        if (y < -height | x < -width | x > Game.screenWidth) {
            Game.bullets.remove(this);
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.buckshotImg, x, y, null);
    }
}
