package ru.warfare.darkannihilation.character;

import static ru.warfare.darkannihilation.constant.Constants.BOT_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;

public class Bot extends BaseCharacter {
    private final int finalX;
    private final int finalY;

    public Bot(Game game) {
        super(game, ImageHub.playerImage, 0, BOT_SHOOT_TIME, BOT_SHOOT_TIME);
        calculateBarriers();

        speedX = randInt(3, 7);
        speedY = randInt(3, 7);
        finalX = screenWidthWidth - 30;
        finalY = screenHeightHeight - 30;

        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);
    }

    @Override
    public void shotgun() {
    }

    @Override
    public void gun() {
        int X = centerX() - 3;
        int count = 0;

        for (BaseBullet bullet : game.bullets) {
            if (bullet.lock) {
                bullet.start(X, y);
                X += 6;
                count++;
            }
            if (count == 2) {
                break;
            }
        }

        AudioHub.playShoot();
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
        x += speedX;
        y += speedY;

        if (x < 30 || x > finalX) {
            speedX = -speedX;
        }
        if (y < 30 || y > finalY) {
            speedY = -speedY;
        }
    }
}
