package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.LinkedList;

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

    private Rect bounds = new Rect();
    private String text;
    private int textWidth;
    private int textHeight;

    public MediaPlayer mediaPlayer;


    public Button(Game g, String t, int X, int Y, String func) {
        game = g;

        buttonImagePressed = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.button_press);
        buttonImagePressed = Bitmap.createScaledBitmap(buttonImagePressed, 300, 70, isFilter);
        buttonImageNotPressed = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.button_notpress);
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

        mediaPlayer = MediaPlayer.create(game.context.getApplicationContext(), R.raw.spacebar);
    }

    public void update() {
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + height) {
            mediaPlayer.start();
            img = buttonImagePressed;
            if (function.equals("start")) {
                game.ai = null;
                game.player = new Player(game);
                game.player.lock = true;
                game.vaders = new Vader[12];
                game.vaderNumbers = 12;
                for (int i = 0; i < 12; i++) {
                    game.vaders[i] = new Vader(game);
                    game.vaders[i].lock = true;
                }
                game.bullets = new ArrayList<>(0);
                game.numberBullets = game.bullets.size();
                game.buttonStart = null;
                game.mediaPlayer.stop();
                game.mediaPlayer = MediaPlayer.create(game.context.getApplicationContext(), R.raw.pirate);
                game.mediaPlayer.setLooping(true);
                game.mediaPlayer.start();
                game.preview = 2;
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
