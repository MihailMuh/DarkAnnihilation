package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHub {
    public static final boolean isFilter = true;

    public static final Bitmap[] explosionImageSmall = new Bitmap[28];
    public static final Bitmap[] explosionImageDefault = new Bitmap[28];
    public static final Bitmap[] explosionImageLarge = new Bitmap[28];
    public static final Bitmap[] screen_image = new Bitmap[34];
    public static Bitmap bulletImage;
    public static Bitmap vaderImage;
    public static Bitmap tripleFighterImg;
    public static Bitmap playerImage;
    public static Bitmap bulletEnemyImage;
    public static Bitmap buttonImagePressed;
    public static Bitmap buttonImageNotPressed;
    public static Bitmap imageHeartFull;
    public static Bitmap imageHeartHalf;
    public static Bitmap imageHeartNon;
    public static Bitmap gameoverScreen;
    public static Bitmap pauseButtonImg;

    public ImageHub(Game game) {
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
        for (int i = 0; i < 34; i++) {
            screen_image[i] = Bitmap.createScaledBitmap(screen_image[i], (int) (game.screenWidth * 1.4), game.screenHeight, isFilter);
        }

        gameoverScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
        gameoverScreen = Bitmap.createScaledBitmap(gameoverScreen, game.screenWidth, game.screenHeight, isFilter);

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
            explosionImageSmall[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (50 * game.resizeK), (int) (50 * game.resizeK), isFilter);
        }
        for (int i = 0; i < 28; i++) {
            explosionImageDefault[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (145 * game.resizeK), (int) (145 * game.resizeK), isFilter);
        }
        for (int i = 0; i < 28; i++) {
            explosionImageLarge[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (300 * game.resizeK), (int) (300 * game.resizeK), isFilter);
        }

        bulletImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bullet);
        bulletImage = Bitmap.createScaledBitmap(bulletImage, (int) (7 * game.resizeK), (int) (30 * game.resizeK), isFilter);

        vaderImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.vader3);
        vaderImage = Bitmap.createScaledBitmap(vaderImage, (int) (75 * game.resizeK), (int) (75 * game.resizeK), isFilter);

        tripleFighterImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.triple_fighter);
        tripleFighterImg = Bitmap.createScaledBitmap(tripleFighterImg, (int) (105 * game.resizeK), (int) (105 * game.resizeK), isFilter);

        playerImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.ship);
        playerImage = Bitmap.createScaledBitmap(playerImage, (int) (100 * game.resizeK), (int) (120 * game.resizeK), isFilter);

        bulletEnemyImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bullet_enemy);
        bulletEnemyImage = Bitmap.createScaledBitmap(bulletEnemyImage, (int) (17 * game.resizeK), (int) (50 * game.resizeK), isFilter);

        buttonImagePressed = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.button_press);
        buttonImagePressed = Bitmap.createScaledBitmap(buttonImagePressed, (int) (300 * game.resizeK), (int) (70 * game.resizeK), isFilter);
        buttonImageNotPressed = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.button_notpress);
        buttonImageNotPressed = Bitmap.createScaledBitmap(buttonImageNotPressed, (int) (300 * game.resizeK), (int) (70 * game.resizeK), isFilter);

        imageHeartFull = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.full_heart);
        imageHeartFull = Bitmap.createScaledBitmap(imageHeartFull, (int) (70 * game.resizeK), (int) (60 * game.resizeK), isFilter);

        imageHeartHalf = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.half_heart);
        imageHeartHalf = Bitmap.createScaledBitmap(imageHeartHalf, (int) (70 * game.resizeK), (int) (60 * game.resizeK), isFilter);

        imageHeartNon = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.non_heart);
        imageHeartNon = Bitmap.createScaledBitmap(imageHeartNon, (int) (70 * game.resizeK), (int) (60 * game.resizeK), isFilter);

        pauseButtonImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.pause_button);
        pauseButtonImg = Bitmap.createScaledBitmap(pauseButtonImg, (int) (100 * game.resizeK), (int) (100 * game.resizeK), isFilter);
    }
}
