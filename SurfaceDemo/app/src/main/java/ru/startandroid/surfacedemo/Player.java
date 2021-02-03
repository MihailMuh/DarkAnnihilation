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
<<<<<<< HEAD
    private final boolean isFilter = true;
    private final Paint color = new Paint();
    private int screenWidth;
    private int screenHeight;
    float deltaX;
    float deltaY;
    float speedX;
    float speedY;
=======
    private boolean isFilter = true;
    private final Paint color = new Paint();
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6

//    Rectangle imgRect = new Rectangle(263, 146, img.getWidth(), img.getHeight());
//    Rectangle img7Rect = new Rectangle(x+ player.getmapX() + 500, y + player.getmapY() + 500, 40, 40);
//
//        ()
//            if(imgRect.intersects(img7Rect)) {
//    }

    public Player(Context context) {
        player_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
<<<<<<< HEAD
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
=======
        player_image = player_image.createScaledBitmap(player_image, 100, 120, isFilter);
        x = 500;
        y = 500;
        endX = 500;
        endY = 500;
        width = player_image.getWidth();
        height = player_image.getHeight();
    }
    public void get_info() {
        Log.i("player", "X = " + x + " Y = " + y);
//        Log.i("player", "width = " + width + " height = " + height);
    }
    public void update(Canvas canvas) {
        float deltaX = endX - x;
        float deltaY = endY - y;
        float speedX;
        float speedY;
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
        speedX = deltaX / 10;
        speedY = deltaY / 10;
        x += speedX;
        y += speedY;
<<<<<<< HEAD
//        if (x < 0 | x > screenWidth + width) {
//            x -= speedX;
//        } else {
//            if (y > screenHeight | y < 0) {
//                y -= speedY;
//            }
//        }
=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
        canvas.drawBitmap(player_image, x - width / 2, y - height / 2, color);
    }
}
