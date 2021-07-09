package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.bullet.Buckshot;
import ru.warfare.darkannihilation.bullet.Bullet;

import static ru.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_SHOTGUN_TIME;

public class MillenniumFalcon extends BaseCharacter {
    public MillenniumFalcon(Game g) {
        super(g, ImageHub.playerImage);

        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (gun.equals("shotgun")) {
            if (now - lastShoot > MILLENNIUM_FALCON_SHOTGUN_TIME) {
                lastShoot = now;
                AudioHub.playShotgun();
                int centerX = centerX();
                for (int i = -4; i <= 4; i += 2) {
                    Buckshot buckshot = new Buckshot(centerX, y, i);
                    Game.bullets.add(buckshot);
                    Game.allSprites.add(buckshot);
                }
            }
        } else {
            if (now - lastShoot > MILLENNIUM_FALCON_SHOOT_TIME) {
                lastShoot = now;
                AudioHub.playShoot();
                Bullet bullet = new Bullet(centerX() - 6, y);
                Game.bullets.add(bullet);
                Game.allSprites.add(bullet);

                bullet = new Bullet(centerX(), y);
                Game.bullets.add(bullet);
                Game.allSprites.add(bullet);
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
