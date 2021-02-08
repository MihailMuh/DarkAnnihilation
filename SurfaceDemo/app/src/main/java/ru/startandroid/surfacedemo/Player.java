package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Player {
    private Bitmap player_image;
    public float x;
    public float y;
    public float endX;
    public float endY;
    public float width;
    public float height;
    private final boolean isFilter = true;
<<<<<<< HEAD
    public final Paint paint = new Paint();
=======
    private final Paint color = new Paint();
>>>>>>> acdb79ad105e33de70e1626dd116379ba67bf263
    private int screenWidth;
    private int screenHeight;
    float deltaX;
    float deltaY;
    float speedX;
    float speedY;

//    Rectangle imgRect = new Rectangle(263, 146, img.getWidth(), img.getHeight());
//    Rectangle img7Rect = new Rectangle(x+ player.getmapX() + 500, y + player.getmapY() + 500, 40, 40);
//
//        ()
//            if(imgRect.intersects(img7Rect)) {
//    }

    public Player(Context context) {
        player_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        player_image = Bitmap.createScaledBitmap(player_image, 100, 120, isFilter);
        width = player_image.getWidth();
        height = player_image.getHeight();
    }

    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;
        endX = x;
        endY = y;
    }

    public void update(Canvas canvas) {
        deltaX = endX - x;
        deltaY = endY - y;
<<<<<<< HEAD
        speedX = deltaX / 5;
        speedY = deltaY / 5;
        x += speedX;
        y += speedY;
        canvas.drawRect(x, y, x + width, y + height, paint);
        canvas.drawBitmap(player_image, x, y, paint);
=======
        speedX = deltaX / 10;
        speedY = deltaY / 10;
        x += speedX;
        y += speedY;
//        if (x < 0 | x > screenWidth + width) {
//            x -= speedX;
//        } else {
//            if (y > screenHeight | y < 0) {
//                y -= speedY;
//            }
//        }
        canvas.drawBitmap(player_image, x - width / 2, y - height / 2, color);
>>>>>>> acdb79ad105e33de70e1626dd116379ba67bf263
    }
}
