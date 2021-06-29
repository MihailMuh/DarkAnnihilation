package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class Sprite {
    public Game game;

    public int x = 0;
    public int y = 0;
    public int speedX = 0;
    public int speedY = 0;

    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;

    public final int screenHeightHeight;
    public final int screenWidthWidth;

    public int health = 0;
    public int damage = 0;

    public boolean buff = false;
    public boolean lock = false;
    public boolean isPassive = false;
    public boolean isBullet = false;

    public String status = "";

    public int left;
    public int top;
    public int right;
    public int bottom;

    public Sprite() {
        screenHeightHeight = Game.screenHeight - height;
        screenWidthWidth = Game.screenWidth - width;
    }

    public Sprite(Game game) {
        this.game = game;

        screenHeightHeight = Game.screenHeight - height;
        screenWidthWidth = Game.screenWidth - width;
    }

    public Sprite(Game g, int w, int h) {
        game = g;

        width = w;
        height = h;
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;

        screenHeightHeight = Game.screenHeight - height;
        screenWidthWidth = Game.screenWidth - width;
    }

    public Sprite(int w, int h) {
        width = w;
        height = h;
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;

        screenHeightHeight = Game.screenHeight - height;
        screenWidthWidth = Game.screenWidth - width;
    }

    public void update() {}
    public void render() {}

    public void intersection() {}
    public void intersectionPlayer() {}
    public void check_intersectionBullet(Sprite bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                intersection();
            }
        }
    }

    public void empireStart() {}
    public void buff() {}
    public void stopBuff() {}

    public void sB() {
        if (buff && !lock) {
            stopBuff();
        }
        buff = false;
    }

    public void createLargeExplosion() {
        AudioHub.playBoom();
        for (int i = Game.numberSmallExplosionsTriple; i < Game.numberMediumExplosionsDefault; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(centerX(), centerY());
                break;
            }
        }
    }

    public void createSmallExplosion() {
        for (int i = Game.numberMediumExplosionsDefault; i < Game.numberSmallExplosionsDefault; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(centerX(), centerY());
                break;
            }
        }
    }

    public void createLargeTripleExplosion() {
        AudioHub.playBoom();
        for (int i = 0; i < Game.numberMediumExplosionsTriple; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(centerX(), centerY());
                break;
            }
        }
    }

    public void createSmallTripleExplosion() {
        for (int i = Game.numberMediumExplosionsTriple; i < Game.numberSmallExplosionsTriple; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(centerX(), centerY());
                break;
            }
        }
    }

    public void createSkullExplosion() {
        AudioHub.playMegaBoom();
        for (int i = Game.numberSmallExplosionsDefault; i < Game.numberExplosionsALL; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(centerX(), centerY());
                break;
            }
        }
    }

    public int centerX() {
        return x + halfWidth;
    }
    public int centerY() {
        return y + halfHeight;
    }
    public int right() {
        return x + width;
    }
    public int bottom() {
        return y + height;
    }

    public void recreateRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean intersect(Sprite sprite) {
        getRect();
        sprite = sprite.getRect();

        if (left <= sprite.right) {
            if (sprite.left <= right) {
                if (top <= sprite.bottom) {
                    return sprite.top <= bottom;
                }
            }
        }
        return false;
    }

    public Sprite newRect(int newLeft, int newTop) {
        right += newLeft - left;
        bottom += newTop - top;
        left = newLeft;
        top = newTop;
        return this;
    }

    public Sprite getRect() {
        right += x - left;
        bottom += y - top;
        left = x;
        top = y;
        return this;
    }

    public Object[] getBox(int a, int b, Bitmap bitmap) {
        return new Object[30];
    }
}
