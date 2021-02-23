package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHub {

    public static final Bitmap[] explosionImageSmall = new Bitmap[28];
    public static final Bitmap[] explosionImageDefault = new Bitmap[28];
    public static final Bitmap[] explosionImageLarge = new Bitmap[28];

    public ImageHub(Game game) {
        Context context = game.context;

        explosionImageSmall[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_1);
        explosionImageSmall[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_2);
        explosionImageSmall[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_3);
        explosionImageSmall[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_4);
        explosionImageSmall[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_5);
        explosionImageSmall[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_6);
        explosionImageSmall[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_7);
        explosionImageSmall[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_8);
        explosionImageSmall[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_9);
        explosionImageSmall[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_10);
        explosionImageSmall[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_11);
        explosionImageSmall[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_12);
        explosionImageSmall[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_13);
        explosionImageSmall[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_14);
        explosionImageSmall[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_15);
        explosionImageSmall[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_16);
        explosionImageSmall[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_17);
        explosionImageSmall[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_18);
        explosionImageSmall[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_19);
        explosionImageSmall[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_20);
        explosionImageSmall[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_21);
        explosionImageSmall[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_22);
        explosionImageSmall[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_23);
        explosionImageSmall[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_24);
        explosionImageSmall[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_25);
        explosionImageSmall[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_26);
        explosionImageSmall[26] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_27);
        explosionImageSmall[27] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_28);
        for (int i = 0; i < 28; i++) {
            explosionImageSmall[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (50 * game.resizeK), (int) (50 * game.resizeK), true);
        }

        for (int i = 0; i < 28; i++) {
            explosionImageDefault[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (145 * game.resizeK), (int) (145 * game.resizeK), true);
        }

        for (int i = 0; i < 28; i++) {
            explosionImageLarge[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (300 * game.resizeK), (int) (300 * game.resizeK), true);
        }

    }
}
