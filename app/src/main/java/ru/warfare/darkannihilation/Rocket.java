package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.ROCKET_DAMAGE;
import static ru.warfare.darkannihilation.Constants.ROCKET_SPEED;

public class Rocket extends Sprite {
    public Rocket() {
        super(ImageHub.rocketImg.getWidth(), ImageHub.rocketImg.getHeight());
        speedY = ROCKET_SPEED;
        damage = ROCKET_DAMAGE;
        isBullet = true;
        status = "rocket";

        hide();
    }

    public void hide() {
        lock = true;
        y = -height;
    }

    public void start(int X) {
        x = X - halfWidth;
        lock = false;
    }

    public void checkIntersections(Sprite sprite) {
        if (!lock) {
            if (getRect().intersect(sprite.getRect())) {
                if (!sprite.isPassive | sprite.status.equals("saturn")) {
                    sprite.intersection();
                }
            }
        }
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        hide();
    }


    @Override
    public void update() {
        y += speedY;

        if (y > Game.screenHeight) {
            hide();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.rocketImg, x, y, null);
    }
}
