package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.BOT_SHOOT_TIME;

public class Bot extends BaseCharacter {
    private final int finalX;
    private final int finalY;

    public Bot() {
        super(ImageHub.playerImage.getWidth(), ImageHub.playerImage.getHeight());
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);

        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;

        finalX = screenWidthWidth - 30;
        finalY = screenHeightHeight - 30;

        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);

        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > BOT_SHOOT_TIME) {
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

    @Override
    public Sprite getRect() {
        return goTO(x + 20, y + 25);
    }

    @Override
    public void checkIntersections(Sprite sprite) {
        if (getRect().intersect(sprite.getRect())) {
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
        Game.canvas.drawBitmap(ImageHub.playerImage, x, y, null);
    }
}
