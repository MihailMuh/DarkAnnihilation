package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import pl.droidsonroids.gif.GifImageView;

import static ru.warfare.darkannihilation.Constants.NUMBER_ATOMIC_BOMB_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_DEFAULT_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_LIGHTNING_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_LOADING_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_SKULL_EXPLOSION;
import static ru.warfare.darkannihilation.Constants.NUMBER_STAR_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_THUNDER_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_TRIPLE_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.Constants.NUMBER_VADER_IMAGES;

public final class ImageHub {
    public static final boolean isFilter = true;

    public static final Bitmap[] explosionTripleImageSmall = new Bitmap[NUMBER_TRIPLE_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionTripleImageMedium = new Bitmap[NUMBER_TRIPLE_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionDefaultImageSmall = new Bitmap[NUMBER_DEFAULT_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionDefaultImageMedium = new Bitmap[NUMBER_DEFAULT_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionLarge = new Bitmap[NUMBER_SKULL_EXPLOSION];

    public static Bitmap[] screenImage = new Bitmap[NUMBER_STAR_SCREEN_IMAGES];
    public static Bitmap[] vaderImage = new Bitmap[NUMBER_VADER_IMAGES];
    public static Bitmap[] vaderOldImage = new Bitmap[NUMBER_VADER_IMAGES];
    public static Bitmap[] portalImages = new Bitmap[NUMBER_PORTAL_IMAGES];
    public static Bitmap[] thunderScreen = new Bitmap[NUMBER_THUNDER_SCREEN_IMAGES];
    public static Bitmap[] atomBombImage = new Bitmap[NUMBER_ATOMIC_BOMB_IMAGES];
    public static final Bitmap[] loadingImages = new Bitmap[NUMBER_LOADING_SCREEN_IMAGES];
    public static Bitmap[] thunderImage = new Bitmap[NUMBER_LIGHTNING_IMAGES];

    public static Bitmap bitmap;
    public static Bitmap bulletImage;
    public static Bitmap tripleFighterImg;
    public static Bitmap playerImage;
    public static Bitmap bulletEnemyImage;
    public static Bitmap buttonImagePressed;
    public static Bitmap buttonImageNotPressed;
    public static Bitmap imageHeartFull;
    public static Bitmap imageHeartHalf;
    public static Bitmap imageHeartNon;
    public static Bitmap imageBlueHeartHalf;
    public static Bitmap imageBlueHeartFull;
    public static Bitmap gameoverScreen;
    public static Bitmap pauseButtonImg;
    public static Bitmap bossImage;
    public static Bitmap laserImage;
    public static Bitmap playerVsBoss;
    public static Bitmap saturnVsBoss;
    public static Bitmap emeraldVsBoss;
    public static Bitmap saturnVsVaders;
    public static Bitmap playerVsVaders;
    public static Bitmap emeraldVsVaders;
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
    public static Bitmap bufferImg;
    public static Bitmap buttonEmeraldImg;
    public static Bitmap emeraldImg;
    public static Bitmap dynamiteImg;

    public static Drawable onImg;
    public static Drawable offImg;
    public static Drawable enImg;
    public static Drawable ruImg;
    public static Drawable frImg;
    public static Drawable spImg;
    public static Drawable geImg;
    public static GifDrawable gifDrawable;

    private static final int screenWidth = Service.getScreenWidth();
    private static final int screenHeight = Service.getScreenHeight();
    private static final double resizeK = Service.getResizeCoefficient();

    public static final int eX75 = (int) (75 * resizeK);
    public static final int eX70 = (int) (70 * resizeK);
    public static final int eX300 = (int) (300 * resizeK);
    private static final int eX13 = (int) (13 * resizeK);
    private static final int eX120 = (int) (120 * resizeK);
    private static final int eX207 = (int) (207 * resizeK);
    private static final int eX152 = (int) (152 * resizeK);
    private static final int eX3545 = (int) (0.3545 * screenHeight);
    private static final int eX169 = (int) (169 * resizeK);
    private static final int screensSizeX = (int) (screenWidth * 1.4);
    private static final int eX50 = (int) (50 * resizeK);
    private static final int eX60 = (int) (60 * resizeK);
    private static final int eX100 = (int) (100 * resizeK);
    private static final int eX105 = (int) (105 * resizeK);
    private static final int eX15 = (int) (15 * resizeK);
    private static final int fact = (int) ((screenWidth / 1.3) * resizeK);
    private static final int eX200 = (int) (200 * resizeK);
    private static final int eX80 = (int) (80 * resizeK);
    private static final int eX175 = (int) (175 * resizeK);
    private static final int eX400 = (int) (400 * resizeK);
    private static final int portalSize = (int) (300 * resizeK);

    @SuppressLint("StaticFieldLeak")
    public static RequestBuilder<Bitmap> requestBuilder;
    private static final Matrix matrix = new Matrix();
    private static Resources res;
    private static String name;

    public static void init(Context context) {
        int eX145 = (int) (145 * resizeK);
        int eX52 = (int) (52 * resizeK);
        int sW = (int) (screenWidth * resizeK);
        int sW150 = (int) (sW * 0.95);
        int eX522 = (int) (522 * resizeK);
        int eX600 = (int) (600 * resizeK);
        int pauseBtn = (int) (180 * resizeK);
        int eX72 = (int) (72 * resizeK);
        int eX61 = (int) (61 * resizeK);
        res = context.getResources();
        name = context.getPackageName();

        requestBuilder =
                GlideApp.with(context)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

        loadFirstLevelBitmaps();
        new Thread(() -> loadSettingsImages(context)).start();

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

        requestBuilder.load(R.drawable.pause_button)
                .override(pauseBtn, pauseBtn)
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

        requestBuilder.load(R.drawable.bullet)
                .override((int) (7 * resizeK), (int) (30 * resizeK))
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

        requestBuilder.load(R.drawable.health)
                .override(eX75, eX75)
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
                .override((int) (97 * resizeK), (int) (120 * resizeK))
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

        requestBuilder.load(R.drawable.gameover)
                .centerCrop()
                .override(screenWidth, screenHeight)
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
                .override((int) (17 * resizeK), eX50)
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

        requestBuilder.load(R.drawable.full_blue_heart)
                .override(eX72, eX61)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  imageBlueHeartFull = resource;
                                  return true;
                              }
                          }
                ).submit();
        requestBuilder.load(R.drawable.half_blue_heart)
                .override(eX72, eX61)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  imageBlueHeartHalf = resource;
                                  return true;
                              }
                          }
                ).submit();
        requestBuilder.load(R.drawable.full_heart)
                .override(eX72, eX61)
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
                .override(eX72, eX61)
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
                .override(eX72, eX61)
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

        requestBuilder.load(R.drawable.emerald_vs_boss)
                .override(sW, sW150)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  emeraldVsBoss = resource;
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

        requestBuilder.load(R.drawable.emerald_vs_vader)
                .override(sW, sW150)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  emeraldVsVaders = resource;
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

        requestBuilder.load(R.drawable.emerald_btn)
                .override(eX152, eX169)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  buttonEmeraldImg = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.button_press)
                .override(eX300, eX70)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  buttonImagePressed = resource;
                                  return true;
                              }
                          }
                ).submit();

        requestBuilder.load(R.drawable.button_notpress)
                .override(eX300, eX70)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  buttonImageNotPressed = resource;
                                  return true;
                              }
                          }
                ).submit();

        for (int i = 0; i < 34; i++) {
            int finalI = i;
            if (i < 3) {
                requestBuilder.load(res.getIdentifier("vader" + (i + 1), "drawable", name))
                        .override(eX75, eX75)
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
            }
            if (i < 12) {
                requestBuilder.load(res.getIdentifier("loading" + i, "drawable", name))
                        .centerCrop()
                        .override(screenWidth, screenHeight)
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
                requestBuilder.load(res.getIdentifier("explosion_skull_" + (i + 1), "drawable", name))
                        .override(eX522, eX600)
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
            if (i < 23) {
                requestBuilder.load(res.getIdentifier("explosion_" + (i + 1), "drawable", name))
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
                requestBuilder.load(res.getIdentifier("explosion_" + (i + 1), "drawable", name))
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
                requestBuilder.load(res.getIdentifier("default_explosion_" + (i + 1), "drawable", name))
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
                requestBuilder.load(res.getIdentifier("default_explosion_" + (i + 1), "drawable", name))
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
            requestBuilder.load(res.getIdentifier("_" + i, "drawable", name))
                    .centerCrop()
                    .override(screensSizeX, screenHeight)
                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                      screenImage[finalI] = resource;
                                      if (finalI > 31) {
                                          Game.endImgInit = true;
                                      }
                                      return true;
                                  }
                              }
                    ).submit();
        }
    }

    public static void loadPortalImages() {
        if (portalImages[0] == null) {
            for (int i = 0; i < 20; i++) {
                int finalI = i;
                requestBuilder.load(res.getIdentifier("portal0" + i, "drawable", name))
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
            }
        }
    }

    public static void deletePortalImages() {
        if (portalImages[0] != null) {
            for (int i = 0; i < 20; i++) {
                portalImages[i].recycle();
            }
            portalImages = new Bitmap[NUMBER_PORTAL_IMAGES];
        }
    }

    public static void loadSecondLevelImages() {
        if (spiderImg == null) {
            requestBuilder.load(R.drawable.spider)
                    .override((int) (350 * resizeK), eX175)
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
                    .override(eX200, (int) (146 * resizeK))
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
                    .override((int) (450 * resizeK), (int) (279 * resizeK))
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
                    .override((int) (350 * resizeK), (int) (255 * resizeK))
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
                    .override((int) (140 * resizeK), (int) (135 * resizeK))
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
            requestBuilder.load(R.drawable.buffer)
                    .override(eX400, (int) (resizeK * 353))
                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                      bufferImg = resource;
                                      return true;
                                  }
                              }
                    ).submit();
            for (int i = 0; i < 20; i++) {
                int finalI = i;
                if (i < 3) {
                    requestBuilder.load(res.getIdentifier("vader1" + (i + 1), "drawable", name))
                            .override(eX75, eX75)
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
                if (i < 4) {
                    requestBuilder.load(res.getIdentifier("atom" + i, "drawable", name))
                            .override(eX100, eX300)
                            .listener(new RequestListener<Bitmap>() {
                                          @Override
                                          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                              return false;
                                          }

                                          @Override
                                          public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                              atomBombImage[finalI] = resource;
                                              return true;
                                          }
                                      }
                            ).submit();
                }
                requestBuilder.load(res.getIdentifier("thunder" + i, "drawable", name))
                        .centerCrop()
                        .override(screensSizeX, screenHeight)
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
        }
    }

    public static void deleteSecondLevelImages() {
        if (thunderScreen[0] != null) {
            for (int i = 0; i < 20; i++) {
                thunderScreen[i].recycle();
                if (i < 3) {
                    vaderOldImage[i].recycle();
                }
                if (i < 4) {
                    atomBombImage[i].recycle();
                }
            }
            thunderScreen = new Bitmap[NUMBER_THUNDER_SCREEN_IMAGES];
            vaderOldImage = new Bitmap[NUMBER_VADER_IMAGES];
            atomBombImage = new Bitmap[NUMBER_ATOMIC_BOMB_IMAGES];

            spiderImg.recycle();
            spiderImg = null;

            sunriseImg.recycle();
            sunriseImg = null;

            bossVadersImg.recycle();
            bossVadersImg = null;

            bufferImg.recycle();
            bufferImg = null;

            bulletBossVadersImg.recycle();
            bulletBossVadersImg = null;

            XWingImg.recycle();
            XWingImg = null;
        }
    }

    public static boolean needImagesForFirstLevel() {
        return screenImage[0] == null;
    }

    public static void loadFirstLevelImages() {
        Game.endImgInit = false;
        loadFirstLevelBitmaps();
        for (int i = 0; i < 34; i++) {
            int finalI = i;
            if (i < 3) {
                requestBuilder.load(res.getIdentifier("vader" + (i + 1), "drawable", name))
                        .override(eX75, eX75)
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
            }
            requestBuilder.load(res.getIdentifier("_" + i, "drawable", name))
                    .centerCrop()
                    .override(screensSizeX, screenHeight)
                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                      screenImage[finalI] = resource;
                                      if (finalI == 33) {
                                          Game.endImgInit = true;
                                      }
                                      return true;
                                  }
                              }
                    ).submit();
        }
    }

    private static void loadFirstLevelBitmaps() {
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
                .override(eX400, eX400)
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
                .override((int) (290 * resizeK), eX175)
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
                .override((int) (30 * resizeK), eX70)
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
    }

    public static void deleteFirstLevelImages() {
        if (screenImage[0] != null) {
            for (int i = 0; i < 34; i++) {
                screenImage[i].recycle();
                if (i < 3) {
                    vaderImage[i].recycle();
                }
            }
            screenImage = new Bitmap[NUMBER_STAR_SCREEN_IMAGES];
            vaderImage = new Bitmap[NUMBER_VADER_IMAGES];

            factoryImg.recycle();
            factoryImg = null;

            attentionImg.recycle();
            attentionImg = null;

            rocketImg.recycle();
            rocketImg = null;

            laserImage.recycle();
            laserImage = null;

            demomanImg.recycle();
            demomanImg = null;

            minionImg.recycle();
            minionImg = null;

            bombImg.recycle();
            bombImg = null;

            tripleFighterImg.recycle();
            tripleFighterImg = null;
        }
    }

    public static void loadWinImages(MainActivity mainActivity) {
        mainActivity.runOnUiThread(new Thread(() -> {
            mainActivity.gif = mainActivity.findViewById(R.id.gifView);
            mainActivity.gif.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            GlideApp.with(mainActivity)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .override(screenWidth, screenHeight)
                    .load(R.drawable.win)
                    .addListener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            mainActivity.gif.setVisibility(GifImageView.VISIBLE);
                            resource.setLoopCount(1);
                            gifDrawable = resource;
                            mainActivity.game.generateWin();
                            AudioHub.restartFlightMusic();
                            return false;
                        }
                    })
                    .into(mainActivity.gif);
        }));
    }

    public static void deleteWinImages(MainActivity mainActivity) {
        mainActivity.runOnUiThread(new Thread(() -> {
            mainActivity.gif.setVisibility(GifImageView.GONE);
            mainActivity.gif.setImageDrawable(null);
            mainActivity.gif = null;
        }));
    }

    public static boolean isWin() {
        if (gifDrawable != null) {
            return gifDrawable.isRunning();
        }
        return true;
    }

    public static void loadCharacterImages(String character) {
        Game.endImgInit = false;
        switch (character) {
            case "saturn":
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
                                          Game.endImgInit = true;
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

                buckshotImg = null;
                playerImage = null;

                emeraldImg = null;
                dynamiteImg = null;
                thunderImage = new Bitmap[NUMBER_LIGHTNING_IMAGES];
                break;
            case "falcon":
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
                                          Game.endImgInit = true;
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

                bulletBuckshotSaturnImg = null;
                saturnImg = null;
                bulletSaturnImg = null;

                emeraldImg = null;
                dynamiteImg = null;
                thunderImage = new Bitmap[NUMBER_LIGHTNING_IMAGES];
                break;
            case "emerald":
                requestBuilder.load(R.drawable.emerald)
                        .override(eX152, eX169)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          emeraldImg = resource;
                                          Game.endImgInit = true;
                                          return true;
                                      }
                                  }
                        ).submit();
                requestBuilder.load(R.drawable.dynamite)
                        .override(eX100, (int) (41.3 * resizeK))
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                          dynamiteImg = resource;
                                          return true;
                                      }
                                  }
                        ).submit();
                for (int i = 0; i < 13; i++) {
                    int finalI = i;
                    requestBuilder.load(res.getIdentifier("laser" + i, "drawable", name))
                            .override(eX3545, screenHeight)
                            .listener(new RequestListener<Bitmap>() {
                                          @Override
                                          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                              return false;
                                          }

                                          @Override
                                          public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                              thunderImage[finalI] = resource;
                                              return true;
                                          }
                                      }
                            ).submit();
                }

                bulletBuckshotSaturnImg = null;
                saturnImg = null;
                bulletSaturnImg = null;

                buckshotImg = null;
                playerImage = null;
                break;
        }
    }

    public static void loadSettingsImages(Context context) {
        if (onImg == null) {
            GlideApp.with(context)
                    .asDrawable()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(R.drawable.on)
                    .override(eX100, (int) (resizeK * 82))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            onImg = resource;
                            return false;
                        }

                    }).submit();

            GlideApp.with(context)
                    .asDrawable()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(R.drawable.off)
                    .override(eX100, (int) (resizeK * 96))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            offImg = resource;
                            return true;
                        }
                    }).submit();

            GlideApp.with(context)
                    .asDrawable()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(R.drawable.en)
                    .override(eX200, eX100)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            enImg = resource;
                            return true;
                        }
                    }).submit();

            ruImg = new BitmapDrawable(res, Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(res, R.drawable.ru), eX200, eX100, true));
            frImg = new BitmapDrawable(res, Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(res, R.drawable.fr), eX200, eX100, true));
            spImg = new BitmapDrawable(res, Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(res, R.drawable.sp), eX200, eX100, true));
            geImg = new BitmapDrawable(res, Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(res, R.drawable.ge), eX200, eX100, true));
        }
    }

    public static void deleteSettingsImages() {
        if (onImg != null) {
            onImg = null;
            offImg = null;
            ruImg = null;
            enImg = null;
            frImg = null;
            spImg = null;
            geImg = null;
        }
    }

    public static Bitmap rotateImage(Bitmap image, float degree) {
        matrix.reset();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
    }

    public static Bitmap resizeImage(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}
