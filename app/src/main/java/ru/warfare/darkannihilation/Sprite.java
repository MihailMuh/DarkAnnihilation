package ru.warfare.darkannihilation;

public class Sprite {
    public int x = 0;
    public int y = 0;
    public int speedX = 0;
    public int speedY = 0;
    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;
    public final Game game;
    public boolean lock = false;
    public int health = 0;
    public int screenWidth;
    public int screenHeight;
    public int halfScreenWidth;
    public int halfScreenHeight;
    public double resizeK;
    public int numberSmallExplosions;
    public int numberLargeExplosions;
    public int numberDefaultExplosions;

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
        resizeK = game.resizeK;
        numberDefaultExplosions = game.numberDefaultExplosions;
        numberLargeExplosions = game.numberExplosionsAll;
        numberSmallExplosions = game.numberSmallExplosions;
    }

    public void check_intersectionBullet(Bullet bullet) {}
    public void check_intersectionBullet(BulletEnemy bulletEnemy) {}
    public void update() {}
    public void render() {}

    public static int randInt(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
