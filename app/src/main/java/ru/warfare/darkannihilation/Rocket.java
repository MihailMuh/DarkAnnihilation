package ru.warfare.darkannihilation;

public class Rocket extends Sprite{
    public int damage = 100;

    public Rocket(Game g) {
        super(g, ImageHub.rocketImg.getWidth(), ImageHub.rocketImg.getHeight());
        speedY = 35;
        lock = true;

        y = -height;
    }

    public void hide() {
        lock = true;
        y = -height;
    }

    public void start(int X) {
        x = X - halfWidth;
        lock = false;
    }

    @Override
    public void update() {
        y += speedY;

        game.player.check_intersectionRocket(this);

        if (y > screenHeight) {
            hide();
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.rocketImg, x, y, null);
    }
}
