package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
    private static int screenWidth;
    private static int screenHeight;
    public int start = 0;

    private Rect bounds = new Rect();
    private String text;
    private int textWidth;
    private int textHeight;

    public Button(Context c, String t, float X, float Y, int w, int h) {
        context = c;
        buttonImagePressed = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_press);
        buttonImagePressed = Bitmap.createScaledBitmap(buttonImagePressed, w, h, isFilter);
        buttonImageNotPressed = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_notpress);
        buttonImageNotPressed = Bitmap.createScaledBitmap(buttonImageNotPressed, w, h, isFilter);
        img = buttonImageNotPressed;
        width = buttonImagePressed.getWidth();
        height = buttonImagePressed.getHeight();

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        paint.getTextBounds(text, 0, text.length(), bounds);
        textWidth = bounds.width();
        textHeight = bounds.height();
    }

    public Button(Context c, String t, float X, float Y) {
        context = c;
        buttonImagePressed = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_press);
        buttonImagePressed = Bitmap.createScaledBitmap(buttonImagePressed, 300, 70, isFilter);
        buttonImageNotPressed = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_notpress);
        buttonImageNotPressed = Bitmap.createScaledBitmap(buttonImageNotPressed, 300, 70, isFilter);
        img = buttonImageNotPressed;
        width = buttonImagePressed.getWidth();
        height = buttonImagePressed.getHeight();

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        paint.getTextBounds(text, 0, text.length(), bounds);
        textWidth = bounds.width();
        textHeight = bounds.height();
    }

    public void update(Canvas canvas) {
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + height) {
            img = buttonImagePressed;
            start = 1;
        } else {
            img = buttonImageNotPressed;
        }

        canvas.drawBitmap(img, x, y, paint);
        canvas.drawText(text, x + ((width - textWidth) / 2),y + (height - textHeight), paint);
    }
}
