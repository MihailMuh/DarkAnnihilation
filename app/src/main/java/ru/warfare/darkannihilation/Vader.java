package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class Vader extends Sprite {
    public Bitmap img;

    public Vader(Game g) {
        super(g, ImageHub.vaderImage[0].getWidth(), ImageHub.vaderImage[0].getHeight());
        health = 2;
        damage = 5;

        img = ImageHub.vaderImage[randInt(0, 2)];

        x = randInt(0, screenWidth);
        y = -150;
        speedX = randInt(-5, 5);
        speedY = randInt(3, 10);

        rect = new Rect(x + 15, y + 15, x + width - 15, y + height - 15);
    }

    public void newStatus() {
        if (game.numberBosses != 0) {
            lock = true;
        }
        health = 2;
        x = randInt(0, screenWidth);
        y = -150;
        speedX = randInt(-5, 5);
        speedY = randInt(3, 10);
    }

    @Override
    public Rect getRect() {
        rect.offsetTo(x + 15, y + 15);
        return rect;
    }

    @Override
    public void intersection() {
        for (int i = numberSmallExplosionsTriple; i < numberMediumExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playBoom();
        game.score += 1;
        newStatus();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMetal();
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playBoom();
        newStatus();
    }

    @Override
    public void check_intersectionBullet(BulletBase bullet) {
        if (Rect.intersects(getRect(), bullet.getRect())) {
            if (bullet.damage < health) {
                health -= bullet.damage;
                bullet.intersection();
                if (health <= 0) {
                    intersection();
                }
            } else {
                intersection();
            }
        }
    }

    @Override
    public void empireStart() {
        lock = false;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (x < -width | x > game.screenWidth | y > game.screenHeight) {
            newStatus();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(img, x, y, null);
    }
}
