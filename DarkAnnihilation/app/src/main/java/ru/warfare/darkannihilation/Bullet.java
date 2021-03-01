package ru.warfare.darkannihilation;

public class Bullet {
    public int x;
    public int y;
    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;
    public static final int speed = 10;
    private final Game game;
    public int damage = 1;

    public Bullet(Game g, int X, int Y) {
        game = g;
//        color.setColor(Color.WHITE);

        width = ImageHub.bulletImage.getWidth();
        height = ImageHub.bulletImage.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        x = X;
        y = Y;
        AudioPlayer.playShoot();
    }

    public void update() {
        y -= speed;
        game.canvas.drawBitmap(ImageHub.bulletImage, x, y, null);
//        game.canvas.drawRect(x, y, x + width, y + height, color);
    }
}
