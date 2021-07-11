package ru.warfare.darkannihilation.button;

import android.graphics.Paint;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

public class PauseButton extends Sprite {
    public static int oldStatus;
    private static final Paint alphaPaint = new Paint();
    private boolean isInvisible;
    private final int myX;

    public PauseButton(Game g) {
        super(g, ImageHub.pauseButtonImg);
        y = 20;
        myX = (int) (Game.screenWidth - (width * 1.5));

        alphaPaint.setFilterBitmap(true);
        alphaPaint.setDither(true);

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
        alphaPaint.setAlpha(255);
        x = myX;
    }

    public void make() {
        oldStatus = Game.gameStatus;
        HardThread.newJob(() -> {
            AudioHub.playClick();
            game.generatePause();
        });
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
        Game.canvas.drawBitmap(image, x, y, alphaPaint);
    }
}
