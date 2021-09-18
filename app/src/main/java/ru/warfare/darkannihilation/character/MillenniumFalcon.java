package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.bullet.Buckshot;
import ru.warfare.darkannihilation.bullet.Bullet;

import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_SHOTGUN_TIME;

public class MillenniumFalcon extends BaseCharacter {
    public MillenniumFalcon(Game g) {
        super(g, ImageHub.playerImage, MILLENNIUM_FALCON_HEALTH, MILLENNIUM_FALCON_SHOOT_TIME, MILLENNIUM_FALCON_SHOTGUN_TIME);
        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);
    }

    @Override
    public void gun() {
        int X = centerX();

        for (int i = -6; i <= 6; i += 6) {
            Bullet bullet = new Bullet(game, X + i, y);
            game.bullets.add(bullet);
        }
        AudioHub.playShoot();
    }

    @Override
    public void shotgun() {
        int centerX = centerX();
        for (int i = -4; i <= 4; i += 2) {
            Buckshot buckshot = new Buckshot(game, centerX, y, i);
            game.bullets.add(buckshot);
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
