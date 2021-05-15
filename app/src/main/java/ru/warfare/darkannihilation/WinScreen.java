package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;

public class WinScreen extends Sprite {
    private int frame = 0;
    private static final int screenImageLength = ImageHub.winScreenImg.length;
    private static final int frameTime = 45;
    private long lastFrame;
    private final Paint paint = new Paint();

    public WinScreen() {
        super(0, 0);

        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        lastFrame = System.currentTimeMillis();
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            if (frame < screenImageLength - 1) {
                frame += 1;
            }
            if (frame == 14) {
                AudioPlayer.restartFlightMusic();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.winScreenImg[frame], 0, 0, null);
        if (frame == screenImageLength - 1) {
            Game.canvas.drawText("Thanks For Playing!",
                    (Game.screenWidth - paint.measureText("Thanks For Playing!")) / 2,
                    (float) ((Game.screenHeight + paint.getTextSize()) / 2.7), paint);
            Game.canvas.drawText("Tap this screen with four or more fingers to enter start menu",
                    (Game.screenWidth - Game.gameoverPaint.measureText("Tap this screen with four or more fingers to enter start menu")) / 2,
                    (float) (Game.screenHeight * 0.65), Game.gameoverPaint);
        }
    }
}
