package ru.warfare.darkannihilation;

import android.util.Log;

public class HealthKit extends Sprite {
    public HealthKit(Game g) {
        super(g, ImageHub.healthKitImg.getWidth(), ImageHub.healthKitImg.getHeight());
        speedY = 2;
        lock = true;

        x = randInt(0, screenWidth);
        y = -height;
    }

    public void stop() {
        x = randInt(0, screenWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void check_intersectionPlayer() {
        if (x + 5 < game.player.x + 10 & game.player.x + 10 < x + width - 5 &
                y + 5 < game.player.y + 10 & game.player.y + 10 < y + height - 5 |
                game.player.x + 10 < x + 5 & x + 5 < game.player.x + game.player.width - 10 &
                        game.player.y + 10 < y + 5 & y + 5 < game.player.y + game.player.height - 10) {
            stop();
            AudioPlayer.healSnd.start();
            game.player.health = 50;
        }
    }

    @Override
    public void update() {
        if (!lock) {
            y += speedY;
            check_intersectionPlayer();
            if (y > screenHeight) {
                stop();
            }
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.healthKitImg, x, y, null);
    }
}
