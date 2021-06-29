package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.FACTORY_HEALTH;
import static ru.warfare.darkannihilation.Constants.FACTORY_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.Constants.FACTORY_SPAWN_TIME;
import static ru.warfare.darkannihilation.Constants.FACTORY_SPEED;
import static ru.warfare.darkannihilation.MATH.randInt;

public class Factory extends Sprite {
    private long lastSpawn = System.currentTimeMillis();
    private boolean isSpawn = false;

    private static final int minionY = ImageHub.factoryImg.getHeight() - 100;
    private static int startBarWhiteX;
    private static int startBarRedX;
    private static int endBarWhiteX;
    private static int endBarRedX;

    private static int startBarWhiteY;
    private static int startBarRedY;
    private static int endBarWhiteY;
    private static int endBarRedY;

    public Factory() {
        super(ImageHub.factoryImg.getWidth(), ImageHub.factoryImg.getHeight());

        new Thread(() -> {
            isPassive = true;

            x = Game.halfScreenWidth - halfWidth;

            hide();

            recreateRect(x + 20, y + 80, right() - 20, bottom() - 20);

            startBarWhiteX = centerX() - 250;
            endBarWhiteX = startBarWhiteX + FACTORY_HEALTH_BAR_LEN;
            startBarRedX = startBarWhiteX + 2;
            endBarRedX = startBarWhiteX - 2;

            while (y < 0) {
                y += FACTORY_SPEED;
            }
            startBarWhiteY = y + 60;
            startBarRedY = y + 62;

            endBarWhiteY = y + 75;
            endBarRedY = y + 73;

            y = -height;
        }).start();
    }

    public void hide() {
        isSpawn = false;
        lock = true;
        y = -height;
        health = FACTORY_HEALTH;
    }

    public void spawn() {
        long now = System.currentTimeMillis();
        if (now - lastSpawn > FACTORY_SPAWN_TIME) {
            lastSpawn = now;
            Game.allSprites.add(new Minion(randInt(x, right), minionY));
            Game.allSprites.add(new Minion(randInt(x, right), minionY));
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 20, y + 80);
    }

    @Override
    public void intersection() {
        Game.score += 75;
        createSkullExplosion();
        hide();
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (getRect().intersect(bullet.getRect())) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                intersection();
            }
        }
    }

    @Override
    public void update() {
        if (y < 0) {
            y += FACTORY_SPEED;
        } else {
            if (!isSpawn) {
                isSpawn = true;
            }
            spawn();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.factoryImg, x, y, null);

        if (!isSpawn) {
            Game.canvas.drawRect(startBarWhiteX, y + 60, endBarWhiteX, y + 75, Game.scorePaint);
            Game.canvas.drawRect(startBarRedX, y + 62, endBarRedX + ((health / (float) FACTORY_HEALTH) * FACTORY_HEALTH_BAR_LEN), y + 73, Game.fpsPaint);
        } else {
            Game.canvas.drawRect(startBarWhiteX, startBarWhiteY, endBarWhiteX, endBarWhiteY, Game.scorePaint);
            Game.canvas.drawRect(startBarRedX, startBarRedY, endBarRedX + ((health / (float) FACTORY_HEALTH) * FACTORY_HEALTH_BAR_LEN), endBarRedY, Game.fpsPaint);
        }
    }
}
