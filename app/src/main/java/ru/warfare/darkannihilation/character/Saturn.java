package ru.warfare.darkannihilation.character;

import static ru.warfare.darkannihilation.constant.Constants.SATURN_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.SATURN_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.SATURN_SHOTGUN_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BuckshotSaturn;
import ru.warfare.darkannihilation.bullet.BulletSaturn;
import ru.warfare.darkannihilation.systemd.Game;

public class Saturn extends BaseCharacter {
    public Saturn(Game g) {
        super(g, ImageHub.saturnImg, SATURN_HEALTH, SATURN_SHOOT_TIME, SATURN_SHOTGUN_TIME);
        recreateRect(x + 25, y + 25, right() - 25, bottom() - 17);
    }

    @Override
    public void gun() {
        int X = centerX();
        for (int i = 0; i < randInt(3, 6); i++) {
            game.bullets.add(new BulletSaturn(game, X, game.player.y));
        }
        AudioHub.playShoot();
    }

    @Override
    public void shotgun() {
        game.bullets.add(new BuckshotSaturn(game, centerX(), y));
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
