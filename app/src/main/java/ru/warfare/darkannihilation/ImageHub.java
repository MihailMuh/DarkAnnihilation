package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

public class ImageHub {
    public static final boolean isFilter = true;

    public static final Bitmap[] explosionTripleImageSmall = new Bitmap[23];
    public static final Bitmap[] explosionTripleImageMedium = new Bitmap[23];
    public static final Bitmap[] explosionDefaultImageSmall = new Bitmap[28];
    public static final Bitmap[] explosionDefaultImageMedium = new Bitmap[28];
    public static final Bitmap[] explosionLarge = new Bitmap[13];

    public static final Bitmap[] screenImage = new Bitmap[34];
    public static final Bitmap[] vaderImage = new Bitmap[3];
    public static final Bitmap[] vaderOldImage = new Bitmap[3];
    public static final Bitmap[] winScreenImg = new Bitmap[100];
    public static final Bitmap[] portalImages = new Bitmap[20];
    public static final Bitmap[] thunderScreen = new Bitmap[20];
    public static final Bitmap[] loadingImages = new Bitmap[12];

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
    public static Bitmap playerVsBoss;
    public static Bitmap saturnVsBoss;
    public static Bitmap saturnVsVaders;
    public static Bitmap playerVsVaders;

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
    public static Bitmap buttonPlayerImg;
    public static Bitmap buttonGunnerImg;
    public static Bitmap saturnImg;
    public static Bitmap bulletSaturnImg;
    public static Bitmap spiderImg;
    public static Bitmap bulletBuckshotSaturnImg;
    public static Bitmap XWingImg;
    public static Bitmap sunriseImg;
    public static Bitmap bossVadersImg;
    public static Bitmap bulletBossVadersImg;

