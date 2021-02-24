package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

public class PauseButton {
    public int x;
    public int y;
    public int mouseX;
    public int mouseY;
    public int width;
    public int height;
    public static final Paint paint = new Paint();
    public final Game game;
    public boolean lock = true;
    public int oldStatus;

    public PauseButton(Game g) {
        game = g;

        width = ImageHub.pauseButtonImg.getWidth();
        height = width;

        y = 20;
        x = game.screenWidth - width * 2;
    }

    public void setCoords(int X, int Y) {
        mouseX = X;
        mouseY = Y;
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + height & game.gameStatus != 4) {
            game.player.dontmove = true;
            game.audioPlayer.buttonSnd.start();
            if (game.gameStatus == 2) {
                game.audioPlayer.readySnd.pause();
            }
            mouseX = 0;
            mouseY = 0;
            game.buttonStart.newFunc("Resume", game.screenWidth / 2 - game.buttonQuit.width / 2,
                    game.screenHeight / 3 - game.buttonStart.height / 2, "pause");
            game.buttonQuit.newFunc("Quit", game.screenWidth / 2 - game.buttonQuit.width / 2,
                    game.buttonStart.height + game.buttonStart.y + 10, "quit");
            game.makeScreenshot();
            oldStatus = game.gameStatus;
            game.gameStatus = 4;
        } else {
            if (game.gameStatus != 4) {
                game.player.dontmove = false;
            }
        }
    }

    public void update() {
        if (!lock) {
            game.canvas.drawBitmap(ImageHub.pauseButtonImg, x, y, paint);
        }
    }
}
