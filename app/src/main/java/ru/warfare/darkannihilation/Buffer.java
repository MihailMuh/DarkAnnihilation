package ru.warfare.darkannihilation;

public class Buffer extends Sprite {
    private final float maxHealth;
    private boolean startBuff;
    private boolean boom = false;

    public Buffer() {
        super(ImageHub.bufferImg.getWidth(), ImageHub.bufferImg.getHeight());
        maxHealth = 35;
        hide();

        recreateRect(x + 70, y + 70, right() - 70, bottom() - 35);
    }

    private void boom() {
        if (HardThread.job == 0) {
            HardThread.job = 5;
            boom = false;
            AudioHub.playMegaBoom();
            createSkullExplosion();
            hide();
            Game.score += 100;
        }
    }

    public void hide() {
        startBuff = false;
        lock = true;
        health = (int) maxHealth;
        x = randInt(width, screenWidthWidth);
        y = -height;
        speedY = randInt(5, 10);
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 70, y + 70);
    }

    @Override
    public void intersection() {
        boom = true;
    }

    @Override
    public void intersectionPlayer() {
        boom = true;
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
        if (!boom) {
            if (y < 35) {
                y += speedY;
            } else {
                if (!startBuff) {
                    if (HardThread.job == 0) {
                        HardThread.job = 6;
                        startBuff = true;
                    }
                }
            }
        } else {
            boom();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bufferImg, x, y, Game.alphaPaint);

        if (!boom) {
            Game.canvas.drawRect(centerX() - 75, y + 50, centerX() + 75, y + 65, Game.scorePaint);
            Game.canvas.drawRect(centerX() - 73, y + 52, centerX() - 77 + (health / maxHealth) * 150, y + 63, Game.fpsPaint);
        }
    }
}

