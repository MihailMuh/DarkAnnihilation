package ru.warfare.darkannihilation;

public class Spider extends Sprite {
    private int shootTripleTime;
    private long lastShoot;

    private int ammo;
    private boolean reload;
    private final float maxHealth;

    public Spider(Game g) {
        super(g, ImageHub.spiderImg.getWidth(), ImageHub.spiderImg.getHeight());
        damage = 20;
        maxHealth = 200;
        hide();

        recreateRect(x + 25, y + 5, x + width - 5, y + halfHeight + (halfHeight / 2));

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;
            if (!reload) {
                if (HardWorker.job == 0) {
                    HardWorker.x = x + halfWidth;
                    HardWorker.y = y + halfHeight;
                    HardWorker.job = 1;
                    ammo++;
                    reload = false;
                }
            } else {
                ammo--;
                if (ammo == 0) {
                    shootTripleTime = 100;
                    reload = false;
                }
            }
            if (ammo >= 20) {
                reload = true;
                shootTripleTime = 200;
            }
        }
    }

    public void hide() {
        lock = true;
        reload = false;
        shootTripleTime = 100;
        ammo = 0;
        health = (int) maxHealth;
        x = randInt(width, screenWidth - width);
        y = -height;
        speedY = randInt(5, 10);
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 25, y + 5);
    }

    @Override
    public void intersection() {
        AudioPlayer.playMegaBoom();
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        game.score += 50;
        hide();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMegaBoom();
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
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
        if (y < 35) {
            y += speedY;
        } else {
            shoot();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.spiderImg, x, y, Game.alphaPaint);

        game.canvas.drawRect(x + halfWidth - 75, y + 10, x + halfWidth + 75, y + 25 , Game.scorePaint);
        game.canvas.drawRect(x + halfWidth - 73, y + 12, x + halfWidth - 77 + (health / maxHealth) * 150, y + 23, Game.fpsPaint);
    }
}
