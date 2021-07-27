package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.ROCKET_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.ROCKET_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_SATURN;

public class Rocket extends Sprite {
    public Rocket(Game game) {
        super(game, ImageHub.rocketImg);
        speedY = ROCKET_SPEED;
        damage = ROCKET_DAMAGE;
        isBullet = true;

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
                if (!sprite.isPassive | sprite.name == BULLET_SATURN) {
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
