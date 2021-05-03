package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class BaseScreen extends Sprite {
    public int frame = -1;
    public int screenImageLength;

    public BaseScreen(Game g, int w, int h, int len) {
        super(g, w, h);

        x = (int) (game.screenWidth * -0.2);

        screenImageLength = len;

        switch (game.level)
        {
            case 1:
                screenImageLength = ImageHub.screenImage.length;
                break;
            case 2:
                screenImageLength = ImageHub.thunderScreen.length;
                break;
        }
    }

    @Override
    public void render() {
        switch (game.level)
        {
            case 1:
                game.canvas.drawBitmap(ImageHub.screenImage[frame], x, y, null);
                break;
            case 2:
                game.canvas.drawBitmap(ImageHub.thunderScreen[frame], x, y, null);
                break;
        }
    }

    public void render(Bitmap img) {
        game.canvas.drawBitmap(img, 0, 0, null);
    }
}
