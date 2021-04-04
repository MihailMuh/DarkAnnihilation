package ru.warfare.darkannihilation;

public class HealthKit extends Sprite {
    public HealthKit(Game g) {
        super(g, ImageHub.healthKitImg.getWidth(), ImageHub.healthKitImg.getHeight());
        speedY = 2;
        lock = true;

        x = randInt(0, screenWidth);
        y = -height;
    }

    public void hide() {
        x = randInt(0, screenWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void update() {
        y += speedY;
        game.player.check_intersectionHealthKit(this);
        if (y > screenHeight) {
            hide();
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.healthKitImg, x, y, null);
    }
}
