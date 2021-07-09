package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.ROCKET_DAMAGE;
import static ru.warfare.darkannihilation.Constants.ROCKET_SPEED;

public class Rocket extends Sprite {
    public Rocket() {
        super(ImageHub.rocketImg);
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
            if (intersect(sprite)) {
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
}
