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
import static ru.warfare.darkannihilation.systemd.service.UriManager.*;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;
import static ru.warfare.darkannihilation.systemd.service.Windows.calculate;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.gif.GifDrawable;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.glide.GlideManager;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.service.Service;
import ru.warfare.darkannihilation.systemd.service.Time;
import ru.warfare.darkannihilation.thread.HardThread;

public final class ImageHub {
    public static final Bitmap[] explosionTripleImageSmall = new Bitmap[NUMBER_TRIPLE_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionTripleImageMedium = new Bitmap[NUMBER_TRIPLE_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionDefaultImageSmall = new Bitmap[NUMBER_DEFAULT_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionDefaultImageMedium = new Bitmap[NUMBER_DEFAULT_EXPLOSION_IMAGES];
    public static final Bitmap[] explosionLarge = new Bitmap[NUMBER_SKULL_EXPLOSION_IMAGES];

    public static Bitmap[] screenImage = new Bitmap[NUMBER_STAR_SCREEN_IMAGES];
    public static Bitmap[] vaderImages = new Bitmap[NUMBER_VADER_IMAGES];
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

    public static int _75;
    public static int _70;
    public static int _300;
    public static int _350;
    public static int screenHeight_1_05;
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
    private static int _7;

    private static final GlideManager glideManager = new GlideManager();
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static volatile boolean endImgInit = false;

    public static void init() {
        _50 = calculate(50);
        _15 = calculate(15);
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
        _7 = calculate(7);
        _80 = calculate(80);
        _70 = calculate(70);
        _166 = calculate(166);
        _175 = calculate(175);
        _314 = calculate(314);
        _03545 = (int) (0.3545 * SCREEN_HEIGHT);
        fact = (int) (SCREEN_WIDTH / 1.3);
        fact03 = (int) (fact * 0.3);
        screenWidth_135 = (int) (SCREEN_WIDTH * 1.35);
        screenHeight_1_05 = (int) (SCREEN_HEIGHT * 1.05263);

        glideManager.run(R.drawable.cannon_ball, _15, object -> buckshotImg = object);
        glideManager.run(R.drawable.pause_button, calculate(180), object -> pauseButtonImg = object);
        glideManager.run(R.drawable.bullet, _7, _30, object -> bulletImage = object);
        glideManager.run(R.drawable.health, _80, object -> healthKitImg = object);
        glideManager.run(R.drawable.buckshot, _100, calculate(123.7), object -> shotgunKitImg = object);
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

        int _522 = calculate(522);
        int _600 = calculate(600);
        int _144 = calculate(144);
        int _154 = calculate(154);
        Uri uri;
        for (int i = 0; i < NUMBER_STAR_SCREEN_IMAGES; i++) {
            final int finalI = i;

            if (i < NUMBER_VADER_IMAGES) {
                glideManager.run(vader(i), _75, object -> vaderImages[finalI] = object);
            }
            if (i < NUMBER_LOADING_SCREEN_IMAGES) {
                glideManager.runCrop(loadingScreen(i), SCREEN_WIDTH, SCREEN_HEIGHT, object -> loadingImages[finalI] = object);
            }
            if (i < NUMBER_SKULL_EXPLOSION_IMAGES) {
                glideManager.run(skullExplosion(i), _522, _600, object -> explosionLarge[finalI] = object);
            }
            if (i < NUMBER_TRIPLE_EXPLOSION_IMAGES) {
                uri = tripleExplosion(i);
                glideManager.run(uri, _150, _144, object -> explosionTripleImageMedium[finalI] = object);
                glideManager.run(uri, _50, object -> explosionTripleImageSmall[finalI] = object);
            }
            if (i < NUMBER_DEFAULT_EXPLOSION_IMAGES) {
                uri = defaultExplosion(i);
                glideManager.run(uri, _150, _154, object -> explosionDefaultImageMedium[finalI] = object);
                glideManager.run(uri, _50, object -> explosionDefaultImageSmall[finalI] = object);
            }

            glideManager.run(starScreen(i), screenWidth_135, SCREEN_HEIGHT, object -> {
                screenImage[finalI] = object;
                if (finalI == 33) {
                    endImgInit = true;
                }
            });
        }
    }

    public static void loadPortalImages() {
        if (portalImages[0] == null) {
            for (int i = 0; i < NUMBER_PORTAL_IMAGES; i++) {
                final int finalI = i;
                glideManager.run(portal(i), _300, object -> portalImages[finalI] = object);
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

        vaderImages = new Bitmap[NUMBER_VADER_IMAGES];

        glideManager.run(R.drawable.spider, _350, _175, object -> spiderImg = object);
        glideManager.run(R.drawable.x_wing, _200, _150, object -> XWingImg = object);
        glideManager.run(R.drawable.area, calculate(450), calculate(269), object -> sunriseImg = object);
        glideManager.run(R.drawable.boss_vaders, _350, calculate(255), object -> bossVadersImg = object);
        glideManager.run(R.drawable.bull_boss_vader, _150, object -> bulletBossVadersImg = object);
        glideManager.run(R.drawable.buffer, _400, _350, object -> bufferImg = object);

        for (int i = 0; i < NUMBER_THUNDER_SCREEN_IMAGES; i++) {
            final int finalI = i;

            if (i < NUMBER_VADER_IMAGES) {
                glideManager.run(vaderOld(i), _75, object -> vaderImages[finalI] = object);
            }
            if (i < NUMBER_ATOMIC_BOMB_IMAGES) {
                glideManager.run(atomicBomb(i), _100, _300, object -> atomBombImage[finalI] = object);
            }
            glideManager.runCrop(thunderScreen(i), screenWidth_135, SCREEN_HEIGHT, object -> {
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
            for (int i = 0; i < NUMBER_THUNDER_SCREEN_IMAGES; i++) {
                thunderScreen[i].recycle();
                if (i < NUMBER_ATOMIC_BOMB_IMAGES) {
                    atomBombImage[i].recycle();
                }
            }
            thunderScreen = new Bitmap[NUMBER_THUNDER_SCREEN_IMAGES];
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

        loadCharacterImages(character, !needImagesForFirstLevel());

        if (needImagesForFirstLevel()) {
            vaderImages = new Bitmap[NUMBER_VADER_IMAGES];

            loadFirstLevelBitmaps();

            for (int i = 0; i < NUMBER_STAR_SCREEN_IMAGES; i++) {
                int finalI = i;

                if (i < NUMBER_VADER_IMAGES) {
                    glideManager.run(vader(i), _75, object -> vaderImages[finalI] = object);
                }
                stringBuilder.setLength(0);
                stringBuilder.append("_").append(i);
                glideManager.run(starScreen(i), screenWidth_135, SCREEN_HEIGHT, object -> {
                    screenImage[finalI] = object;
                    if (finalI == 33) {
                        endImgInit = true;
                    }
                });
            }
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
            for (int i = 0; i < NUMBER_STAR_SCREEN_IMAGES; i++) {
                screenImage[i].recycle();
            }
            screenImage = new Bitmap[NUMBER_STAR_SCREEN_IMAGES];

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

    public static void loadWinImages(Game game) {
        glideManager.runGif(R.drawable.win, SCREEN_WIDTH, SCREEN_HEIGHT, drawable -> {
            GifDrawable gif = (GifDrawable) drawable;
            gif.setLoopCount(1);
            game.setBackground(gif);
            game.generateWin();
            AudioHub.playFlightSnd();
            gif.start();

            HardThread.doInPool(() -> {
                Time.sleep(4000);
                while (gif.isRunning()) {
                    Time.relax();
                }

                Service.runOnUiThread(() -> game.setBackground(null));
                Game.gameStatus = WIN;
            });
        }, true);
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
            glideManager.run(R.drawable.bullet, _7, _30, object -> bulletImage = object);

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
        glideManager.run(R.drawable.dynamite, _100, calculate(41.3), object -> dynamiteImg = object);

        for (int i = 0; i < NUMBER_LIGHTNING_IMAGES; i++) {
            int finalI = i;
            glideManager.run(laser(i), _03545, SCREEN_HEIGHT, object -> thunderImage[finalI] = object);
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
        endImgInit = false;
        Uri uri = null;

        for (int i = 0; i < NUMBER_VADER_IMAGES; i++) {
            int finalI = i;

            switch (character) {
                case SATURN:
                    uri = saturnGuns(i);
                    break;
                case MILLENNIUM_FALCON:
                    uri = millennuimGuns(i);
                    break;
                case EMERALD:
                    uri = emeraldGuns(i);
                    break;
            }
            glideManager.run(uri, _400, _314, object -> {
                gunsImage[finalI] = object;

                if (finalI == 2) {
                    endImgInit = true;
                }
            });
        }
    }

    public static void loadFightScreen(byte character, byte boss) {
        endImgInit = false;

        int id = 0;
        switch (character) {
            case SATURN:
                if (boss == DEATH_STAR) {
                    id = R.drawable.saturn_vs_boss;
                } else {
                    id = R.drawable.saturn_vs_vader;
                }
                break;
            case MILLENNIUM_FALCON:
                if (boss == DEATH_STAR) {
                    id = R.drawable.player_vs_boss;
                } else {
                    id = R.drawable.player_vs_vader;
                }
                break;
            case EMERALD:
                if (boss == DEATH_STAR) {
                    id = R.drawable.emerald_vs_boss;
                } else {
                    id = R.drawable.emerald_vs_vader;
                }
                break;
        }
        fightScreen(id);

        Time.waitImg();
    }

    private static void fightScreen(int id) {
        glideManager.run(id, screenHeight_1_05, SCREEN_HEIGHT, object -> {
            fightScreen = object;
            endImgInit = true;
        });
    }

    public static void deleteFightScreen() {
        fightScreen = null;
    }

    public static void loadSettingsImages() {
        if (onImg == null) {
            endImgInit = false;

            glideManager.runDrawable(R.drawable.on, _100, _80, object -> onImg = object);
            glideManager.runDrawable(R.drawable.off, _100, _100, object -> offImg = object);
            glideManager.runDrawable(R.drawable.en, _200, _100, object -> enImg = object);
            glideManager.runDrawable(R.drawable.ru, _200, _100, object -> ruImg = object);
            glideManager.runDrawable(R.drawable.fr, _200, _100, object -> frImg = object);
            glideManager.runDrawable(R.drawable.sp, _200, _100, object -> spImg = object);
            glideManager.runDrawable(R.drawable.ge, _200, _100, object -> {
                geImg = object;
                endImgInit = true;
            });

            Time.waitImg();
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
        return Bitmap.createBitmap(image, 0, 0, width, height, matrix, true);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}
