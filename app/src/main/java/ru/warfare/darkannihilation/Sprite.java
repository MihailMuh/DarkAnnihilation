package ru.warfare.darkannihilation;

public class Sprite {
    public final Game game;

    public int x = 0;
    public int y = 0;
    public int speedX = 0;
    public int speedY = 0;

    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;

    public int health = 0;
    public int damage = 0;

    public int screenWidth;
    public int screenHeight;
    public int halfScreenWidth;
    public int halfScreenHeight;
    public int numberSmallExplosionsTriple;
    public int numberLargeExplosions;
    public int numberMediumExplosionsTriple;
    public int numberSmallExplosionsDefault;
    public int numberMediumExplosionsDefault;

    public boolean lock = false;
    public boolean isPassive = false;
    public boolean isBullet = false;

    public int left;
    public int top;
    public int right;
    public int bottom;

    public Sprite(Game g, int w, int h) {
        game = g;

        width = w;
        height = h;
        halfWidth = width / 2;
        halfHeight = height / 2;

        screenHeight = game.screenHeight;
        screenWidth = game.screenWidth;
        halfScreenWidth = game.halfScreenWidth;
        halfScreenHeight = game.halfScreenHeight;

        numberMediumExplosionsTriple = game.numberMediumExplosionsTriple;
        numberSmallExplosionsTriple = game.numberSmallExplosionsTriple;
        numberLargeExplosions = game.numberExplosionsALL;
        numberSmallExplosionsDefault = game.numberSmallExplosionsDefault;
        numberMediumExplosionsDefault = game.numberMediumExplosionsDefault;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;
    }

    public void check_intersectionBullet(BaseBullet bullet) {}
    public void update() {}
    public void render() {}
    public void intersection() {}
    public void intersectionPlayer() {}
    public void empireStart() {}

    public void recreateRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean intersect(Sprite sprite) {
        return left < sprite.right && sprite.left < right && top < sprite.bottom && sprite.top < bottom;
    }

    public Sprite goTO(int newLeft, int newTop) {
        right += newLeft - left;
        bottom += newTop - top;
        left = newLeft;
        top = newTop;
        return this;
    }

    public Sprite goTO() {
        right += x - left;
        bottom += y - top;
        left = x;
        top = y;
        return this;
    }

    public Sprite getRect() {
        return goTO();
    }

    public static int randInt(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
