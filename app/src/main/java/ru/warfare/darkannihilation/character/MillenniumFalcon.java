package ru.warfare.darkannihilation.character;

import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_SHOTGUN_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_MILLENNIUM_FALCON_BUCKSHOT;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;

public class MillenniumFalcon extends BaseCharacter {
    public MillenniumFalcon(Game g) {
        super(g, ImageHub.playerImage, MILLENNIUM_FALCON_HEALTH, MILLENNIUM_FALCON_SHOOT_TIME, MILLENNIUM_FALCON_SHOTGUN_TIME);
        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);
    }

    @Override
    public void gun() {
        int X = centerX() - 6;
        int count = 0;
        for (int i = 0; i < NUMBER_MILLENNIUM_FALCON_BULLETS; i++) {
            if (game.bullets[i].lock) {
                game.bullets[i].start(X, y);
                X += 6;
                count++;
            }
            if (count == 3) {
                break;
            }
        }
        AudioHub.playShoot();
    }

    @Override
    public void shotgun() {
        int X = centerX();
        int count = -4;

        for (int i = NUMBER_MILLENNIUM_FALCON_BULLETS; i < NUMBER_MILLENNIUM_FALCON_BUCKSHOT; i++) {
            if (game.bullets[i].lock) {
                game.bullets[i].start(X, y, count);
                count += 2;
            }
            if (count == 6) {
                break;
            }
        }
        AudioHub.playShotgun();
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 20, y + 25);
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        speedX = (endX - x) / 5;
        speedY = (endY - y) / 5;
    }
}
