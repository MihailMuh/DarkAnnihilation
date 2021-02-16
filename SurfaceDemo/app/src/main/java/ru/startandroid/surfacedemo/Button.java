package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.LinkedList;

public class Button {
    private Context context;
    private static Bitmap buttonImagePressed;
    private static Bitmap buttonImageNotPressed;
    private Bitmap img;
    public float x;
    public float y;
    public float mouseX;
    public float mouseY;
    public float width;
    public float height;
    private static final boolean isFilter = true;
    public static final Paint paint = new Paint();
    public Game game;
    public String function;

    private Rect bounds = new Rect();
    private String text;
    private int textWidth;
    private int textHeight;

    public Button(Game g, String t, float X, float Y, String func) {
        game = g;
        context = game.context;

        buttonImagePressed = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_press);
        buttonImagePressed = Bitmap.createScaledBitmap(buttonImagePressed, 300, 70, isFilter);
        buttonImageNotPressed = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_notpress);
        buttonImageNotPressed = Bitmap.createScaledBitmap(buttonImageNotPressed, 300, 70, isFilter);
        img = buttonImageNotPressed;
        width = buttonImagePressed.getWidth();
        height = buttonImagePressed.getHeight();
        function = func;

        x = X;
        y = Y;

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        paint.getTextBounds(text, 0, text.length(), bounds);
        textWidth = bounds.width();
        textHeight = bounds.height();
    }

    public void update() {
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + height) {
            img = buttonImagePressed;
            if (function.equals("start")) {
//            game.ai.playerImage.recycle();
                game.ai = null;
                game.player = new Player(game);
//            for (int i = 0; i < game.vaderNumbers; i++) {
//                game.vaders[i].vaderImage.recycle();
//                game.vaders[i] = null;
//            }
                game.vaders = new Vader[12];
                game.vaderNumbers = 12;
                for (int i = 0; i < 12; i++) {
                    game.vaders[i] = new Vader(game);
                }
//            for (int i = 0; i < game.bullets.size(); i++) {
//                game.bullets.get(i).bulletImage.recycle();
//                game.bullets.remove(i);
//            }
                game.bullets = new LinkedList<>();
//            game.button.img.recycle();
                game.buttonStart = null;

                game.preview = 0;
            } else {
                if (function.equals("quit")) {
                    System.exit(0);
                }
            }
        } else {
            img = buttonImageNotPressed;
        }

        game.canvas.drawBitmap(img, x, y, paint);
        game.canvas.drawText(text, x + ((width - textWidth) / 2),y + (height - textHeight), paint);
    }
}
