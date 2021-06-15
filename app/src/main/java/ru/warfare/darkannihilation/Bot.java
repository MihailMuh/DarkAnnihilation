package ru.warfare.darkannihilation;

public class Bot extends BaseCharacter {
    public Bot() {
        super(ImageHub.playerImage.getWidth(), ImageHub.playerImage.getHeight());
        speedX = randInt(3, 7);
        speedY = randInt(3, 7);

        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;

        recreateRect(x + 20, y + 25, right() - 20, bottom() - 20);

        shootTime = 100;
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootTime) {
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

        if (x < 30 | x > screenWidthWidth - 30) {
            speedX = -speedX;
        }
        if (y < 30 | y > screenHeightHeight - 30) {
            speedY = -speedY;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.playerImage, x, y, null);
    }
}
