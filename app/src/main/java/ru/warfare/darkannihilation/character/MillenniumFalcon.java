package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.bullet.Buckshot;
import ru.warfare.darkannihilation.bullet.Bullet;

import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_SHOTGUN_TIME;
import static ru.warfare.darkannihilation.constant.NamesConst.SHOTGUN;

public class MillenniumFalcon extends BaseCharacter {
    public MillenniumFalcon(Game g) {
        super(g, ImageHub.playerImage, MILLENNIUM_FALCON_HEALTH);
        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (gun == SHOTGUN) {
            if (now - lastShoot > MILLENNIUM_FALCON_SHOTGUN_TIME) {
                lastShoot = now;
                AudioHub.playShotgun();
                int centerX = centerX();
                for (int i = -4; i <= 4; i += 2) {
                    Buckshot buckshot = new Buckshot(game, centerX, y, i);
                    game.bullets.add(buckshot);
                }
            }
        } else {
            if (now - lastShoot > MILLENNIUM_FALCON_SHOOT_TIME) {
                lastShoot = now;
                AudioHub.playShoot();
                int X = centerX();

                Bullet bullet = new Bullet(game, X + 3, y);
                game.bullets.add(bullet);

                bullet = new Bullet(game, X - 3, y);
                game.bullets.add(bullet);
            }
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 20, y + 25);
    }

    @Override
    public void update() {
        if (!lock) {
            shoot();
        }
        x += speedX;
        y += speedY;

        speedX = (endX - x) / 5;
        speedY = (endY - y) / 5;
    }
}
