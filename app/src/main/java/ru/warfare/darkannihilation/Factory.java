package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.FACTORY_HEALTH;
import static ru.warfare.darkannihilation.Constants.FACTORY_HEALTH_BAR_HEIGHT;
import static ru.warfare.darkannihilation.Constants.FACTORY_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.Constants.FACTORY_SPAWN_TIME;
import static ru.warfare.darkannihilation.Constants.FACTORY_SPEED;
import static ru.warfare.darkannihilation.MATH.randInt;

public class Factory extends Sprite {
    private long lastSpawn = System.currentTimeMillis();
    private boolean isSpawn = false;
    private float hp = 10;

    private static final int minionY = ImageHub.factoryImg.getHeight() - 100;

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

            while (y < 0) {
                y += FACTORY_SPEED;
            }
            startBarWhiteY = y + 60;
            startBarRedY = startBarWhiteY + 3;
            endBarWhiteY = startBarWhiteY + FACTORY_HEALTH_BAR_HEIGHT;
            endBarRedY = endBarWhiteY - 3;

            y = -height;
        }).start();
    }

    public void hide() {
        hp = 10;
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
        return newRect(x + 20, y + 80);
    }

    @Override
    public void intersection() {
        Game.score += 75;
        createSkullExplosion();
        hide();
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

        if (hp > 6) {
            hp = ((health / (float) FACTORY_HEALTH) * FACTORY_HEALTH_BAR_LEN);
        } else {
            hp = 6;
        }
        if (!isSpawn) {
            Game.canvas.drawRect(centerX() - 250, y + 60, centerX() + 250, y + 75, Game.scorePaint);
            Game.canvas.drawRect(centerX() - 247, y + 62, centerX() - 253 + hp, y + 73, Game.topPaintRed);
        } else {
            Game.canvas.drawRect(centerX() - 250, startBarWhiteY, centerX() + 250, endBarWhiteY, Game.scorePaint);
            Game.canvas.drawRect(centerX() - 247, startBarRedY, centerX() - 253 + hp, endBarRedY, Game.topPaintRed);
        }
    }
}
