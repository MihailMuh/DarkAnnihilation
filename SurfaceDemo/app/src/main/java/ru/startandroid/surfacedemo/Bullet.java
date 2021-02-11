package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Bullet extends Sprite{
    private Bitmap bulletImage;
    public float x;
    public float y;
    public float width;
    public float height;
    private final boolean isFilter = true;
    public final Paint color = new Paint();
    private int screenWidth;
    private int screenHeight;
    private final float speed = 10;
    public int start = 0;

    public Bullet(Context context, Player player) {
        bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        bulletImage = Bitmap.createScaledBitmap(bulletImage, 7, 30, isFilter);
        width = bulletImage.getWidth();
        height = bulletImage.getHeight();
        x = player.x + player.width / 2;
        y = player.y;
    }

    @Override
    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void update(Canvas canvas, Player player) {
        if (start == 1) {
            y -= speed;
            if (y < -150) {
                x = player.x + player.width / 2;
                y = player.y;
            }
//        canvas.drawRect(x, y, x + width, y + height, color);
        }
        canvas.drawBitmap(bulletImage, x, y, color);
    }
}
