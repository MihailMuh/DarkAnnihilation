package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class FightBg extends Sprite {
    private Bitmap img;

    public FightBg(Game g) {
        super(g, ImageHub.fightBgImg.getWidth(), ImageHub.fightBgImg.getHeight());

        x = 0;
        y = halfScreenHeight;
        int min = 10;

        while ((y - halfScreenHeight) + height > screenHeight) {
            img = Bitmap.createScaledBitmap(ImageHub.fightBgImg, (int) ((screenWidth-min) * game.resizeK),
                    (int) ((screenWidth-150-min) * game.resizeK), ImageHub.isFilter);
            height = img.getHeight();
            min += 10;
        }
        width = img.getWidth();
        height = img.getHeight();
        halfHeight = height / 2;
        halfWidth = width / 2;

        x = halfScreenWidth;
        y = halfScreenHeight;
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(img, x - halfHeight, y - halfHeight, null);
    }
}
