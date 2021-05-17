package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Paint;

public class BaseScreen extends Sprite {
    public int frame = -1;
    public int screenImageLength;

    public BaseScreen(int w, int h, int len) {
        super(w, h);

        x = (int) (Game.screenWidth * -0.2);

        screenImageLength = len;

        switch (Game.level)
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
        switch (Game.level)
        {
            case 1:
                Game.canvas.drawBitmap(ImageHub.screenImage[frame], x, 0, null);
                break;
            case 2:
                Game.canvas.drawBitmap(ImageHub.thunderScreen[frame], x, 0, null);
                break;
        }
    }

    public void render(Bitmap img) {
        Game.canvas.drawBitmap(img, 0, 0, null);
    }
}
