package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.HEALTH_KIT_SPEED;

public class HealthKit extends Sprite {
    public HealthKit(Game g) {
        super(g, ImageHub.healthKitImg.getWidth(), ImageHub.healthKitImg.getHeight());
        speedY = HEALTH_KIT_SPEED;
        isBullet = true;

        hide();
    }

    public void hide() {
        x = MATH.randInt(0, screenWidthWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void intersectionPlayer() {
        hide();
        AudioHub.playHealSnd();
        game.player.heal();
    }

    @Override
    public void update() {
        y += speedY;

        if (y > Game.screenHeight) {
            hide();
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.healthKitImg, x, y, null);
    }
}
