package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public final class ImageHub {
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
    public static Bitmap buttonSaturnImg;
    public static Bitmap saturnImg;
    public static Bitmap bulletSaturnImg;
    public static Bitmap spiderImg;
    public static Bitmap bulletBuckshotSaturnImg;
    public static Bitmap XWingImg;
    public static Bitmap sunriseImg;
    public static Bitmap bossVadersImg;
    public static Bitmap bulletBossVadersImg;

    public static void init(Context context) {

        int screensSizeX = (int) (Game.screenWidth * 1.4);
        int vaderSize = (int) (75 * Game.resizeK);
        int eX145 = (int) (145 * Game.resizeK);
        int eX152 = (int) (152 * Game.resizeK);
        int eX50 = (int) (50 * Game.resizeK);
        int eX52 = (int) (52 * Game.resizeK);
        int eX500 = (int) (500 * Game.resizeK);
        int eX435 = (int) (435 * Game.resizeK);
        int sW150 = (int) ((Game.screenWidth -150) * Game.resizeK);
        int sW = (int) (Game.screenWidth * Game.resizeK);
        int portalSize = (int) (300 * Game.resizeK);
        int eX70 = (int) (70 * Game.resizeK);
        int eX300 = (int) (300 * Game.resizeK);
        int eX60 = (int) (60 * Game.resizeK);
        int eX100 = (int) (100 * Game.resizeK);
        int eX105 = (int) (105 * Game.resizeK);
        int eX15 = (int) (15 * Game.resizeK);
        int fact = (int) ((Game.screenWidth / 1.3) * Game.resizeK);
        int eX200 = (int) (200 * Game.resizeK);
        int eX400 = (int) (400 * Game.resizeK);
        int eX80 = (int) (80 * Game.resizeK);
        int eX13 = (int) (13 * Game.resizeK);
        int eX175 = (int) (175 * Game.resizeK);
        int eX207 = (int) (207 * Game.resizeK);
        int eX120 = (int) (120 * Game.resizeK);

        RequestBuilder<Bitmap> requestBuilder =
                Glide.with(context)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

        requestBuilder.load(R.drawable.bullet)
                .override((int) (7 * Game.resizeK), (int) (30 * Game.resizeK))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bulletImage = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.triple_fighter)
                .override(eX105, eX105)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  tripleFighterImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.boss)
                .override(eX200, eX200)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bossImage = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.health)
                .override(vaderSize, vaderSize)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  healthKitImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.buckshot)
                .override((int) (97 * Game.resizeK), (int) (120 * Game.resizeK))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  shotgunKitImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.gun_to_shotgun)
                .override(eX400, eX400)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  gunToShotgun = resource;
                                  return true;
                              }
                          }
                ).submit();
        requestBuilder.load(R.drawable.gun_to_none)
                .override(eX400, eX400)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  gunToNone = resource;
                                  return true;
                              }
                          }
                ).submit();
        requestBuilder.load(R.drawable.shotgun_to_gun)
                .override(eX400, eX400)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  shotgunToGun = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.minion)
                .override(eX80, eX50)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  minionImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.demoman)
                .override((int) (290 * Game.resizeK), eX175)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  demomanImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.saturn_bullet)
                .override(eX13, eX13)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bulletSaturnImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.spider)
                .override((int) (350 * Game.resizeK), eX175)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  spiderImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.x_wing)
                .override(eX200, (int) (146 * Game.resizeK))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  XWingImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.area)
                .override((int) (450 * Game.resizeK), (int) (279 * Game.resizeK))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  sunriseImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.boss_vaders)
                .override((int) (350 * Game.resizeK), (int) (255 * Game.resizeK))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bossVadersImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.bull_boss_vader)
                .override((int) (140 * Game.resizeK), (int) (135 * Game.resizeK))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bulletBossVadersImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.gameover)
                .centerCrop()
                .override(Game.screenWidth, Game.screenHeight)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  gameoverScreen = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.bullet_enemy)
                .override((int) (17 * Game.resizeK), eX50)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bulletEnemyImage = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.full_heart)
                .override(eX70, eX60)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  imageHeartFull = resource;
                                  return true;
                              }
                          }
                ).submit();
        requestBuilder.load(R.drawable.half_heart)
                .override(eX70, eX60)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  imageHeartHalf = resource;
                                  return true;
                              }
                          }
                ).submit();
        requestBuilder.load(R.drawable.non_heart)
                .override(eX70, eX60)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  imageHeartNon = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.pause_button)
                .override(eX120, eX120)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  pauseButtonImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.laser)
                .override(eX15, eX60)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  laserImage = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.player_vs_boss)
                .override(sW, sW150)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  playerVsBoss = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.player_vs_vader)
                .override(sW, sW150)
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

        requestBuilder.load(R.drawable.saturn_vs_boss)
                .override(sW, sW150)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  saturnVsBoss = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.saturn_vs_vader)
                .override(sW, sW150)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  saturnVsVaders = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.cannon_ball)
                .override(eX15, eX15)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  buckshotImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.rocket)
                .override(eX50, eX100)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  rocketImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.attention)
                .override(eX70, eX70)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  attentionImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.factory)
                .override(fact, (int) (fact * 0.3))
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  factoryImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.bomb)
                .override((int) (30 * Game.resizeK), eX70)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bombImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.ship_button)
                .override(eX100, eX120)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  buttonPlayerImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.ship)
                .override(eX100, eX120)
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

        requestBuilder.load(R.drawable.saturn_btn)
                .override(eX100, eX207)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  buttonSaturnImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.saturn)
                .override(eX100, eX207)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  saturnImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.buckshot_saturn)
                .override(eX15, eX15)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }
                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  bulletBuckshotSaturnImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        buttonImagePressed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_press),
                eX300, eX70, isFilter);
        buttonImageNotPressed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_notpress),
                eX300, eX70, isFilter);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            if (i < 3) {
                requestBuilder.load(context.getResources().getIdentifier("vader" + (i + 1), "drawable", context.getPackageName()))
                        .override(vaderSize, vaderSize)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }
                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          vaderImage[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
                requestBuilder.load(context.getResources().getIdentifier("vader1" + (i + 1), "drawable", context.getPackageName()))
                        .override(vaderSize, vaderSize)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }
                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          vaderOldImage[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
            }
            if (i < 12) {
                requestBuilder.load(context.getResources().getIdentifier("loading" + i, "drawable", context.getPackageName()))
                        .centerCrop()
                        .override(Game.screenWidth, Game.screenHeight)
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
            if (i < 13) {
                requestBuilder.load(context.getResources().getIdentifier("explosion_skull_" + (i + 1), "drawable", context.getPackageName()))
                        .override(eX435, eX500)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }
                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          explosionLarge[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
            }
            if (i < 20) {
                requestBuilder.load(context.getResources().getIdentifier("portal0" + i, "drawable", context.getPackageName()))
                        .override(portalSize, portalSize)
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

                requestBuilder.load(context.getResources().getIdentifier("thunder" + i, "drawable", context.getPackageName()))
                        .centerCrop()
                        .override(screensSizeX, Game.screenHeight)
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
            if (i < 23) {
                requestBuilder.load(context.getResources().getIdentifier("explosion_" + (i + 1), "drawable", context.getPackageName()))
                        .override(eX145, eX152)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }
                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          explosionTripleImageMedium[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
                requestBuilder.load(context.getResources().getIdentifier("explosion_" + (i + 1), "drawable", context.getPackageName()))
                        .override(eX50, eX52)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }
                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          explosionTripleImageSmall[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
            }
            if (i < 28) {
                requestBuilder.load(context.getResources().getIdentifier("default_explosion_" + (i + 1), "drawable", context.getPackageName()))
                        .override(eX145, eX145)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }
                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          explosionDefaultImageMedium[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
                requestBuilder.load(context.getResources().getIdentifier("default_explosion_" + (i + 1), "drawable", context.getPackageName()))
                        .override(eX50, eX50)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }
                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          explosionDefaultImageSmall[finalI] = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
            }
            if (i < 34) {
                screenImage[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("_" + i, "drawable", context.getPackageName())),
                        screensSizeX, Game.screenHeight, isFilter);
            }
            requestBuilder.load(context.getResources().getIdentifier("win_" + (i + 1), "drawable", context.getPackageName()))
                    .centerCrop()
                    .override(Game.screenWidth, Game.screenHeight)
                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                      return false;
                                  }
                                  @Override
                                  public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                      winScreenImg[finalI] = resource;
                                      return true;
                                  }
                              }
                    ).submit();
        }
    }
}
