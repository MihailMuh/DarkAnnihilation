package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class FightBg extends Sprite {
    private Bitmap img;

    public FightBg() {
        super(ImageHub.playerVsBoss.getWidth(), ImageHub.playerVsBoss.getHeight());

        y = Game.halfScreenHeight;
        int min = 10;

        while (bottom() - Game.halfScreenHeight > Game.screenHeight) {
            height = (int) ((Game.screenWidth-150-min) * Game.resizeK);
            min += 10;
        }
        img = Bitmap.createScaledBitmap(ImageHub.playerVsBoss, (int) ((Game.screenWidth-min) * Game.resizeK),
                height, ImageHub.isFilter);

        width = img.getWidth();
        halfHeight = height / 2;
        halfWidth = width / 2;

        x = Game.halfScreenWidth - halfWidth;
        y = Game.halfScreenHeight - halfHeight;
    }

    public void newImg(String character) {
        switch (character)
        {
            case "saturn":
                img = Bitmap.createScaledBitmap(ImageHub.saturnVsBoss, width, height, ImageHub.isFilter);
                break;
            case "falcon":
                img = Bitmap.createScaledBitmap(ImageHub.playerVsBoss, width, height, ImageHub.isFilter);
                break;
            case "saturn-vaders":
                img = Bitmap.createScaledBitmap(ImageHub.saturnVsVaders, width, height, ImageHub.isFilter);
                break;
            case "falcon-vaders":
                img = Bitmap.createScaledBitmap(ImageHub.playerVsVaders, width, height, ImageHub.isFilter);
                break;
            case "emerald":
                img = Bitmap.createScaledBitmap(ImageHub.emeraldVsBoss, width, height, ImageHub.isFilter);
                break;
            case "emerald-vaders":
                img = Bitmap.createScaledBitmap(ImageHub.emeraldVsVaders, width, height, ImageHub.isFilter);
                break;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(img, x, y, null);
    }
}
