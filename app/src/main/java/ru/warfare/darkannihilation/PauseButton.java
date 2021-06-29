package ru.warfare.darkannihilation;

import android.graphics.Paint;

public class PauseButton extends Sprite {
    public static int oldStatus;
    public static final Paint alphaPaint = new Paint();
    private boolean isInvisible;
    private final int myX;

    public PauseButton(Game g) {
        super(g, ImageHub.pauseButtonImg.getWidth(), ImageHub.pauseButtonImg.getHeight());
        y = 20;
        myX = (int) (Game.screenWidth - (width * 1.5));

        alphaPaint.setFilterBitmap(true);
        alphaPaint.setDither(true);
        alphaPaint.setAntiAlias(true);

        show();
    }

    @Override
    public void intersectionPlayer() {
        if (!isInvisible) {
            isInvisible = true;
            alphaPaint.setAlpha(80);
        }
    }

    public void work() {
        if (isInvisible) {
            isInvisible = false;
            alphaPaint.setAlpha(255);
        }
    }
    public void show() {
        isInvisible = false;
        x = myX;
    }

    public void make() {
        AudioHub.playClick();
        oldStatus = Game.gameStatus;
        game.generatePause();
    }

    public boolean checkCoords(int X, int Y) {
        if (!isInvisible) {
            if (x < X) {
                if (X < right()) {
                    if (y < Y) {
                        return Y < bottom();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.pauseButtonImg, x, y, alphaPaint);
    }
}
