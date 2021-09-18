package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_LARGE_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_SMALL_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SKULL_EXPLOSIONS;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_LARGE_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_SMALL_EXPLOSION;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public abstract class Sprite {
    public Game game;
    public Bitmap image;

    public int x = 0;
    public int y = 0;
    public int speedX = 0;
    public int speedY = 0;

    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;

    protected int screenHeightHeight;
    protected int screenWidthWidth;

    public int health = 0;
    public int damage = 0;
    public byte name = 0;

    protected boolean buff = false;
    public boolean lock = false;

    public int left;
    public int top;
    public int right;
    public int bottom;

    protected Sprite(Game game) {
        this.game = game;
    }

    protected Sprite(Game g, Bitmap bitmap) {
        game = g;
        image = bitmap;

        makeParams();
    }

    protected void makeParams() {
        width = image.getWidth();
        height = image.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;
    }

    protected void calculateBarriers() {
        screenHeightHeight = SCREEN_HEIGHT - height;
        screenWidthWidth = SCREEN_WIDTH - width;
    }

    public abstract void update();

    public void render() {
        Game.canvas.drawBitmap(image, x, y, null);
    }

    public void turn() {
        render();
        update();
    }

    public void intersectionPlayer() {
    }

    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.kill();
            if (health <= 0) {
                kill();
            }
        }
    }

    public abstract void kill();

    public void hide() {
    }

    public void start() {
        if (buff) {
            onBuff();
        }
    }

    public void empireStart() {
    }

    protected void onStopBuff() {
    }

    protected void onBuff() {
    }

    public void buff() {
        buff = true;
        onBuff();
    }

    public void stopBuff() {
        if (buff) {
            onStopBuff();
            buff = false;
        }
    }

    protected void createLargeExplosion() {
        int X = centerX();
        int Y = centerY();
        HardThread.doInBackGround(() -> {
            AudioHub.playBoom();
            for (int i = 0; i < NUMBER_DEFAULT_LARGE_EXPLOSION; i++) {
                if (game.defaultLargeExplosion[i].lock) {
                    game.defaultLargeExplosion[i].start(X, Y);
                    break;
                }
            }
        });
    }

    protected void createSmallExplosion() {
        for (int i = 0; i < NUMBER_DEFAULT_SMALL_EXPLOSION; i++) {
            if (game.defaultSmallExplosion[i].lock) {
                game.defaultSmallExplosion[i].start(centerX(), centerY());
                break;
            }
        }
    }

    protected void largeTripleExplosion() {
        AudioHub.playBoom();
        for (int i = 0; i < NUMBER_TRIPLE_LARGE_EXPLOSION; i++) {
            if (game.tripleLargeExplosion[i].lock) {
                game.tripleLargeExplosion[i].start(centerX(), centerY());
                break;
            }
        }
    }

    protected void createLargeTripleExplosion() {
        int X = centerX();
        int Y = centerY();
        HardThread.doInBackGround(() -> {
            AudioHub.playBoom();
            for (int i = 0; i < NUMBER_TRIPLE_LARGE_EXPLOSION; i++) {
                if (game.tripleLargeExplosion[i].lock) {
                    game.tripleLargeExplosion[i].start(X, Y);
                    break;
                }
            }
        });
    }

    protected void createSmallTripleExplosion() {
        for (int i = 0; i < NUMBER_TRIPLE_SMALL_EXPLOSION; i++) {
            if (game.tripleSmallExplosion[i].lock) {
                game.tripleSmallExplosion[i].start(centerX(), centerY());
                break;
            }
        }
    }

    protected void createSkullExplosion() {
        AudioHub.playMegaBoom();
        for (int i = 0; i < NUMBER_SKULL_EXPLOSIONS; i++) {
            if (game.skullExplosion[i].lock) {
                game.skullExplosion[i].start(centerX(), centerY());
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
        if (sprite != null) {
            getRect();
            sprite = sprite.getRect();

            if (left <= sprite.right) {
                if (sprite.left <= right) {
                    if (top <= sprite.bottom) {
                        return sprite.top <= bottom;
                    }
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
