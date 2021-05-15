package ru.warfare.darkannihilation;

public class Factory extends Sprite {
    private static final int spawnTime = 1_000;
    private long lastSpawn;
    private final float maxHealth;

    public Factory() {
        super(ImageHub.factoryImg.getWidth(), ImageHub.factoryImg.getHeight());

        health = 200;
        speedY = 3;
        lock = true;
        maxHealth = health;
        isPassive = true;

        x = Game.halfScreenWidth - halfWidth;
        y = -height;

        recreateRect(x + 20, y + 80, x + width - 20, y + height - 20);

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
            Game.allSprites.add(new Minion(x));
            Game.allSprites.add(new Minion(x));
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 20, y + 80);
    }

    @Override
    public void intersection() {
        AudioPlayer.playMegaBoom();
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

        Game.canvas.drawRect(x + halfWidth - 250, y + 60, x + halfWidth + 250, y + 75 , Game.scorePaint);
        Game.canvas.drawRect(x + halfWidth - 248, y + 62, x + halfWidth - 252 + (health / maxHealth) * 500, y + 73, Game.fpsPaint);
    }
}
