package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.SpriteWrapper;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.ROCKET_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.ROCKET_SPEED;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class Rocket extends SpriteWrapper {
    public Rocket(Game game) {
        super(game, ImageHub.rocketImg);

        damage = ROCKET_DAMAGE;
        lock = true;
    }

    public void start(int X) {
        x = X - halfWidth;
        y = -height;
        lock = false;
    }

    public void checkIntersections(Sprite sprite) {
        if (intersect(sprite)) {
            sprite.kill();
        }
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        lock = true;
    }

    @Override
    public void update() {
        y += ROCKET_SPEED;

        if (y > SCREEN_HEIGHT) {
            lock = true;
        }
    }
}
