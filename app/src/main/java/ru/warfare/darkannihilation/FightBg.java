package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class FightBg extends Sprite {
    private Bitmap img;

    public FightBg() {
        super(ImageHub.playerVsBoss.getWidth(), ImageHub.playerVsBoss.getHeight());

        new Thread(() -> {
            y = Game.halfScreenHeight;

            int min = 10;
            while (bottom() - Game.halfScreenHeight > Game.screenHeight) {
                height = (int) ((Game.screenWidth-150-min) * Game.resizeK);
                min += 10;
            }

            width = (int) (height * 1.05);
            halfHeight = height / 2;
            halfWidth = width / 2;

            x = Game.halfScreenWidth - halfWidth;
            y = Game.halfScreenHeight - halfHeight;
        }).start();
    }

    public void newImg(String character) {
        switch (character)
        {
            case "saturn":
                img = Bitmap.createScaledBitmap(ImageHub.saturnVsBoss, width, height, true);
                break;
            case "falcon":
                img = Bitmap.createScaledBitmap(ImageHub.playerVsBoss, width, height, true);
                break;
            case "saturn-vaders":
                img = Bitmap.createScaledBitmap(ImageHub.saturnVsVaders, width, height, true);
                break;
            case "falcon-vaders":
                img = Bitmap.createScaledBitmap(ImageHub.playerVsVaders, width, height, true);
                break;
            case "emerald":
                img = Bitmap.createScaledBitmap(ImageHub.emeraldVsBoss, width, height, true);
                break;
            case "emerald-vaders":
                img = Bitmap.createScaledBitmap(ImageHub.emeraldVsVaders, width, height, true);
                break;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(img, x, y, null);
    }
}
