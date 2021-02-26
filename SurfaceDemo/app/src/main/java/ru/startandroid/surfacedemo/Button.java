package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

public class Button {
    private Bitmap img;
    public int x;
    public int y;
    public int mouseX;
    public int mouseY;
    public int width;
    public int height;
    public static final Paint paint = new Paint();
    public final Game game;
    public String function;

    private String text;
    private int textWidth;
    private int textHeight;

    public Button(Game g, String t, int X, int Y, String func) {
        game = g;

        img = ImageHub.buttonImageNotPressed;
        width = ImageHub.buttonImagePressed.getWidth();
        height = ImageHub.buttonImagePressed.getHeight();
        function = func;

        x = X;
        y = Y;

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        textWidth = (int) paint.measureText(text);
        textHeight = (int) (paint.getTextSize() / 4);
    }

    public void newFunc(String t, int X, int Y, String func) {
        function = func;

        x = X;
        y = Y;

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        textWidth = (int) paint.measureText(text);
        textHeight = (int) (paint.getTextSize() / 4);
    }

    public void update() {
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + height) {
            AudioPlayer.buttonSnd.start();
            img = ImageHub.buttonImagePressed;
            if (function.equals("start")) {
                game.generateNewGame();
            } else {
                if (function.equals("quit")) {
                    System.exit(0);
                } else {
                    if (function.equals("pause")) {
                        AudioPlayer.pauseMusic.pause();
                        AudioPlayer.pirateMusic.start();
                        if (game.pauseButton.oldStatus == 2) {
                            AudioPlayer.readySnd.start();
                        }
                        game.gameStatus = game.pauseButton.oldStatus;
                    } else {
                        if (function.equals("menu")) {
                            game.generateMenu();
                        }
                    }
                }
            }
        } else {
            img = ImageHub.buttonImageNotPressed;
//            game.player.dontmove = true;
        }

        game.canvas.drawBitmap(img, x, y, paint);
        game.canvas.drawText(text, x + (width - textWidth) / 2,y + (height / 2 + textHeight), paint);
    }
}
