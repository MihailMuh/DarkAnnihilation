package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Vader {
    private Bitmap vader_img;
    public float x;
    public float y;
    public float speedX;
    public float speedY;
    public float width;
    public float height;
<<<<<<< HEAD
    private int screenWidth;
    private int screenHeight;
=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    private boolean isFilter = true;
    private final Paint color = new Paint();

//    Rectangle imgRect = new Rectangle(263, 146, img.getWidth(), img.getHeight());
//    Rectangle img7Rect = new Rectangle(x+ player.getmapX() + 500, y + player.getmapY() + 500, 40, 40);
//
//        ()
//            if(imgRect.intersects(img7Rect)) {
//    }

    public Vader(Context context) {
        vader_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.vader3);
        vader_img = Bitmap.createScaledBitmap(vader_img, 75, 75, isFilter);
        x = get_random(0, 1920);
        y = -50;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
        width = vader_img.getWidth();
        height = vader_img.getHeight();
    }
<<<<<<< HEAD

    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

=======
    public void get_info() {
        Log.i("player", "width = " + width + " height = " + height);
    }
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    public float get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
<<<<<<< HEAD

=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    public void newStatus() {
        x = get_random(0, 1920);
        y = -50;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
    }
<<<<<<< HEAD

=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    public void update(Canvas canvas) {
        x += speedX;
        y += speedY;
        canvas.drawBitmap(vader_img, x - width / 2, y - height / 2, color);
<<<<<<< HEAD
        if (x < -50 | x > screenWidth + 150 | y > screenHeight + 50) {
=======
        if (x < -50 | x > 2000 | y > 1600) {
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
            newStatus();
        }
    }
}