    public ImageHub(Game game) {
        Context context = game.context;

        int screensSizeX = (int) (game.screenWidth * 1.4);
        int vaderSize = (int) (75 * game.resizeK);
        int eX145 = (int) (145 * game.resizeK);
        int eX152 = (int) (152 * game.resizeK);
        int eX50 = (int) (50 * game.resizeK);
        int eX52 = (int) (52 * game.resizeK);
        int eX500 = (int) (500 * game.resizeK);
        int eX435 = (int) (435 * game.resizeK);
        int sW150 = (int) ((game.screenWidth-150) * game.resizeK);
        int sW = (int) (game.screenWidth * game.resizeK);
        int portalSize = (int) (300 * game.resizeK);

        Thread thread1 = new Thread(() -> {
            int eX70 = (int) (70 * game.resizeK);
            int eX300 = (int) (300 * game.resizeK);
            int eX60 = (int) (60 * game.resizeK);
            int eX100 = (int) (100 * game.resizeK);
            int eX15 = (int) (15 * game.resizeK);
            int fact = (int) ((game.screenWidth / 1.3) * game.resizeK);

            bulletImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet),
                    (int) (7 * game.resizeK), (int) (30 * game.resizeK), isFilter);

            tripleFighterImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.triple_fighter),
                    (int) (105 * game.resizeK), (int) (105 * game.resizeK), isFilter);

            bossImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.boss),
                    (int) (200 * game.resizeK), (int) (200 * game.resizeK), isFilter);

            healthKitImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.health),
                    (int) (75 * game.resizeK), (int) (75 * game.resizeK), isFilter);

            shotgunKitImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.buckshot),
                    (int) (95 * game.resizeK), (int) (95 * game.resizeK), isFilter);

            gunToShotgun = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gun_to_shotgun),
                    (int) (400 * game.resizeK), (int) (400 * game.resizeK), isFilter);

            minionImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.minion),
                    (int) (80 * game.resizeK), (int) (80 * game.resizeK), isFilter);

            demomanImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.demoman),
                    (int) (290 * game.resizeK), (int) (170 * game.resizeK), isFilter);

            bulletSaturnImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_bullet),
                    (int) (13 * game.resizeK), (int) (13 * game.resizeK), isFilter);

            spiderImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spider),
                    (int) (350 * game.resizeK), (int) (175 * game.resizeK), isFilter);

            XWingImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.x_wing),
                    (int) (200 * game.resizeK), (int) (146 * game.resizeK), isFilter);

            sunriseImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.area),
                    (int) (450 * game.resizeK), (int) (279 * game.resizeK), isFilter);

            bossVadersImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.boss_vaders),
                    (int) (350 * game.resizeK), (int) (255 * game.resizeK), isFilter);

            bulletBossVadersImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bull_boss_vader),
                    (int) (140 * game.resizeK), (int) (135 * game.resizeK), isFilter);

            gameoverScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover),
                    game.screenWidth, game.screenHeight, isFilter);

            bulletEnemyImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_enemy),
                    (int) (17 * game.resizeK), eX50, isFilter);

            buttonImagePressed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_press),
                    eX300, eX70, isFilter);
            buttonImageNotPressed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_notpress),
                    eX300, eX70, isFilter);

            imageHeartFull = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.full_heart),
                    eX70, eX60, isFilter);

            imageHeartHalf = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.half_heart),
                    eX70, eX60, isFilter);

            imageHeartNon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.non_heart),
                    eX70, eX60, isFilter);

            pauseButtonImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_button),
                    100, 100, isFilter);

            laserImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.laser),
                    (int) (15 * game.resizeK), eX60, isFilter);

            playerVsBoss = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_vs_boss),
                    sW, sW150, isFilter);

            saturnVsBoss = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_vs_boss),
                    sW, sW150, isFilter);

            gunToNone = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gun_to_none),
                    gunToShotgun.getWidth(), gunToShotgun.getWidth(), isFilter);

            shotgunToGun = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shotgun_to_gun),
                    gunToShotgun.getWidth(), gunToShotgun.getWidth(), isFilter);

            buckshotImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cannon_ball),
                    eX15, eX15, isFilter);

            rocketImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket),
                    eX50, eX100, isFilter);

            attentionImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.attention),
                    eX70, eX70, isFilter);

            factoryImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.factory),
                    fact, (int) (fact * 0.3), isFilter);

            bombImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb),
                    (int) (30 * game.resizeK), eX70, isFilter);

            buttonPlayerImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ship_button),
                    eX100, (int) (120 * game.resizeK), isFilter);

            buttonGunnerImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_btn),
                    eX100, (int) (207 * game.resizeK), isFilter);

            saturnImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn),
                    eX100, (int) (207 * game.resizeK), isFilter);

            bulletBuckshotSaturnImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.buckshot_saturn),
                    eX15, eX15, isFilter);

            saturnVsVaders = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_vs_vader),
                    sW, sW150, isFilter);
        }); thread1.start();

        GlideApp.with(context)
                .asBitmap()
                .load(R.drawable.ship)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().override((int) (100 * game.resizeK), (int) (120 * game.resizeK)))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  playerImage = resource;
                                  return true;
                              }
                          }
                ).submit();

        GlideApp.with(context)
                .asBitmap()
                .load(R.drawable.player_vs_vader)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().override(sW, sW150))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  playerVsVaders = resource;
                                  return true;
                              }
                          }
                ).submit();

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            if (i < 12) {
                GlideApp.with(context)
                        .asBitmap()
                        .load(context.getResources().getIdentifier("loading" + i, "drawable", context.getPackageName()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().override(game.screenWidth, game.screenHeight))
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          loadingImages[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
            }
            if (i < 20) {
                GlideApp.with(context)
                        .asBitmap()
                        .load(context.getResources().getIdentifier("portal0" + i, "drawable", context.getPackageName()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().override(portalSize, portalSize))
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          portalImages[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();

                GlideApp.with(context)
                        .asBitmap()
                        .load(context.getResources().getIdentifier("thunder" + i, "drawable", context.getPackageName()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .apply(new RequestOptions().override(screensSizeX, game.screenHeight))
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          thunderScreen[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
            }
            if (i < 34) {
//                GlideApp.with(context)
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .centerInside()
//                    .load(context.getResources().getIdentifier("_" + i, "drawable", context.getPackageName()))
//                    .apply(new RequestOptions().override(screensSizeX, game.screenHeight))
//                    .listener(new RequestListener<Bitmap>() {
//                                  @Override
//                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                                      return false;
//                                  }
//
//                                  @Override
//                                  public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                                      screenImage[finalI] = resource;
//                                      return true;
//                                  }
//                              }
//                    ).submit();
                screenImage[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("_" + i, "drawable", context.getPackageName())),
                        screensSizeX, game.screenHeight, isFilter);
            }
            GlideApp.with(context)
                    .asBitmap()
                    .load(context.getResources().getIdentifier("win_" + (i + 1), "drawable", context.getPackageName()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .apply(new RequestOptions().override(game.screenWidth, game.screenHeight))
                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                      winScreenImg[finalI] = resource;
                                      Service.print("" + finalI);
                                      return true;
                                  }
                              }
                    ).submit();
        }

        for (int i = 0; i < 28; i++) {
            if (i < explosionTripleImageSmall.length) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("explosion_" + (i + 1), "drawable", context.getPackageName()));
                explosionTripleImageMedium[i] = Bitmap.createScaledBitmap(bitmap, eX145, eX152, isFilter);
                explosionTripleImageSmall[i] = Bitmap.createScaledBitmap(bitmap, eX50, eX52, isFilter);
            }
            if (i < 3) {
                vaderImage[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("vader" + (i + 1), "drawable", context.getPackageName())),
                        vaderSize, vaderSize, isFilter);

                vaderOldImage[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("vader1" + (i + 1), "drawable", context.getPackageName())),
                        vaderSize, vaderSize, isFilter);
            }
            if (i < explosionLarge.length) {
                explosionLarge[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("explosion_skull_" + (i + 1), "drawable", context.getPackageName())),
                        eX435, eX500, isFilter);
            }

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    context.getResources().getIdentifier("default_explosion_" + (i + 1), "drawable", context.getPackageName()));
            explosionDefaultImageMedium[i] = Bitmap.createScaledBitmap(bitmap, eX145, eX145, isFilter);
            explosionDefaultImageSmall[i] = Bitmap.createScaledBitmap(bitmap, eX50, eX50, isFilter);
        }

        int eX70 = (int) (70 * game.resizeK);
        int eX300 = (int) (300 * game.resizeK);
        int eX60 = (int) (60 * game.resizeK);
        int eX100 = (int) (100 * game.resizeK);
        int eX15 = (int) (15 * game.resizeK);
        int fact = (int) ((game.screenWidth / 1.3) * game.resizeK);

