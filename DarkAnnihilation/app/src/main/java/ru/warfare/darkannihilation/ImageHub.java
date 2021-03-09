package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageHub {
    public static final boolean isFilter = true;
    private int id;

    public static final Bitmap[] explosionImageSmall = new Bitmap[28];
    public static final Bitmap[] explosionImageDefault = new Bitmap[28];
    public static final Bitmap[] explosionImageLarge = new Bitmap[28];
    public static final Bitmap[] screenImage = new Bitmap[34];
    public static final Bitmap[] vaderImage = new Bitmap[3];
    public static final Bitmap[] winScreenImg = new Bitmap[100];

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

        for (int i = 0; i < screenImage.length; i++) {
            id = context.getResources().getIdentifier("_" + i, "drawable", context.getPackageName());
            screenImage[i] = BitmapFactory.decodeResource(context.getResources(), id);
            screenImage[i] = Bitmap.createScaledBitmap(screenImage[i], (int) (game.screenWidth * 1.4), game.screenHeight, isFilter);
        }

        for (int i = 0; i < 28; i++) {
            id = context.getResources().getIdentifier("explosion_" + (i+1), "drawable", context.getPackageName());
            explosionImageSmall[i] = BitmapFactory.decodeResource(context.getResources(), id);
            explosionImageLarge[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (600 * game.resizeK), (int) (600 * game.resizeK), isFilter);
            explosionImageDefault[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (145 * game.resizeK), (int) (145 * game.resizeK), isFilter);
            explosionImageSmall[i] = Bitmap.createScaledBitmap(explosionImageSmall[i], (int) (50 * game.resizeK), (int) (50 * game.resizeK), isFilter);
        }

        for (int i = 0; i < 3; i++) {
            id = context.getResources().getIdentifier("vader" + (i+1), "drawable", context.getPackageName());
            vaderImage[i] = BitmapFactory.decodeResource(game.context.getResources(), id);
            vaderImage[i] = Bitmap.createScaledBitmap(vaderImage[i], (int) (75 * game.resizeK), (int) (75 * game.resizeK), isFilter);
        }

        for (int i = 0; i < winScreenImg.length; i++) {
            id = context.getResources().getIdentifier("win_" + (i+1), "drawable", context.getPackageName());
            winScreenImg[i] = BitmapFactory.decodeResource(context.getResources(), id);
            winScreenImg[i] = Bitmap.createScaledBitmap(winScreenImg[i], game.screenWidth, game.screenHeight, isFilter);
        }

        gameoverScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
        gameoverScreen = Bitmap.createScaledBitmap(gameoverScreen, game.screenWidth, game.screenHeight, isFilter);

        bulletImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bullet);
        bulletImage = Bitmap.createScaledBitmap(bulletImage, (int) (7 * game.resizeK), (int) (30 * game.resizeK), isFilter);

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
