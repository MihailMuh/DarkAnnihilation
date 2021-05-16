package ru.warfare.darkannihilation;

public class Buffer extends Sprite {
    private final float maxHealth;
    private boolean startBuff;

    public Buffer() {
        super(ImageHub.bufferImg.getWidth(), ImageHub.bufferImg.getHeight());
        maxHealth = 30;
        hide();

        recreateRect(x + 70, y + 70, right() - 70, bottom() - 35);
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
        intersectionPlayer();
        Game.score += 100;
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMegaBoom();
        createSkullExplosion();
        hide();
        for (int i = 0; i < Game.allSprites.size(); i++) {
            Game.allSprites.get(i).stopBuff();
        }
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
            if (!startBuff) {
                for (int i = 0; i < Game.allSprites.size(); i++) {
                    Game.allSprites.get(i).buff();
                }
                startBuff = true;
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bufferImg, x, y, Game.alphaPaint);

        Game.canvas.drawRect(centerX() - 75, y + 50, centerX() + 75, y + 65 , Game.scorePaint);
        Game.canvas.drawRect(centerX() - 73, y + 52, centerX() - 77 + (health / maxHealth) * 150, y + 63, Game.fpsPaint);
    }
}