//        bulletImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet),
//                (int) (7 * game.resizeK), (int) (30 * game.resizeK), isFilter);
//
//        tripleFighterImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.triple_fighter),
//                (int) (105 * game.resizeK), (int) (105 * game.resizeK), isFilter);
//
//        bossImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.boss),
//                (int) (200 * game.resizeK), (int) (200 * game.resizeK), isFilter);
//
//        healthKitImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.health),
//                (int) (75 * game.resizeK), (int) (75 * game.resizeK), isFilter);
//
//        shotgunKitImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.buckshot),
//                (int) (95 * game.resizeK), (int) (95 * game.resizeK), isFilter);
//
//        gunToShotgun = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gun_to_shotgun),
//                (int) (400 * game.resizeK), (int) (400 * game.resizeK), isFilter);
//
//        minionImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.minion),
//                (int) (80 * game.resizeK), (int) (80 * game.resizeK), isFilter);
//
//        demomanImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.demoman),
//                (int) (290 * game.resizeK), (int) (170 * game.resizeK), isFilter);
//
//        bulletSaturnImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_bullet),
//                (int) (13 * game.resizeK), (int) (13 * game.resizeK), isFilter);
//
//        spiderImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spider),
//                (int) (350 * game.resizeK), (int) (175 * game.resizeK), isFilter);
//
//        XWingImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.x_wing),
//                (int) (200 * game.resizeK), (int) (146 * game.resizeK), isFilter);
//
//        sunriseImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.area),
//                (int) (450 * game.resizeK), (int) (279 * game.resizeK), isFilter);
//
//        bossVadersImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.boss_vaders),
//                (int) (350 * game.resizeK), (int) (255 * game.resizeK), isFilter);
//
//        bulletBossVadersImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bull_boss_vader),
//                (int) (140 * game.resizeK), (int) (135 * game.resizeK), isFilter);
//
//        gameoverScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover),
//                game.screenWidth, game.screenHeight, isFilter);
//
//        bulletEnemyImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_enemy),
//                (int) (17 * game.resizeK), eX50, isFilter);
//
//        buttonImagePressed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_press),
//                eX300, eX70, isFilter);
//        buttonImageNotPressed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_notpress),
//                eX300, eX70, isFilter);
//
//        imageHeartFull = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.full_heart),
//                eX70, eX60, isFilter);
//
//        imageHeartHalf = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.half_heart),
//                eX70, eX60, isFilter);
//
//        imageHeartNon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.non_heart),
//                eX70, eX60, isFilter);
//
//        pauseButtonImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_button),
//                100, 100, isFilter);
//
//        laserImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.laser),
//                (int) (15 * game.resizeK), eX60, isFilter);
//
//        playerVsBoss = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_vs_boss),
//                sW, sW150, isFilter);
//
//        saturnVsBoss = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_vs_boss),
//                sW, sW150, isFilter);
//
//        gunToNone = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gun_to_none),
//                gunToShotgun.getWidth(), gunToShotgun.getWidth(), isFilter);
//
//        shotgunToGun = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shotgun_to_gun),
//                gunToShotgun.getWidth(), gunToShotgun.getWidth(), isFilter);
//
//        buckshotImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cannon_ball),
//                eX15, eX15, isFilter);
//
//        rocketImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket),
//                eX50, eX100, isFilter);
//
//        attentionImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.attention),
//                eX70, eX70, isFilter);
//
//        factoryImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.factory),
//                fact, (int) (fact * 0.3), isFilter);
//
//        bombImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb),
//                (int) (30 * game.resizeK), eX70, isFilter);
//
//        buttonPlayerImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ship_button),
//                eX100, (int) (120 * game.resizeK), isFilter);
//
//        buttonGunnerImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_btn),
//                eX100, (int) (207 * game.resizeK), isFilter);
//
//        saturnImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn),
//                eX100, (int) (207 * game.resizeK), isFilter);
//
//        bulletBuckshotSaturnImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.buckshot_saturn),
//                eX15, eX15, isFilter);
//
//        saturnVsVaders = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.saturn_vs_vader),
//                sW, sW150, isFilter);
//
    }
}
