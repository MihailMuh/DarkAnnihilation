package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import pl.droidsonroids.gif.GifImageView;

public final class ImageHub {
    public static final boolean isFilter = true;

    public static final Bitmap[] explosionTripleImageSmall = new Bitmap[23];
    public static final Bitmap[] explosionTripleImageMedium = new Bitmap[23];
    public static final Bitmap[] explosionDefaultImageSmall = new Bitmap[28];
    public static final Bitmap[] explosionDefaultImageMedium = new Bitmap[28];
    public static final Bitmap[] explosionLarge = new Bitmap[13];

    public static Bitmap[] screenImage = new Bitmap[34];
    public static Bitmap[] vaderImage = new Bitmap[3];
    public static Bitmap[] vaderOldImage = new Bitmap[3];
    public static final Bitmap[] portalImages = new Bitmap[20];
    public static Bitmap[] thunderScreen = new Bitmap[20];
    public static final Bitmap[] loadingImages = new Bitmap[12];

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
    public static Bitmap bufferImg;
    public static Drawable onImg;
    public static Drawable offImg;
    public static Drawable enImg;
    public static Drawable ruImg;
    public static Drawable frImg;
    public static Drawable spImg;
    public static Drawable geImg;

    private static final int screenWidth = Service.getScreenWidth();
    private static final int screenHeight = Service.getScreenHeight();
    private static final double resizeK = Service.getResizeCoefficient();

    public static final int eX75 = (int) (75 * resizeK);
    public static final int eX70 = (int) (70 * resizeK);
    public static final int eX300 = (int) (300 * resizeK);
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
    private static Resources res;
    private static String name;

    public static void init(Context context) {
        int eX145 = (int) (145 * resizeK);
        int eX152 = (int) (152 * resizeK);
        int eX52 = (int) (52 * resizeK);
        int eX500 = (int) (500 * resizeK);
        int eX435 = (int) (435 * resizeK);
        int sW150 = (int) ((screenWidth - 150) * resizeK);
        int sW = (int) (screenWidth * resizeK);
        int eX13 = (int) (13 * resizeK);
        int eX207 = (int) (207 * resizeK);
        int eX120 = (int) (120 * resizeK);
        int pauseBtn = (int) (150 * Service.getResizeCoefficientForLayout());
        res = context.getResources();
        name = context.getPackageName();

        requestBuilder =
                GlideApp.with(context)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

        loadFirstLevelBitmaps();
        loadLayoutImages(context);

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

    public static void deletePortalImages() {
        if (portalImages[0] != null) {
            for (int i = 0; i < 20; i++) {
                portalImages[i].recycle();
            }
        }
    }

    public static void loadSecondLevelImages(Context context) {
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

    public static void deleteSecondLevelImages() {
        if (thunderScreen[0] != null) {
            for (int i = 0; i < 20; i++) {
                thunderScreen[i].recycle();
                if (i < 3) {
                    vaderOldImage[i].recycle();
                }
            }
            thunderScreen = new Bitmap[20];
            vaderOldImage = new Bitmap[3];

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

    public static void loadFirstLevelImages(Context context) {
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
            screenImage = new Bitmap[34];
            vaderImage = new Bitmap[3];

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

    public static void loadWinImages(Context context) {
        MainActivity.handler.post(() ->
                GlideApp.with(context)
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
                                MainActivity.gif.setVisibility(GifImageView.VISIBLE);
                                resource.setLoopCount(1);
                                Game.lastBoss = System.currentTimeMillis();
                                Game.endImgInit = true;
                                return false;
                            }
                        })
                        .into(MainActivity.gif));
    }

    public static void deleteWinImages() {
        MainActivity.handler.post(() -> MainActivity.gif.setImageDrawable(null));
    }

    public static void loadLayoutImages(Context context) {
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

    public static void deleteLayoutImages() {
        onImg = null;
        offImg = null;
        ruImg = null;
        enImg = null;
        frImg = null;
        spImg = null;
        geImg = null;
    }

    public static Bitmap rotateImage(Bitmap image, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, isFilter);
    }

    public static Bitmap resizeImage(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}
