package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.Bullet;
import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.Constants.BOT_SHOOT_TIME;

public class Bot extends BaseCharacter {
    private final int finalX;
    private final int finalY;

    public Bot() {
        super(ImageHub.playerImage);
        speedX = Math.randInt(3, 7);
        speedY = Math.randInt(3, 7);

        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;

        finalX = screenWidthWidth - 30;
        finalY = screenHeightHeight - 30;

        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > BOT_SHOOT_TIME) {
            int X = centerX();

            Bullet bullet = new Bullet(X - 6, y);
            Game.bullets.add(bullet);
            Game.allSprites.add(bullet);

            bullet = new Bullet(X, y);
            Game.bullets.add(bullet);
            Game.allSprites.add(bullet);

            lastShoot = now;
            AudioHub.playShoot();
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 20, y + 25);
    }

    @Override
    public void checkIntersections(Sprite sprite) {
        if (intersect(sprite)) {
            sprite.intersectionPlayer();
        }
    }

    @Override
    public void update() {
        shoot();
        x += speedX;
        y += speedY;

        if (x < 30 | x > finalX) {
            speedX = -speedX;
        }
        if (y < 30 | y > finalY) {
            speedY = -speedY;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, null);
    }
}
