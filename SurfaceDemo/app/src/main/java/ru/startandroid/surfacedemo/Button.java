package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Button {
    private static Bitmap buttonImagePressed;
    private static Bitmap buttonImageNotPressed;
    private Bitmap img;
    public int x;
    public int y;
    public int mouseX;
    public int mouseY;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    public static final Paint paint = new Paint();
    public final Game game;
    public String function;

    private String text;
    private int textWidth;
    private int textHeight;

    public Button(Game g, String t, int X, int Y, String func) {
        game = g;

        buttonImagePressed = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.button_press);
        buttonImagePressed = Bitmap.createScaledBitmap(buttonImagePressed, (int) (300 * game.resizeK), (int) (70 * game.resizeK), isFilter);
        buttonImageNotPressed = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.button_notpress);
        buttonImageNotPressed = Bitmap.createScaledBitmap(buttonImageNotPressed, (int) (300 * game.resizeK), (int) (70 * game.resizeK), isFilter);
        img = buttonImageNotPressed;
        width = buttonImagePressed.getWidth();
        height = buttonImagePressed.getHeight();
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
            game.audioPlayer.buttonSnd.start();
            img = buttonImagePressed;
            if (function.equals("start")) {
                game.generateNewGame();
            } else {
                if (function.equals("quit")) {
                    System.exit(0);
                }
            }
        } else {
            img = buttonImageNotPressed;
        }

        game.canvas.drawBitmap(img, x, y, paint);
        game.canvas.drawText(text, x + (width - textWidth) / 2,y + (height / 2 + textHeight), paint);
    }
}
