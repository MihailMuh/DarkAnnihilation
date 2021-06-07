package ru.warfare.darkannihilation;

public class Factory extends Sprite {
    private static final int spawnTime = 1_000;
    private long lastSpawn;
    private final float maxHealth;
    private static final int minionY = ImageHub.factoryImg.getHeight() - 100;

    public Factory() {
        super(ImageHub.factoryImg.getWidth(), ImageHub.factoryImg.getHeight());

        health = 200;
        speedY = 3;
        lock = true;
        maxHealth = health;
        isPassive = true;

        x = Game.halfScreenWidth - halfWidth;
        y = -height;

        recreateRect(x + 20, y + 80, right() - 20, bottom() - 20);

        lastSpawn = System.currentTimeMillis();
    }

    public void hide() {
        lock = true;
        y = -height;
        health = (int) maxHealth;
    }

    public void spawn() {
        long now = System.currentTimeMillis();
        if (now - lastSpawn > spawnTime) {
            lastSpawn = now;
            Game.allSprites.add(new Minion(randInt(x, x + ImageHub.factoryImg.getWidth()), minionY));
            Game.allSprites.add(new Minion(randInt(x, x + ImageHub.factoryImg.getWidth()), minionY));
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 20, y + 80);
    }

    @Override
    public void intersection() {
        AudioHub.playMegaBoom();
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
            y += speedY;
        } else {
            spawn();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.factoryImg, x, y, null);

        Game.canvas.drawRect(centerX() - 250, y + 60, centerX() + 250, y + 75 , Game.scorePaint);
        Game.canvas.drawRect(centerX() - 248, y + 62, centerX() - 252 + (health / maxHealth) * 500, y + 73, Game.fpsPaint);
    }
}
