package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_ATOMIC_BOMB_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_LIGHTNING_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_LOADING_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SKULL_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_STAR_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_THUNDER_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_VADER_IMAGES;
import static ru.warfare.darkannihilation.constant.Modes.WIN;
import static ru.warfare.darkannihilation.constant.NamesConst.DEATH_STAR;
import static ru.warfare.darkannihilation.constant.NamesConst.EMERALD;
import static ru.warfare.darkannihilation.constant.NamesConst.MILLENNIUM_FALCON;
import static ru.warfare.darkannihilation.constant.NamesConst.SATURN;
import static ru.warfare.darkannihilation.systemd.service.Py.print;
import static ru.warfare.darkannihilation.systemd.service.Service.packageName;
import static ru.warfare.darkannihilation.systemd.service.Service.resources;
import static ru.warfare.darkannihilation.systemd.service.Windows.DENSITY;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import pl.droidsonroids.gif.GifDrawable;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.glide.GlideManager;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.MainActivity;
import ru.warfare.darkannihilation.systemd.service.Service;
import ru.warfare.darkannihilation.systemd.service.Time;

public final class ImageHub {
    public static final Bitmap[] explosionTripleImageSmall = new Bitmap[NUMBER_TRIPLE_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionTripleImageMedium = new Bitmap[NUMBER_TRIPLE_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionDefaultImageSmall = new Bitmap[NUMBER_DEFAULT_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionDefaultImageMedium = new Bitmap[NUMBER_DEFAULT_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionLarge = new Bitmap[NUMBER_SKULL_EXPLOSION_IMAGES];

    public static Bitmap[] screenImage = new Bitmap[NUMBER_STAR_SCREEN_IMAGES];
    public static Bitmap[] vaderImage = new Bitmap[NUMBER_VADER_IMAGES];
    public static Bitmap[] vaderOldImage = new Bitmap[NUMBER_VADER_IMAGES];
    public static Bitmap[] portalImages = new Bitmap[NUMBER_PORTAL_IMAGES];
    public static Bitmap[] thunderScreen = new Bitmap[NUMBER_THUNDER_SCREEN_IMAGES];
    public static Bitmap[] atomBombImage = new Bitmap[NUMBER_ATOMIC_BOMB_IMAGES];
    public static final Bitmap[] loadingImages = new Bitmap[NUMBER_LOADING_SCREEN_IMAGES];
    public static Bitmap[] thunderImage = new Bitmap[NUMBER_LIGHTNING_IMAGES];
    public static Bitmap[] gunsImage = new Bitmap[NUMBER_VADER_IMAGES];

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
    public static Bitmap fightScreen;
    public static Bitmap healthKitImg;
    public static Bitmap shotgunKitImg;
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

    public static int _75;
    public static int _70;
    public static int _300;
    public static int _350;
    public static int _095;
    public static int _150;
    private static int _120;
    private static int _03545;
    private static int _166;
    private static int screenWidth_135;
    private static int _50;
    private static int _60;
    private static int _100;
    private static int _15;
    private static int fact;
    private static int fact03;
    private static int _200;
    private static int _80;
    private static int _175;
    private static int _400;
    private static int _30;
    private static int _314;

    private static final GlideManager glideManager = new GlideManager();
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static volatile boolean endImgInit = false;

    public static void init() {
        _50 = (int) (50 * DENSITY);
        _15 = (int) (15 * DENSITY);
        _30 = _15 * 2;
        _150 = _50 * 3;
        _100 = _50 * 2;
        _200 = _100 * 2;
        _300 = _100 * 3;
        _350 = _50 * 7;
        _60 = _30 * 2;
        _75 = _15 * 5;
        _120 = _60 * 2;
        _400 = _200 * 2;
        _80 = (int) (80 * DENSITY);
        _70 = (int) (70 * DENSITY);
        _166 = (int) (166 * DENSITY);
        _175 = (int) (175 * DENSITY);
        _314 = (int) (314 * DENSITY);
        _03545 = (int) (0.3545 * SCREEN_HEIGHT);
        fact = (int) ((SCREEN_WIDTH / 1.3) * DENSITY);
        fact03 = (int) (fact * 0.3);
        screenWidth_135 = (int) (SCREEN_WIDTH * 1.35);
        _095 = (int) (SCREEN_WIDTH * 0.95);

        glideManager.run(R.drawable.cannon_ball, _15, object -> buckshotImg = object);
        glideManager.run(R.drawable.pause_button, (int) (180 * DENSITY), object -> pauseButtonImg = object);
        glideManager.run(R.drawable.bullet, (int) (7 * DENSITY), _30, object -> bulletImage = object);
        glideManager.run(R.drawable.health, _80, object -> healthKitImg = object);
        glideManager.run(R.drawable.buckshot, _100, (int) (123.7 * DENSITY), object -> shotgunKitImg = object);
        glideManager.runCrop(R.drawable.gameover, SCREEN_WIDTH, SCREEN_HEIGHT, object -> gameoverScreen = object);
        glideManager.run(R.drawable.bullet_enemy, _15, _50, object -> bulletEnemyImage = object);
        glideManager.run(R.drawable.full_blue_heart, _70, _60, object -> imageBlueHeartFull = object);
        glideManager.run(R.drawable.half_blue_heart, _70, _60, object -> imageBlueHeartHalf = object);
        glideManager.run(R.drawable.full_heart, _70, _60, object -> imageHeartFull = object);
        glideManager.run(R.drawable.half_heart, _70, _60, object -> imageHeartHalf = object);
        glideManager.run(R.drawable.non_heart, _70, _60, object -> imageHeartNon = object);
        glideManager.run(R.drawable.ship_button, _100, _120, object -> buttonPlayerImg = object);
        glideManager.run(R.drawable.ship, _100, _120, object -> playerImage = object);
        glideManager.run(R.drawable.saturn_btn, _100, _200, object -> buttonSaturnImg = object);
        glideManager.run(R.drawable.emerald_btn, _150, _166, object -> buttonEmeraldImg = object);
        glideManager.run(R.drawable.button_press, _300, _70, object -> buttonImagePressed = object);
        glideManager.run(R.drawable.button_notpress, _300, _70, object -> buttonImageNotPressed = object);

        loadFirstLevelBitmaps();
        loadSettingsImages();

        int _522 = (int) (522 * DENSITY);
        int _600 = (int) (600 * DENSITY);
        int _144 = (int) (144 * DENSITY);
        int _154 = (int) (154 * DENSITY);
        for (int i = 0; i < 34; i++) {
            final int finalI = i;
            final int i1 = i + 1;

            if (i < 3) {
                stringBuilder.setLength(0);
                stringBuilder.append("vader").append(i1);
                glideManager.run(getId(), _75, object -> vaderImage[finalI] = object);
            }
            if (i < NUMBER_LOADING_SCREEN_IMAGES) {
                stringBuilder.setLength(0);
                stringBuilder.append("loading").append(i);
                glideManager.runCrop(getId(), SCREEN_WIDTH, SCREEN_HEIGHT, object -> loadingImages[finalI] = object);
            }
            if (i < NUMBER_SKULL_EXPLOSION_IMAGES) {
                stringBuilder.setLength(0);
                stringBuilder.append("explosion_skull_").append(i);
                glideManager.run(getId(), _522, _600, object -> explosionLarge[finalI] = object);
            }
            if (i < NUMBER_TRIPLE_EXPLOSION_IMAGES) {
                stringBuilder.setLength(0);
                stringBuilder.append("explosion_").append(i);
                glideManager.run(getId(), _150, _144, object -> explosionTripleImageMedium[finalI] = object);
                glideManager.run(getId(), _50, object -> explosionTripleImageSmall[finalI] = object);
            }
            if (i < NUMBER_DEFAULT_EXPLOSION_IMAGES) {
                stringBuilder.setLength(0);
                stringBuilder.append("default_explosion_").append(i);
                glideManager.run(getId(), _150, _154, object -> explosionDefaultImageMedium[finalI] = object);
                glideManager.run(getId(), _50, object -> explosionDefaultImageSmall[finalI] = object);
            }

            stringBuilder.setLength(0);
            stringBuilder.append("_").append(i);
            glideManager.run(getId(), screenWidth_135, SCREEN_HEIGHT, object -> {
                screenImage[finalI] = object;
                if (finalI > 31) {
                    endImgInit = true;
                }
            });
        }
    }

    public static void loadPortalImages() {
        if (portalImages[0] == null) {
            for (int i = 0; i < 20; i++) {
                final int finalI = i;

                stringBuilder.setLength(0);
                stringBuilder.append("portal0").append(i);
                glideManager.run(getId(), _300, object -> portalImages[finalI] = object);
            }
        }
    }

    public static void deletePortalImages() {
        if (portalImages[0] != null) {
            portalImages = new Bitmap[NUMBER_PORTAL_IMAGES];
        }
    }

    public static void loadSecondLevelImages() {
        endImgInit = false;

        glideManager.run(R.drawable.spider, _350, _175, object -> spiderImg = object);
        glideManager.run(R.drawable.x_wing, _200, _150, object -> XWingImg = object);
        glideManager.run(R.drawable.area, (int) (450 * DENSITY), (int) (269 * DENSITY), object -> sunriseImg = object);
        glideManager.run(R.drawable.boss_vaders, _350, (int) (255 * DENSITY), object -> bossVadersImg = object);
        glideManager.run(R.drawable.bull_boss_vader, _150, object -> bulletBossVadersImg = object);
        glideManager.run(R.drawable.buffer, _400, _350, object -> bufferImg = object);

        for (int i = 0; i < 20; i++) {
            final int finalI = i;
            final int i1 = i + 1;

            if (i < 3) {
                stringBuilder.setLength(0);
                stringBuilder.append("vader1").append(i1);
                glideManager.run(getId(), _75, object -> vaderOldImage[finalI] = object);
            }
            if (i < 4) {
                stringBuilder.setLength(0);
                stringBuilder.append("atom").append(i);
                glideManager.run(getId(), _100, _300, object -> atomBombImage[finalI] = object);
            }
            stringBuilder.setLength(0);
            stringBuilder.append("thunder").append(i);
            glideManager.runCrop(getId(), screenWidth_135, SCREEN_HEIGHT, object -> {
                thunderScreen[finalI] = object;
                if (finalI == 18) {
                    endImgInit = true;
                }
            });
        }

        Time.waitImg();
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

    public static void loadFirstLevelAndCharacterImages(byte character) {
        endImgInit = false;

        if (needImagesForFirstLevel()) {
            loadFirstLevelBitmaps();
            loadCharacterImages(character, false);

            for (int i = 0; i < 34; i++) {
                final int finalI = i;
                final int i1 = i + 1;

                if (i < 3) {
                    stringBuilder.setLength(0);
                    stringBuilder.append("vader").append(i1);
                    glideManager.run(getId(), _75, object -> vaderImage[finalI] = object);
                }
                stringBuilder.setLength(0);
                stringBuilder.append("_").append(i);
                glideManager.run(getId(), screenWidth_135, SCREEN_HEIGHT, object -> {
                    screenImage[finalI] = object;
                    if (finalI == 33) {
                        endImgInit = true;
                    }
                });
            }
        } else {
            loadCharacterImages(character, true);
        }

        Time.waitImg();
    }

    private static void loadFirstLevelBitmaps() {
        glideManager.run(R.drawable.triple_fighter, _100, object -> tripleFighterImg = object);
        glideManager.run(R.drawable.boss, _400, object -> bossImage = object);
        glideManager.run(R.drawable.minion, _80, _50, object -> minionImg = object);
        glideManager.run(R.drawable.demoman, _300, _175, object -> demomanImg = object);
        glideManager.run(R.drawable.laser, _15, _60, object -> laserImage = object);
        glideManager.run(R.drawable.rocket, _50, _100, object -> rocketImg = object);
        glideManager.run(R.drawable.attention, _70, object -> attentionImg = object);
        glideManager.run(R.drawable.factory, fact, fact03, object -> factoryImg = object);
        glideManager.run(R.drawable.bomb, _30, _70, object -> bombImg = object);
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
        try {
            gifDrawable = new GifDrawable(resources, R.drawable.win);
            gifDrawable.setLoopCount(1);
            gifDrawable.setSpeed(0.5f);
            mainActivity.newWinGif(gifDrawable);
            gifDrawable.start();
        } catch (Exception e) {
            print(e);
        }
        mainActivity.game.generateWin();
        AudioHub.playFlightSnd();
        HardThread.doInBackGround(() -> {
            Time.sleep(4000);
            while (gifDrawable.isRunning()) {
                Time.sleep(250);
            }

            Service.runOnUiThread(mainActivity::deleteWinGif);
            gifDrawable = null;
            Game.gameStatus = WIN;
        });
    }

    public static void loadCharacterImages(int character, boolean needWait) {
        switch (character) {
            case SATURN:
                loadSaturn(needWait);
                break;
            case MILLENNIUM_FALCON:
                loadMillennium(needWait);
                break;
            case EMERALD:
                loadEmerald(needWait);
                break;
        }
    }

    private static void loadSaturn(boolean needWait) {
        glideManager.run(R.drawable.saturn, _100, _200, object -> {
            saturnImg = object;
            if (needWait) {
                endImgInit = true;
            }
        });
        glideManager.run(R.drawable.saturn_bullet, _15, object -> bulletSaturnImg = object);
        glideManager.run(R.drawable.buckshot_saturn, _15, object -> bulletBuckshotSaturnImg = object);

        deleteMillennium();
        deleteEmerald();
    }

    private static void deleteSaturn() {
        if (saturnImg != null) {
            saturnImg = null;
            bulletBuckshotSaturnImg = null;
            bulletSaturnImg = null;
        }
    }

    private static void loadMillennium(boolean needWait) {
        if (playerImage == null) {
            glideManager.run(R.drawable.ship, _100, _120, object -> {
                playerImage = object;
                if (needWait) {
                    endImgInit = true;
                }
            });

            glideManager.run(R.drawable.cannon_ball, _15, object -> buckshotImg = object);
            glideManager.run(R.drawable.bullet, (int) (7 * DENSITY), _30, object -> bulletImage = object);

            deleteSaturn();
            deleteEmerald();
        } else {
            if (needWait) {
                endImgInit = true;
            }
        }
    }

    private static void deleteMillennium() {
        if (playerImage != null) {
            playerImage = null;
            buckshotImg = null;
            bulletImage = null;
        }
    }

    private static void loadEmerald(boolean needWait) {
        glideManager.run(R.drawable.emerald, _150, _166, object -> {
            emeraldImg = object;
            if (needWait) {
                endImgInit = true;
            }
        });
        glideManager.run(R.drawable.dynamite, _100, (int) (41.3 * DENSITY), object -> dynamiteImg = object);

        for (int i = 0; i < NUMBER_LIGHTNING_IMAGES; i++) {
            int finalI = i;
            stringBuilder.setLength(0);
            stringBuilder.append("laser").append(i);
            glideManager.run(getId(), _03545, SCREEN_HEIGHT, object -> thunderImage[finalI] = object);
        }

        deleteSaturn();
        deleteMillennium();
    }

    private static void deleteEmerald() {
        if (emeraldImg != null) {
            emeraldImg = null;
            dynamiteImg = null;
            thunderImage = new Bitmap[NUMBER_LIGHTNING_IMAGES];
        }
    }

    public static void loadGunsImages(byte character) {
        String str = "";
        switch (character) {
            case SATURN:
                str = "saturn";
                break;
            case MILLENNIUM_FALCON:
                str = "millennium";
                break;
            case EMERALD:
                str = "emerald";
                break;
        }

        for (int i = 0; i < NUMBER_VADER_IMAGES; i++) {
            int finalI = i;
            stringBuilder.setLength(0);
            stringBuilder.append(str).append("_guns").append(i);
            glideManager.run(getId(), _400, _314, object -> gunsImage[finalI] = object);
        }
    }

    public static void loadFightScreen(byte character, byte boss) {
        endImgInit = false;
        switch (character) {
            case SATURN:
                if (boss == DEATH_STAR) {
                    glideManager.run(R.drawable.saturn_vs_boss, SCREEN_WIDTH, _095, object -> {
                        fightScreen = object;
                        endImgInit = true;
                    });
                } else {
                    glideManager.run(R.drawable.saturn_vs_vader, SCREEN_WIDTH, _095, object -> {
                        fightScreen = object;
                        endImgInit = true;
                    });
                }
                break;
            case MILLENNIUM_FALCON:
                if (boss == DEATH_STAR) {
                    glideManager.run(R.drawable.player_vs_boss, SCREEN_WIDTH, _095, object -> {
                        fightScreen = object;
                        endImgInit = true;
                    });
                } else {
                    glideManager.run(R.drawable.player_vs_vader, SCREEN_WIDTH, _095, object -> {
                        fightScreen = object;
                        endImgInit = true;
                    });
                }
                break;
            case EMERALD:
                if (boss == DEATH_STAR) {
                    glideManager.run(R.drawable.emerald_vs_boss, SCREEN_WIDTH, _095, object -> {
                        fightScreen = object;
                        endImgInit = true;
                    });
                } else {
                    glideManager.run(R.drawable.emerald_vs_vader, SCREEN_WIDTH, _095, object -> {
                        fightScreen = object;
                        endImgInit = true;
                    });
                }
                break;
        }
    }

    public static void deleteFightScreen() {
        fightScreen = null;
    }

    public static void loadSettingsImages() {
        if (onImg == null) {
            glideManager.runDrawable(R.drawable.on, _100, _80, object -> onImg = object);
            glideManager.runDrawable(R.drawable.off, _100, _100, object -> offImg = object);
            glideManager.runDrawable(R.drawable.en, _200, _100, object -> enImg = object);
            glideManager.runDrawable(R.drawable.ru, _200, _100, object -> ruImg = object);
            glideManager.runDrawable(R.drawable.fr, _200, _100, object -> frImg = object);
            glideManager.runDrawable(R.drawable.sp, _200, _100, object -> spImg = object);
            glideManager.runDrawable(R.drawable.ge, _200, _100, object -> geImg = object);
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

    private static int getId() {
        return resources.getIdentifier(stringBuilder.toString(), "drawable", packageName);
    }

    public static Bitmap rotateBitmap(Bitmap image, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
    }

    public static Bitmap mirrorImage(@NonNull Bitmap image, boolean horizontal) {
        Matrix matrix = new Matrix();
        int width = image.getWidth();
        int height = image.getHeight();

        if (horizontal) {
            matrix.postScale(-1, 1, width / 2f, height / 2f);
        } else {
            matrix.postScale(1, -1, width / 2f, height / 2f);
        }
        return Bitmap.createBitmap(image, 0, 0, width,height, matrix, true);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}
