package ru.warfare.darkannihilation.character;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SATURN_BUCKSHOT;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SATURN_BULLETS;
import static ru.warfare.darkannihilation.constant.Constants.SATURN_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.SATURN_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.SATURN_SHOTGUN_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;

public class Saturn extends BaseCharacter {
    public Saturn(Game g) {
        super(g, ImageHub.saturnImg, SATURN_HEALTH, SATURN_SHOOT_TIME, SATURN_SHOTGUN_TIME);
        recreateRect(x + 25, y + 25, right() - 25, bottom() - 17);
    }

    @Override
    public void gun() {
        int X = centerX();
        int count = randInt(2, 5);

        for (int i = 0; i < NUMBER_SATURN_BULLETS; i++) {
            if (game.bullets[i].lock) {
                game.bullets[i].start(X, y);
                count--;
            }
            if (count == -1) {
                break;
            }
        }
        AudioHub.playShoot();
    }

    @Override
    public void shotgun() {
        for (int i = NUMBER_SATURN_BULLETS; i < NUMBER_SATURN_BUCKSHOT; i++) {
            if (game.bullets[i].lock) {
                game.bullets[i].start(centerX(), y);
                break;
            }
        }
    }

    @Override
    public void baseHeal() {
        health += 10;
        if (health - maxHealth > 0) {
            addArmorHeart();
            maxHealth = health;
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 25, y + 25);
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        speedX = (endX - x) / 20;
        speedY = (endY - y) / 20;
    }
}
