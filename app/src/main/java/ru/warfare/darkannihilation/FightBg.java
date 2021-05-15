package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class FightBg extends Sprite {
    private Bitmap img;

    public FightBg(Game g) {
        super(g, ImageHub.playerVsBoss.getWidth(), ImageHub.playerVsBoss.getHeight());

        x = 0;
        y = Game.halfScreenHeight;
        int min = 10;

        while ((y - Game.halfScreenHeight) + height > Game.screenHeight) {
            img = Bitmap.createScaledBitmap(ImageHub.playerVsBoss, (int) ((Game.screenWidth-min) * Game.resizeK),
                    (int) ((Game.screenWidth-150-min) * Game.resizeK), ImageHub.isFilter);
            height = img.getHeight();
            min += 10;
        }
        width = img.getWidth();
        height = img.getHeight();
        halfHeight = height / 2;
        halfWidth = width / 2;

        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
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
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(img, x - halfHeight, y - halfHeight, null);
    }
}
