package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHub {
    public static final boolean isFilter = true;

    public static final Bitmap[] explosionImageSmall = new Bitmap[28];
    public static final Bitmap[] explosionImageDefault = new Bitmap[28];
    public static final Bitmap[] explosionImageLarge = new Bitmap[28];
    public static final Bitmap[] screenImage = new Bitmap[34];
    public static final Bitmap[] vaderImage = new Bitmap[3];
    public static Bitmap bulletImage;
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
    public static Bitmap bossImage;
    public static Bitmap laserImage;
    public static Bitmap fightBgImg;
    public static Bitmap healthKitImg;
    public static Bitmap shotgunKitImg;
    public static Bitmap shotgunToGun;
    public static Bitmap gunToShotgun;
    public static Bitmap gunToNone;
    public static Bitmap buckshotImg;
    public static Bitmap rocketImg;
    public static Bitmap attentionImg;
    public static Bitmap factoryImg;
    public static Bitmap minionImg;
    public static Bitmap bombImg;
    public static Bitmap demomanImg;

    public ImageHub(Game game) {
        Context context = game.context;

        screenImage[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable._0);
        screenImage[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable._1);
        screenImage[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable._2);
        screenImage[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable._3);
        screenImage[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable._4);
        screenImage[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable._5);
        screenImage[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable._6);
        screenImage[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable._7);
        screenImage[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable._8);
        screenImage[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable._9);
        screenImage[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable._10);
        screenImage[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable._11);
        screenImage[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable._12);
        screenImage[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable._13);
        screenImage[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable._14);
        screenImage[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable._15);
        screenImage[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable._16);
        screenImage[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable._17);
        screenImage[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable._18);
        screenImage[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable._19);
        screenImage[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable._20);
        screenImage[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable._21);
        screenImage[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable._22);
        screenImage[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable._23);
        screenImage[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable._24);
        screenImage[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable._25);
        screenImage[26] = BitmapFactory.decodeResource(context.getResources(), R.drawable._26);
        screenImage[27] = BitmapFactory.decodeResource(context.getResources(), R.drawable._27);
        screenImage[28] = BitmapFactory.decodeResource(context.getResources(), R.drawable._28);
        screenImage[29] = BitmapFactory.decodeResource(context.getResources(), R.drawable._29);
        screenImage[30] = BitmapFactory.decodeResource(context.getResources(), R.drawable._30);
        screenImage[31] = BitmapFactory.decodeResource(context.getResources(), R.drawable._31);
        screenImage[32] = BitmapFactory.decodeResource(context.getResources(), R.drawable._32);
        screenImage[33] = BitmapFactory.decodeResource(context.getResources(), R.drawable._33);
        for (int i = 0; i < 34; i++) {
            screenImage[i] = Bitmap.createScaledBitmap(screenImage[i], (int) (game.screenWidth * 1.4), game.screenHeight, isFilter);
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
            explosionImageDefault[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (145 * game.resizeK), (int) (145 * game.resizeK), isFilter);
        }
        for (int i = 0; i < 28; i++) {
            explosionImageLarge[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (600 * game.resizeK), (int) (600 * game.resizeK), isFilter);
        }
        for (int i = 0; i < 28; i++) {
            explosionImageSmall[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (50 * game.resizeK), (int) (50 * game.resizeK), isFilter);
        }

        bulletImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bullet);
        bulletImage = Bitmap.createScaledBitmap(bulletImage, (int) (7 * game.resizeK), (int) (30 * game.resizeK), isFilter);

        Bitmap vaderImg1 = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.vader1);
        vaderImg1 = Bitmap.createScaledBitmap(vaderImg1, (int) (75 * game.resizeK), (int) (75 * game.resizeK), isFilter);
        Bitmap vaderImg2 = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.vader2);
        vaderImg2 = Bitmap.createScaledBitmap(vaderImg2, vaderImg1.getWidth(), vaderImg1.getHeight(), isFilter);
        Bitmap vaderImg3 = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.vader3);
        vaderImg3 = Bitmap.createScaledBitmap(vaderImg3, vaderImg1.getWidth(), vaderImg1.getHeight(), isFilter);
        vaderImage[0] = vaderImg1;
        vaderImage[1] = vaderImg2;
        vaderImage[2] = vaderImg3;

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
        pauseButtonImg = Bitmap.createScaledBitmap(pauseButtonImg, 100, 100, isFilter);

        bossImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.boss);
        bossImage = Bitmap.createScaledBitmap(bossImage, (int) (200 * game.resizeK), (int) (200 * game.resizeK), isFilter);

        laserImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.laser);
        laserImage = Bitmap.createScaledBitmap(laserImage, (int) (15 * game.resizeK), (int) (60 * game.resizeK), isFilter);

        fightBgImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.player_vs_boss);
        fightBgImg = Bitmap.createScaledBitmap(fightBgImg, (int) (game.screenWidth * game.resizeK), (int) ((game.screenWidth-150) * game.resizeK), isFilter);

        healthKitImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.health);
        healthKitImg = Bitmap.createScaledBitmap(healthKitImg, (int) (75 * game.resizeK), (int) (75 * game.resizeK), isFilter);

        shotgunKitImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.buckshot);
        shotgunKitImg = Bitmap.createScaledBitmap(shotgunKitImg, (int) (95 * game.resizeK), (int) (95 * game.resizeK), isFilter);

        gunToShotgun = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.gun_to_shotgun);
        gunToShotgun = Bitmap.createScaledBitmap(gunToShotgun, (int) (400 * game.resizeK), (int) (400 * game.resizeK), isFilter);

        gunToNone = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.gun_to_none);
        gunToNone = Bitmap.createScaledBitmap(gunToNone, gunToShotgun.getWidth(), gunToShotgun.getWidth(), isFilter);

        shotgunToGun = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.shotgun_to_gun);
        shotgunToGun = Bitmap.createScaledBitmap(shotgunToGun, gunToShotgun.getWidth(), gunToShotgun.getWidth(), isFilter);

        buckshotImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.cannon_ball);
        buckshotImg = Bitmap.createScaledBitmap(buckshotImg, (int) (15 * game.resizeK), (int) (15 * game.resizeK), isFilter);

        rocketImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.rocket);
        rocketImg = Bitmap.createScaledBitmap(rocketImg, (int) (50 * game.resizeK), (int) (100 * game.resizeK), isFilter);

        attentionImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.attention);
        attentionImg = Bitmap.createScaledBitmap(attentionImg, (int) (70 * game.resizeK), (int) (70 * game.resizeK), isFilter);

        factoryImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.factory);
        factoryImg = Bitmap.createScaledBitmap(factoryImg, (int) ((game.screenWidth / 1.3) * game.resizeK),
                (int) ((game.screenWidth / 1.3) * game.resizeK * 0.3), isFilter);

        minionImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.minion);
        minionImg = Bitmap.createScaledBitmap(minionImg, (int) (80 * game.resizeK), (int) (80 * game.resizeK), isFilter);

        bombImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bomb);
        bombImg = Bitmap.createScaledBitmap(bombImg, (int) (30 * game.resizeK), (int) (70 * game.resizeK), isFilter);

        demomanImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.demoman);
        demomanImg = Bitmap.createScaledBitmap(demomanImg, (int) (290 * game.resizeK), (int) (170 * game.resizeK), isFilter);

    }
}
