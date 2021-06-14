package ru.warfare.darkannihilation;

public class AtomicBomb extends Sprite {
    private int frame = 0;
    private static final float frameTime = 30;
    private static final int len = ImageHub.atomBombImage.length - 1;
    private static final float maxHealth = 20;
    private long lastFrame;
    private final int halfwidthM2;
    private final int halfwidthP2;
    private boolean boom = false;

    public AtomicBomb() {
        super(ImageHub.atomBombImage[0].getWidth(), ImageHub.atomBombImage[0].getHeight());

        damage = 1000;
        halfwidthM2 = halfWidth - 2;
        halfwidthP2 = halfWidth + 2;
        newStatus();

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);

        lastFrame = System.currentTimeMillis();
    }

    private void boom() {
        if (HardThread.job == 0) {
            HardThread.job = 8;
            boom = false;
            intersection();
        }
    }

    public void newStatus() {
        lock = true;
        speedX = 0;
        speedY = 1;
        health = (int) maxHealth;
        x = randInt(0, Game.screenWidth);
        y = -height;

        if (buff) {
            up();
        }
    }

    private void up() {
        speedX *= 3;
        speedY *= 3;
    }

    @Override
    public void buff() {
        buff = true;
        up();
    }

    @Override
    public void stopBuff() {
        speedX /= 3;
        speedY /= 3;
        buff = false;
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void intersection() {
        intersectionPlayer();
        Game.score += 50;
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        AudioHub.playMegaBoom();
        newStatus();
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (getRect().intersect(bullet.getRect())) {
            bullet.intersection();
            if (!boom) {
                health -= bullet.damage;
                if (health <= 0) {
                    boom = true;
                }
            }
        }
    }

    @Override
    public void update() {
        if (boom) {
            boom();
        } else {
            long now = System.currentTimeMillis();
            if (now - lastFrame > frameTime) {
                lastFrame = now;
                if (frame != len) {
                    frame++;
                } else {
                    frame = 0;
                }
            }
            y += speedY;

            if (y > Game.screenHeight) {
                newStatus();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.atomBombImage[frame], x, y, Game.alphaPaint);

        if (!boom) {
            Game.canvas.drawRect(centerX() - halfWidth, y + 20, centerX() + halfWidth, y + 35, Game.scorePaint);
            Game.canvas.drawRect(centerX() - halfwidthM2, y + 22, centerX() - halfwidthP2 + ((health / maxHealth) * width), y + 33, Game.fpsPaint);
        }
    }
}