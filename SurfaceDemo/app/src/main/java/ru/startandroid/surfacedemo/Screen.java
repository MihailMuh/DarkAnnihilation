package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Screen {
    private Bitmap[] screen_image = new Bitmap[34];
    private final boolean isFilter = true;
    public final Paint color = new Paint();
    public float x;
    public float y;
    private long lastUpdate;
    private static final int frameRate = 25;
    private long now;
    private int frame = 0;
    private int screenImageLength = screen_image.length;
    private Game game;

    public Screen(Game g) {
        game = g;
        Context context = game.context;
        screen_image[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable._0);
        screen_image[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable._1);
        screen_image[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable._2);
        screen_image[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable._3);
        screen_image[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable._4);
        screen_image[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable._5);
        screen_image[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable._6);
        screen_image[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable._7);
        screen_image[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable._8);
        screen_image[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable._9);
        screen_image[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable._10);
        screen_image[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable._11);
        screen_image[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable._12);
        screen_image[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable._13);
        screen_image[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable._14);
        screen_image[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable._15);
        screen_image[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable._16);
        screen_image[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable._17);
        screen_image[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable._18);
        screen_image[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable._19);
        screen_image[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable._20);
        screen_image[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable._21);
        screen_image[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable._22);
        screen_image[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable._23);
        screen_image[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable._24);
        screen_image[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable._25);
        screen_image[26] = BitmapFactory.decodeResource(context.getResources(), R.drawable._26);
        screen_image[27] = BitmapFactory.decodeResource(context.getResources(), R.drawable._27);
        screen_image[28] = BitmapFactory.decodeResource(context.getResources(), R.drawable._28);
        screen_image[29] = BitmapFactory.decodeResource(context.getResources(), R.drawable._29);
        screen_image[30] = BitmapFactory.decodeResource(context.getResources(), R.drawable._30);
        screen_image[31] = BitmapFactory.decodeResource(context.getResources(), R.drawable._31);
        screen_image[32] = BitmapFactory.decodeResource(context.getResources(), R.drawable._32);
        screen_image[33] = BitmapFactory.decodeResource(context.getResources(), R.drawable._33);

        for (int i = 0; i < screenImageLength; i++) {
            screen_image[i] = Bitmap.createScaledBitmap(screen_image[i], (int) ((game.screenWidth + 110) * 1.4), game.screenHeight + 100, isFilter);
        }
        x = (float) (game.screenWidth * -0.2);
        y = 0;

        lastUpdate = System.nanoTime();
    }

    public void update() {
        now = System.nanoTime();
        if (now - lastUpdate > frameRate) {
            lastUpdate = now;
            if (frame == screenImageLength) {
                frame = 0;
            }
            game.canvas.drawBitmap(screen_image[frame], x, y, color);
            frame += 1;
        }
    }
}
