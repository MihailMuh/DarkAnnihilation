package ru.warfare.darkannihilation;

public class Spider extends Sprite {
    private int shootTripleTime;
    private long lastShoot;

    private int ammo;
    private boolean reload;
    private final float maxHealth;

    public Spider() {
        super(ImageHub.spiderImg.getWidth(), ImageHub.spiderImg.getHeight());
        damage = 20;
        maxHealth = 200;
        hide();

        recreateRect(x + 25, y + 5, right() - 5, y + halfHeight + (halfHeight / 2));

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;
            if (!reload) {
                if (HardWorker.job == 0) {
                    HardWorker.x = centerX();
                    HardWorker.y = centerY();
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
        ammo = 0;
        health = (int) maxHealth;
        x = randInt(width, screenWidthWidth);
        y = -height;
        speedY = randInt(5, 10);
        shootTripleTime = 100;

        if (buff) {
            shootTripleTime /= 2;
        }
    }

    @Override
    public void buff() {
        buff = true;
        shootTripleTime /= 2;
    }

    @Override
    public void stopBuff() {
        shootTripleTime = 100;
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 25, y + 5);
    }

    @Override
    public void intersection() {
        intersectionPlayer();
        Game.score += 50;
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMegaBoom();
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
        if (y < 35) {
            y += speedY;
        } else {
            shoot();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.spiderImg, x, y, Game.alphaPaint);

        Game.canvas.drawRect(centerX() - 75, y + 10, centerX() + 75, y + 25 , Game.scorePaint);
        Game.canvas.drawRect(centerX() - 73, y + 12, centerX() - 77 + (health / maxHealth) * 150, y + 23, Game.fpsPaint);
    }
}
