package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class Heart {
    public Bitmap image;
    public int x;
    public int y;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    private final Game game;
    public boolean lock = false;

    public Heart(Game g, int X, int Y, String type) {
        game = g;
        if (type.equals("full")) {
            image = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.full_heart);
            image = Bitmap.createScaledBitmap(image, 70, 60, isFilter);
        } else {
            if (type.equals("half")) {
                image = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.half_heart);
                image = Bitmap.createScaledBitmap(image, 70, 60, isFilter);
            } else {
                if (type.equals("non")) {
                    image = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.non_heart);
                    image = Bitmap.createScaledBitmap(image, 70, 60, isFilter);
                }
            }
        }
        width = image.getWidth();
        height = image.getHeight();
        x = X;
        y = Y;
    }

    public void change(String type) {
        if (type.equals("full")) {
            image = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.full_heart);
            image = Bitmap.createScaledBitmap(image, 70, 60, isFilter);
        } else {
            if (type.equals("half")) {
                image = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.half_heart);
                image = Bitmap.createScaledBitmap(image, 70, 60, isFilter);
            } else {
                if (type.equals("non")) {
                    image = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.non_heart);
                    image = Bitmap.createScaledBitmap(image, 70, 60, isFilter);
                } else {
                    lock = true;
                }
            }
        }
    }

    public void update() {
        if (!lock) {
            game.canvas.drawBitmap(image, x, y, color);
        }
    }
}
