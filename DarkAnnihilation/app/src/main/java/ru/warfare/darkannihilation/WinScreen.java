package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class WinScreen extends Sprite {
    private int frame = 0;
    private static final int screenImageLength = ImageHub.winScreenImg.length;
    private static final int frameTime = 45;
    private long lastFrame;
    private long now;
    private final Paint paint = new Paint();

    public WinScreen(Game g) {
        super(g, 0, 0);

        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        lastFrame = System.currentTimeMillis();
    }

    @Override
    public void update() {
        now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            if (frame < screenImageLength - 1) {
                frame += 1;
            }
            if (frame == 14) {
                AudioPlayer.flightSnd.seekTo(0);
                AudioPlayer.flightSnd.start();
            }
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.winScreenImg[frame], x, y, null);
        if (frame == screenImageLength - 1) {
            game.canvas.drawText("Thanks For Playing!",
                    (screenWidth - paint.measureText("Thanks For Playing!")) / 2,
                    (float) ((screenHeight + paint.getTextSize()) / 2.7), paint);
            game.canvas.drawText("Tap this screen with four or more fingers to enter start menu",
                    (screenWidth - Game.gameoverPaint.measureText("Tap this screen with four or more fingers to enter start menu")) / 2,
                    (float) (screenHeight * 0.65), Game.gameoverPaint);
        }
    }
}
