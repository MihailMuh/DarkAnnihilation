package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.BUFFER_HEALTH;

public class Buffer extends Sprite {
    private boolean startBuff;
    private boolean boom = false;

    public Buffer() {
        super(ImageHub.bufferImg.getWidth(), ImageHub.bufferImg.getHeight());
        hide();

        recreateRect(x + 70, y + 70, right() - 70, bottom() - 35);
    }

    private void boom() {
        if (startBuff) {
            if (HardThread.job == 0) {
                HardThread.job = 5;
                inter();
            }
        } else {
            inter();
        }
    }

    private void inter() {
        boom = false;
        createSkullExplosion();
        hide();
        Game.score += 100;
    }

    public void hide() {
        startBuff = false;
        lock = true;
        health = BUFFER_HEALTH;
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
            Game.canvas.drawRect(centerX() - 73, y + 52, centerX() - 77 + (health / (float) BUFFER_HEALTH) * 150, y + 63, Game.fpsPaint);
        }
    }
}

