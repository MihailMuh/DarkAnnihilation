package ru.warfare.darkannihilation.systemd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import ru.warfare.darkannihilation.Clerk;
import ru.warfare.darkannihilation.ClientServer;
import ru.warfare.darkannihilation.interfaces.Function;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.Table;
import ru.warfare.darkannihilation.Windows;
import ru.warfare.darkannihilation.audio.GameOver;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.BaseExplosion;
import ru.warfare.darkannihilation.base.BaseScreen;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletEnemyOrbit;
import ru.warfare.darkannihilation.button.*;
import ru.warfare.darkannihilation.character.Bot;
import ru.warfare.darkannihilation.character.Emerald;
import ru.warfare.darkannihilation.character.MillenniumFalcon;
import ru.warfare.darkannihilation.character.Saturn;
import ru.warfare.darkannihilation.enemy.AtomicBomb;
import ru.warfare.darkannihilation.enemy.Attention;
import ru.warfare.darkannihilation.enemy.Buffer;
import ru.warfare.darkannihilation.enemy.Demoman;
import ru.warfare.darkannihilation.enemy.Factory;
import ru.warfare.darkannihilation.enemy.Portal;
import ru.warfare.darkannihilation.enemy.Rocket;
import ru.warfare.darkannihilation.enemy.Spider;
import ru.warfare.darkannihilation.enemy.Sunrise;
import ru.warfare.darkannihilation.enemy.TripleFighter;
import ru.warfare.darkannihilation.enemy.Vader;
import ru.warfare.darkannihilation.enemy.XWing;
import ru.warfare.darkannihilation.enemy.boss.BossVaders;
import ru.warfare.darkannihilation.enemy.boss.DeathStar;
import ru.warfare.darkannihilation.explosion.*;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.screen.*;
import ru.warfare.darkannihilation.support.*;

import static ru.warfare.darkannihilation.constant.Constants.DRAW_FPS;
import static ru.warfare.darkannihilation.constant.Modes.*;
import static ru.warfare.darkannihilation.Py.print;
import static ru.warfare.darkannihilation.constant.NamesConst.BOSS_VADERS;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ORBIT;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_SATURN;
import static ru.warfare.darkannihilation.constant.NamesConst.DEATH_STAR;
import static ru.warfare.darkannihilation.constant.NamesConst.EMERALD;
import static ru.warfare.darkannihilation.constant.NamesConst.MILLENNIUM_FALCON;
import static ru.warfare.darkannihilation.constant.NamesConst.SATURN;

public final class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private final SurfaceHolder holder = getHolder();
    public MainActivity mainActivity;
    private Thread thread;
    public static Canvas canvas;

    public static int screenWidth;
    public static int screenHeight;
    public static int halfScreenWidth;
    public static int halfScreenHeight;
    public static float resizeK;

    public static final Paint fpsPaint = new Paint();
    public static final Paint startPaint = new Paint();
    public static final Paint gameoverPaint = new Paint();
    public static final Paint scorePaint = new Paint();
    public static final Paint topPaint = new Paint();
    public static final Paint topPaintRed = new Paint();
    public static final Paint alphaEnemy = new Paint();
    public static final Paint winPaint = new Paint();
    public static final Paint paint50 = new Paint();
    public static final Paint buttonsPaint = new Paint();

    public static final Random random = new Random();
    private static final StringBuilder textBuilder = new StringBuilder();

    public final BaseExplosion[] allExplosions = new BaseExplosion[73];
    public ArrayList<Sprite> bullets = new ArrayList<>(0);
    public ArrayList<Sprite> bosses = new ArrayList<>(0);
    public ArrayList<Sprite> allSprites = new ArrayList<>(0);

    public HardThread hardThread = new HardThread();
    public BaseScreen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public Button buttonMenu;
    public Button buttonRestart;
    public PauseButton pauseButton;
    public FightScreen fightScreen;
    public HealthKit healthKit;
    public ShotgunKit shotgunKit;
    public ChangerGuns changerGuns;
    public Rocket rocket;
    public Attention attention;
    public Factory factory;
    public Demoman demoman;
    public Portal portal;
    public ButtonPlayer buttonPlayer;
    public ButtonSaturn buttonSaturn;
    public ButtonEmerald buttonEmerald;
    public BaseCharacter player;
    public LoadingScreen loadingScreen;
    public Spider spider;
    public Sunrise sunrise;
    public Buffer buffer;
    public AtomicBomb atomicBomb;
    private Settings settings;

    public static final int numberVaders = 10;
    public static final int numberMediumExplosionsTriple = 20;
    public static final int numberSmallExplosionsTriple = 15 + numberMediumExplosionsTriple;
    public static final int numberMediumExplosionsDefault = numberSmallExplosionsTriple + 20;
    public static final int numberSmallExplosionsDefault = numberMediumExplosionsDefault + 15;
    public static final int numberExplosionsALL = 73;

    public static volatile byte level = 1;
    public static volatile byte gameStatus = MENU;
    private int count = 0;
    public static int score = 0;
    public int oldScore;
    public int bestScore = 0;
    private int pointerCount;
    private int moveAll;
    private volatile boolean isFirstRun = true;
    public volatile boolean playing = false;
    public static volatile byte character = MILLENNIUM_FALCON;
    public static volatile boolean endImgInit = false;
    public static volatile boolean vibrate;
    public String language = "en";

    private int fpsX;
    private int fpsY;
    private int scoreX;
    private int maxScoreX;
    private int chooseChX;
    private int chooseChY;
    private int thanksX;
    private int thanksY;
    private int go_to_menuX;
    private int go_to_menuY;
    private int go_to_restartX;
    private int go_to_restartY;
    private int shootY;
    private int shootX;
    private int buttonsY;
    private static int tableY;

    private String string_current_score;
    private String string_max_score;
    private String string_top;
    private String string_start;
    private String string_quit;
    private String string_settings;
    private String string_back;
    private String string_resume;
    private String string_restart;
    private String string_to_menu;
    private String string_choose_your_character;
    private String string_thanks;
    private String string_go_to_menu;
    private String string_shoot;
    private String string_go_to_restart;
    public static String string_volume;
    public static String string_loud_effects;
    public static String string_loud_music;
    public static String string_vibration;
    public static String string_enable;
    public static String string_disable;
    public static String string_choose_lang;
    public static String string_smooth;

    public int BOSS_TIME;
    public long lastBoss;
    public long pauseTimer;

    private static final int MILLIS_IN_SECOND = 1_000_000_000;
    private long timeFrame;

    public Game(Context mainActivity, AttributeSet attrs) {
        super(mainActivity, attrs);
    }

    public void init() {
        mainActivity = Service.getContext();
        screenWidth = Windows.screenWidth();
        screenHeight = Windows.screenHeight();
        halfScreenWidth = screenWidth / 2;
        halfScreenHeight = screenHeight / 2;
        resizeK = Windows.resizeK();

        holder.setFixedSize(screenWidth, screenHeight);

        buttonsY = (int) (screenHeight - (ImageHub._70 * 1.5));
        fpsY = 300;
        fpsX = screenWidth - 250;
        tableY = buttonsY - ImageHub._70;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setAntiAlias(true);

        fpsPaint.setColor(Color.RED);
        fpsPaint.setTextSize(40);

        startPaint.set(paint);
        startPaint.setTextSize(300);

        gameoverPaint.set(paint);

        scorePaint.set(paint);
        scorePaint.setTextSize(40);
        scorePaint.setFakeBoldText(true);

        topPaint.set(paint);
        topPaint.setTextSize(30);

        topPaintRed.set(topPaint);
        topPaintRed.setColor(Color.RED);

        winPaint.set(paint);
        winPaint.setTextSize(100);

        paint50.set(paint);
        paint50.setTextSize(50);

        buttonsPaint.set(paint);

        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        recoverySettings();

        while (!endImgInit) {
        }

        buttonMenu = new Button(this);
        buttonStart = new Button(this);
        buttonQuit = new Button(this);
        buttonRestart = new Button(this);

        HardThread.doInBackGround(() -> {
            buttonPlayer = new ButtonPlayer(this);
            buttonSaturn = new ButtonSaturn(this);
            buttonEmerald = new ButtonEmerald(this);
            pauseButton = new PauseButton(this);
            player = new Bot(this);
            healthKit = new HealthKit(this);
            shotgunKit = new ShotgunKit(this);
            changerGuns = new ChangerGuns(this);
            screen = new StarScreen();
            loadingScreen = new LoadingScreen(this);

            while (buttonStart.right() > buttonMenu.x) {
                buttonMenu.newFunc(string_top, halfScreenWidth, buttonsY, "top");
                buttonStart.newFunc(string_start, halfScreenWidth - buttonMenu.width, buttonsY, "start");
                buttonQuit.newFunc(string_quit, buttonStart.x - buttonStart.width, buttonsY, "quit");
                buttonRestart.newFunc(string_settings, buttonMenu.x + buttonQuit.width, buttonsY, "settings");
            }
        });

        for (int i = 0; i < numberVaders * 2; i++) {
            allSprites.add(new Vader(this, false));
        }

        for (int i = 0; i < numberExplosionsALL; i++) {
            if (i < numberMediumExplosionsTriple) {
                allExplosions[i] = new ExplosionTriple("default");
            } else {
                if (i < numberSmallExplosionsTriple) {
                    allExplosions[i] = new ExplosionTriple("small");
                } else {
                    if (i < numberMediumExplosionsDefault) {
                        allExplosions[i] = new DefaultExplosion("default");
                    } else {
                        if (i < numberSmallExplosionsDefault) {
                            allExplosions[i] = new DefaultExplosion("small");
                        } else {
                            allExplosions[i] = new ExplosionSkull();
                        }
                    }
                }
            }
            allSprites.add(allExplosions[i]);
        }
    }

    private void gameplay() {
        moveAll = player.speedX / 3;

        if (screen.x < 0 & screen.right() > screenWidth) {
            screen.x -= moveAll;
        } else {
            if (screen.x >= 0) {
                screen.x -= 2;
            } else {
                screen.x += 2;
            }
        }
        screen.update();
        screen.render();

        workWithSprites();

        if (player.intersect(changerGuns)) {
            changerGuns.intersectionPlayer();
        } else {
            if (player.intersect(pauseButton)) {
                pauseButton.intersectionPlayer();
            } else {
                changerGuns.work();
                pauseButton.work();
            }
        }

        if (System.currentTimeMillis() - lastBoss > BOSS_TIME) {
            lastBoss = System.currentTimeMillis();
            HardThread.doInBackGround(() -> {
                if (bosses.size() == 0) {
                    Sprite boss;
                    byte bossType = DEATH_STAR;
                    if (level == 1) {
                        boss = new DeathStar(this);
                    } else {
                        bossType = BOSS_VADERS;
                        boss = new BossVaders(this);
                    }
                    fightScreen = new FightScreen(character, bossType);
                    bosses.add(boss);
                    allSprites.add(boss);
                }
            });
        }

        if (!shotgunKit.picked) {
            if (!shotgunKit.lock) {
                shotgunKit.x -= moveAll;
                shotgunKit.update();
                shotgunKit.render();
                player.checkIntersections(shotgunKit);
            } else {
                if (score >= 50) {
                    if (random.nextFloat() <= 0.01) {
                        shotgunKit.lock = false;
                    }
                }
            }
        }
        if (healthKit.lock) {
            if (random.nextFloat() <= 0.00125) {
                healthKit.lock = false;
            }
        }

        if (level == 1) {
            if (score > 50) {
                if (attention.lock) {
                    if (random.nextFloat() <= 0.004) {
                        attention.start();
                    }
                }
            }
            if (score > 170) {
                if (bosses.size() == 0) {
                    if (factory.lock) {
                        if (random.nextFloat() <= 0.0009) {
                            factory.lock = false;
                            removeSaturnTrash();
                        }
                    }
                }
            }
            if (score > 70) {
                if (demoman.lock) {
                    if (random.nextFloat() <= 0.0021) {
                        demoman.lock = false;
                    }
                }
            }

        } else {
            int curScore = score - oldScore;
            if (curScore > 30) {
                if (spider.lock) {
                    if (random.nextFloat() <= 0.001) {
                        spider.lock = false;
                    }
                }
            }
            if (bosses.size() == 0) {
                if (curScore > 100) {
                    if (sunrise.lock) {
                        if (random.nextFloat() <= 0.0009) {
                            sunrise.lock = false;
                            removeSaturnTrash();
                        }
                    }
                }
                if (curScore > 50) {
                    if (buffer.lock) {
                        if (random.nextFloat() <= 0.001) {
                            buffer.lock = false;
                        }
                    }
                }
                if (atomicBomb.lock) {
                    if (random.nextFloat() <= 0.0022) {
                        atomicBomb.lock = false;
                    }
                }
            }
        }

        changerGuns.render();
        pauseButton.render();

        player.update();
        player.render();
    }

    @Override
    public void run() {
        while (playing) {
            if (DRAW_FPS) {
                timeFrame = System.nanoTime();
            }
            if (holder.getSurface().isValid()) {
                canvas = holder.lockHardwareCanvas();
                switch (gameStatus) {
                    case GAME:
                        gameplay();
                        renderCurrentScore();
                        break;
                    case MENU:
                        preview();
                        renderMaxScore(50);
                        break;
                    case READY:
                        ready();
                        renderCurrentScore();
                        break;
                    case GAME_OVER:
                        gameover();
                        renderCurrentScore();
                        renderMaxScore(130);
                        break;
                    case PAUSE:
                        pause();
                        renderCurrentScore();
                        renderMaxScore(130);
                        break;
                    case BOSS_PREVIEW:
                        renderSprites();
                        bossIncoming();
                        renderCurrentScore();
                        break;
                    case PORTAL:
                        portalTime();
                        renderCurrentScore();
                        break;
                    case WIN:
                        win();
                        break;
                    case TOP:
                        topScore();
                        break;
                    case AFTER_PAUSE:
                        renderSprites();
                        afterPause();
                        renderCurrentScore();
                        break;
                    case SETTINGS:
                        settings();
                        break;
                    case LOADING:
                        loading();
                        break;
                }
                renderFPS();
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointerCount = event.getPointerCount();
        int clickX = (int) event.getX(0);
        int clickY = (int) event.getY(0);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                boolean pb = false;
                boolean cg = false;

                switch (gameStatus) {
                    case MENU:
                        buttonSaturn.setCoords(clickX, clickY);
                        buttonPlayer.setCoords(clickX, clickY);
                        buttonEmerald.setCoords(clickX, clickY);
                        buttonStart.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        buttonMenu.setCoords(clickX, clickY);
                        buttonRestart.setCoords(clickX, clickY);
                        break;
                    case PAUSE:
                        buttonRestart.setCoords(clickX, clickY);
                        buttonStart.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        buttonMenu.setCoords(clickX, clickY);
                        break;
                    case SETTINGS:
                        buttonMenu.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        break;
                    case TOP:
                        buttonMenu.setCoords(clickX, clickY);
                        break;
                    case GAME:
                    case READY:
                    case GAME_OVER:
                    case PORTAL:
                        pb = pauseButton.checkCoords(clickX, clickY);
                        break;
                }
                if (gameStatus == PORTAL | gameStatus == GAME) {
                    cg = changerGuns.checkCoords(clickX, clickY);
                }
                if (!pb && cg) {
                    player.dontmove = true;
                    changerGuns.make();
                    break;
                }
                if (pb && !cg) {
                    player.dontmove = true;
                    pauseButton.make();
                    break;
                }
                player.dontmove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                switch (gameStatus) {
                    case MENU:
                        buttonStart.sweep(clickX, clickY);
                        buttonQuit.sweep(clickX, clickY);
                        buttonMenu.sweep(clickX, clickY);
                        buttonRestart.sweep(clickX, clickY);
                        break;
                    case PAUSE:
                        buttonRestart.sweep(clickX, clickY);
                        buttonStart.sweep(clickX, clickY);
                        buttonQuit.sweep(clickX, clickY);
                        buttonMenu.sweep(clickX, clickY);
                        break;
                    case GAME:
                    case READY:
                    case PORTAL:
                        if (!player.dontmove) {
                            player.setCoords(clickX, clickY);
                        }

                        if (pointerCount >= 2) {
                            changerGuns.setCoords((int) event.getX(1), (int) event.getY(1));
                        }
                        break;
                    case SETTINGS:
                        buttonMenu.sweep(clickX, clickY);
                        buttonQuit.sweep(clickX, clickY);
                        break;
                    case TOP:
                        buttonMenu.sweep(clickX, clickY);
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                buttonStart.isPressed = false;
                buttonQuit.isPressed = false;
                buttonMenu.isPressed = false;
                buttonRestart.isPressed = false;
                break;
        }
        return true;
    }

    public void onPause() {
        AudioHub.stopAndSavePlaying();
        playing = false;
        while (!thread.isInterrupted()) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                print("Game thread " + e.toString());
            }
        }
        hardThread.stopJob();
    }

    public void onResume() {
        if (isFirstRun) {
            isFirstRun = false;
            AudioHub.loadMenuSnd();
        } else {
            hardThread.startJob();
            AudioHub.whoIsPlayed();
        }
        HardThread.doInBackGround(() -> {
            playing = true;
            thread = new Thread(this);
            thread.start();
        });
    }

    public void generateTopScore() {
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonEmerald.hide();
        buttonMenu.newFunc(string_back, (int) (halfScreenWidth - 150 * resizeK), buttonsY, "menu");

        makeTopPlayersTable();

        gameStatus = TOP;
    }

    public void generateWin() {
        AudioHub.playWinMusic();
        AudioHub.pauseBossMusic();
        AudioHub.pauseBackgroundMusic();
        saveScore();
        portal.kill();
        gameStatus = WIN;
    }

    public void generateGameover() {
        GameOver.play();
        makeScoresParams();
        saveScore();
        getMaxScore();
        gameStatus = GAME_OVER;
    }

    public void generatePause() {
        pauseTimer = System.currentTimeMillis();

        AudioHub.stopAndSavePlaying();
        AudioHub.playPauseMusic();

        makeScoresParams();
        int X = halfScreenWidth - buttonQuit.halfWidth;

        buttonStart.newFunc(string_resume, X, screenHeight / 3 - buttonStart.halfHeight, "pause");
        buttonRestart.newFunc(string_restart, X, buttonStart.bottom() + 30, "restart");
        buttonMenu.newFunc(string_to_menu, X, buttonRestart.bottom() + 30, "menu");
        buttonQuit.newFunc(string_quit, X, buttonMenu.bottom() + 30, "quit");

        gameStatus = PAUSE;
    }

    public void generateSettings() {
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonEmerald.hide();
        buttonQuit.newFunc(string_quit, halfScreenWidth + 30, buttonsY, "quit");
        buttonMenu.newFunc(string_to_menu, halfScreenWidth - 30 - buttonQuit.width, buttonsY, "fromSetting");
        settings = new Settings(this);
        gameStatus = SETTINGS;
    }

    public void generateMenu() {
        AudioHub.clearStatus();

        getMaxScore();
        alphaEnemy.setAlpha(255);
        endImgInit = false;
        makeScoresParams();

        GameOver.pause();
        AudioHub.deleteWinMusic();
        AudioHub.loadMenuSnd();
        AudioHub.deletePauseMusic();
        AudioHub.pauseBackgroundMusic();
        AudioHub.pauseBossMusic();

        ImageHub.deleteSecondLevelImages();
        ImageHub.deleteSettingsImages();
        if (ImageHub.needImagesForFirstLevel()) {
            ImageHub.loadFirstLevelImages();
            while (!endImgInit) {
            }
        }
        ImageHub.loadCharacterImages(MILLENNIUM_FALCON);
        while (!endImgInit) {
        }

        score = 0;
        level = 1;

        allSprites = new ArrayList<>(0);
        bullets = new ArrayList<>(0);
        bosses = new ArrayList<>(0);

        spider = null;
        sunrise = null;
        buffer = null;
        shotgunKit.picked = false;

        screen = new StarScreen();
        player = new Bot(this);

        int len = numberVaders * 2;
        for (int i = 0; i < len; i++) {
            allSprites.add(new Vader(this, false));
        }

        while (buttonStart.right() > buttonMenu.x) {
            buttonMenu.newFunc(string_top, halfScreenWidth, buttonsY, "top");
            buttonStart.newFunc(string_start, halfScreenWidth - buttonMenu.width, buttonsY, "start");
            buttonQuit.newFunc(string_quit, buttonStart.x - buttonStart.width, buttonsY, "quit");
            buttonRestart.newFunc(string_settings, buttonMenu.x + buttonQuit.width, buttonsY, "settings");
        }

        for (int i = 0; i < numberExplosionsALL; i++) {
            allExplosions[i].stop();
            allSprites.add(allExplosions[i]);
        }
        gameStatus = MENU;
    }

    public void generateNewGame() {
        GameOver.pause();
        AudioHub.clearStatus();
        AudioHub.deleteMenuSnd();
        AudioHub.pauseBossMusic();
        AudioHub.deletePauseMusic();
        ImageHub.loadCharacterImages(character);

        makeScoresParams();

        count = 0;
        BOSS_TIME = 100_000;

        bosses = new ArrayList<>(0);
        bullets = new ArrayList<>(0);
        allSprites = new ArrayList<>(0);

        shotgunKit.hide();
        healthKit.hide();
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonEmerald.hide();
        pauseButton.show();
        if (portal != null) {
            portal.kill();
        }

        allSprites.add(healthKit);

        int len;
        switch (level) {
            case 1:
                HardThread.doInUI(AudioHub::loadFirstLevelSounds);

                score = 0;

                spider = null;
                sunrise = null;
                buffer = null;
                atomicBomb = null;

                ImageHub.deleteSecondLevelImages();
                while (!endImgInit) {
                }
                switch (character) {
                    case SATURN:
                        player = new Saturn(this);
                        break;
                    case MILLENNIUM_FALCON:
                        player = new MillenniumFalcon(this);
                        break;
                    case EMERALD:
                        player = new Emerald(this);
                        break;
                }
                if (ImageHub.needImagesForFirstLevel()) {
                    ImageHub.loadFirstLevelImages();
                    while (!endImgInit) {
                    }
                }

                shotgunKit.picked = false;

                rocket = new Rocket(this);
                attention = new Attention(this);
                factory = new Factory(this);
                demoman = new Demoman(this);
                screen = new StarScreen();
                alphaEnemy.setAlpha(255);

                len = numberVaders - 1;
                allSprites.add(new TripleFighter(this));
                for (int i = 0; i < len; i++) {
                    if (random.nextFloat() <= 0.12) {
                        allSprites.add(new TripleFighter(this));
                    } else {
                        allSprites.add(new Vader(this, false));
                    }
                }

                allSprites.add(factory);
                allSprites.add(demoman);
                allSprites.add(rocket);
                allSprites.add(attention);
                break;
            case 2:
                HardThread.doInUI(AudioHub::loadSecondLevelSounds);

                oldScore = score;

                attention = null;
                rocket = null;
                factory = null;
                demoman = null;

                ImageHub.deleteFirstLevelImages();

                player.newStatus();

                spider = new Spider(this);
                sunrise = new Sunrise(this);
                buffer = new Buffer(this);
                screen = new ThunderScreen();
                atomicBomb = new AtomicBomb(this);
                alphaEnemy.setAlpha(165);

                len = numberVaders + 5;
                for (int i = 0; i < len; i++) {
                    if (random.nextFloat() <= 0.18) {
                        allSprites.add(new XWing(this));
                    } else {
                        allSprites.add(new Vader(this, true));
                    }
                }

                allSprites.add(spider);
                allSprites.add(sunrise);
                allSprites.add(buffer);
                allSprites.add(atomicBomb);
                break;
        }

        for (int i = 0; i < numberExplosionsALL; i++) {
            allExplosions[i].stop();
            allSprites.add(allExplosions[i]);
        }

        changerGuns.hide();

        AudioHub.playReadySnd();

        gameStatus = READY;
    }

    private void gameover() {
        canvas.drawBitmap(ImageHub.gameoverScreen, 0, 0, null);

        pauseButton.render();

        for (int i = 0; i < numberExplosionsALL; i++) {
            if (!allExplosions[i].lock) {
                allExplosions[i].render();
                allExplosions[i].update();
            }
        }

        canvas.drawText(string_go_to_restart, go_to_restartX, go_to_restartY, gameoverPaint);

        if (pointerCount >= 4) {
            level = 1;
            onLoading(this::generateNewGame);
        }
    }

    private void bossIncoming() {
        Sprite boss = bosses.get(bosses.size() - 1);
        boss.update();
        fightScreen.render();
        if (boss.y >= -400 | pointerCount >= 4) {
            if (portal == null) {
                gameStatus = GAME;
            } else {
                gameStatus = PORTAL;
            }
            boss.y = -boss.height;
            boss.speedY = 3;
            ImageHub.deleteFightScreen();
            fightScreen = null;
        }
    }

    private void afterPause() {
        count += 1;
        if (0 <= count & count < 23) {
            canvas.drawText("3", (screenWidth - startPaint.measureText("3")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
        } else {
            if (23 <= count & count < 46) {
                canvas.drawText("2", (screenWidth - startPaint.measureText("2")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
            } else {
                if (46 <= count & count < 69) {
                    canvas.drawText("1", (screenWidth - startPaint.measureText("1")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                } else {
                    if (count >= 70) {
                        pauseButton.show();
                        gameStatus = GAME;
                        count = 0;
                    }
                }
            }
        }
    }

    private void pause() {
        if (PauseButton.oldStatus != 3) {
            renderSprites();

            if (PauseButton.oldStatus == 2) {
                if (0 <= count & count < 70) {
                    canvas.drawText("3", (screenWidth - startPaint.measureText("3")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                } else {
                    if (70 <= count & count < 140) {
                        canvas.drawText("2", (screenWidth - startPaint.measureText("2")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                    } else {
                        if (140 <= count & count < 210) {
                            canvas.drawText("1", (screenWidth - startPaint.measureText("1")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                        } else {
                            if (210 <= count & count < 280) {
                                canvas.drawText(string_shoot, shootX, shootY, startPaint);
                            }
                        }
                    }
                }
            }
        } else {
            canvas.drawBitmap(ImageHub.gameoverScreen, 0, 0, null);
            canvas.drawText(string_go_to_restart, go_to_restartX, go_to_restartY, gameoverPaint);
        }

        buttonStart.render();
        buttonQuit.render();
        buttonMenu.render();
        buttonRestart.render();
    }

    private void ready() {
        moveAll = player.speedX / 3;

        if (screen.x < 0 & screen.right() > screenWidth) {
            screen.x -= moveAll;
        } else {
            if (screen.x >= 0) {
                screen.x -= 2;
            } else {
                screen.x += 2;
            }
        }
        screen.update();
        screen.render();

        player.update();
        player.render();

        pauseButton.render();

        count++;
        if (0 <= count & count < 70) {
            canvas.drawText("3", (screenWidth - startPaint.measureText("1")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
        } else {
            if (70 <= count & count < 140) {
                canvas.drawText("2", (screenWidth - startPaint.measureText("2")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
            } else {
                if (140 <= count & count < 210) {
                    canvas.drawText("1", (screenWidth - startPaint.measureText("3")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                } else {
                    if (210 <= count & count < 280) {
                        canvas.drawText(string_shoot, shootX, shootY, startPaint);
                    } else {
                        if (count >= 280) {
                            player.lock = false;
                            changerGuns.start();
                            count = 0;
                            gameStatus = GAME;
                            lastBoss = System.currentTimeMillis();
                        }
                    }
                }
            }
        }
    }

    private void preview() {
        screen.update();
        screen.render();

        for (int i = 0; i < allSprites.size(); i++) {
            Sprite sprite = allSprites.get(i);
            if (!sprite.lock) {
                sprite.render();
                sprite.update();
                if (!sprite.isPassive) {
                    player.checkIntersections(sprite);
                }
                if (!sprite.isBullet) {
                    for (int j = 0; j < bullets.size(); j++) {
                        sprite.check_intersectionBullet(bullets.get(j));
                    }
                }
            }
        }

        player.update();
        player.render();

        buttonStart.render();
        buttonQuit.render();
        buttonMenu.render();
        buttonRestart.render();
        buttonPlayer.render();
        buttonSaturn.render();
        buttonEmerald.render();

        if (buttonPlayer.x < screenWidth) {
            canvas.drawText(string_choose_your_character, chooseChX, chooseChY, Game.paint50);
        }
    }

    private void topScore() {
        screen.update();
        screen.render();

        buttonMenu.render();

        Table.drawTable();
    }

    private void settings() {
        screen.update();
        screen.render();

        buttonMenu.render();
        buttonQuit.render();
    }

    private void win() {
        if (endImgInit) {
            canvas.drawText(string_thanks, thanksX, thanksY, winPaint);
            canvas.drawText(string_go_to_menu, go_to_menuX, go_to_menuY, gameoverPaint);

            if (pointerCount >= 4) {
                onLoading(this::generateMenu);
            }
        }
    }

    private void portalTime() {
        moveAll = player.speedX / 3;

        if (screen.x < 0 & screen.right() > screenWidth) {
            screen.x -= moveAll;
        } else {
            if (screen.x >= 0) {
                screen.x -= 2;
            } else {
                screen.x += 2;
            }
        }
        screen.update();
        screen.render();

        workWithSprites();

        if (player.intersect(changerGuns)) {
            changerGuns.intersectionPlayer();
        } else {
            if (player.intersect(pauseButton)) {
                pauseButton.intersectionPlayer();
            } else {
                changerGuns.work();
                pauseButton.work();
            }
        }

        if (!shotgunKit.picked) {
            if (!shotgunKit.lock) {
                shotgunKit.x -= moveAll;
                shotgunKit.update();
                shotgunKit.render();
                player.checkIntersections(shotgunKit);
            } else {
                if (score >= 50) {
                    if (random.nextFloat() <= 0.01) {
                        shotgunKit.lock = false;
                    }
                }
            }
        }

        if (level == 1) {
            if (score > 170) {
                if (bosses.size() == 0) {
                    if (factory.lock) {
                        if (random.nextFloat() <= 0.0009) {
                            factory.lock = false;
                            removeSaturnTrash();
                        }
                    }
                }
            }
            if (score > 70) {
                if (demoman.lock) {
                    if (random.nextFloat() <= 0.0021) {
                        demoman.lock = false;
                    }
                }
            }

        } else {
            int curScore = score - oldScore;
            if (curScore > 30) {
                if (spider.lock) {
                    if (random.nextFloat() <= 0.001) {
                        spider.lock = false;
                    }
                }
            }
            if (bosses.size() == 0) {
                if (curScore > 100) {
                    if (sunrise.lock) {
                        if (random.nextFloat() <= 0.0009) {
                            sunrise.lock = false;
                        }
                    }
                }
                if (curScore > 50) {
                    if (buffer.lock) {
                        if (random.nextFloat() <= 0.001) {
                            buffer.lock = false;
                        }
                    }
                }
                if (atomicBomb.lock) {
                    if (random.nextFloat() <= 0.0022) {
                        atomicBomb.lock = false;
                    }
                }
            }
        }

        player.update();
        player.render();

        if (portal != null) {
            portal.x -= moveAll;
            portal.render();
            portal.update();
        }

        pauseButton.render();
        changerGuns.render();
    }

    private void loading() {
        loadingScreen.render();
        loadingScreen.update();
    }

    private void renderSprites() {
        screen.render();
        for (int i = 0; i < allSprites.size(); i++) {
            Sprite sprite = allSprites.get(i);
            if (sprite != null) {
                if (!sprite.lock) {
                    sprite.render();
                }
            }
        }
        shotgunKit.render();
        healthKit.render();
        player.render();
        changerGuns.render();
        if (portal != null) {
            portal.render();
        }
    }

    private void workWithSprites() {
        for (int i = 0; i < allSprites.size(); i++) {
            Sprite anySprite = allSprites.get(i);
            if (anySprite != null) {
                if (!anySprite.lock) {
                    anySprite.x -= moveAll;
                    anySprite.render();
                    anySprite.update();
                    if (!anySprite.isPassive) {
                        player.checkIntersections(anySprite);
                    }
                    if (level == 1) {
                        rocket.checkIntersections(anySprite);
                    }
                    if (!anySprite.isBullet) {
                        for (int j = 0; j < bullets.size(); j++) {
                            anySprite.check_intersectionBullet(bullets.get(j));
                        }
                    }
                    if (character == SATURN) {
                        if (anySprite.name == BULLET_ENEMY) {
                            for (int j = 0; j < bullets.size(); j++) {
                                Sprite bulletPlayer = bullets.get(j);
                                if (bulletPlayer.name == BULLET_SATURN) {
                                    if (anySprite.intersect(bulletPlayer)) {
                                        if (random.nextFloat() <= 0.5) {

                                            Object[] info = bulletPlayer
                                                    .getBox(bulletPlayer.centerX(), bulletPlayer.centerY(),
                                                            (Bitmap) anySprite.getBox(0, 0, null)[0]);

                                            if ((boolean) info[3]) {
                                                BulletEnemyOrbit bulletEnemyOrbit = new BulletEnemyOrbit(info);
                                                allSprites.add(bulletEnemyOrbit);
                                                bullets.add(bulletEnemyOrbit);

                                                allSprites.remove(anySprite);
                                                break;
                                            }
                                        }
                                        anySprite.intersectionPlayer();
                                        bulletPlayer.intersection();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void renderFPS() {
        if (DRAW_FPS) {
            textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText(textBuilder.toString(), fpsX, fpsY, fpsPaint);

            textBuilder.setLength(0);
        }
    }

    private void renderCurrentScore() {
        textBuilder.append(string_current_score).append(score);
        canvas.drawText(textBuilder.toString(), scoreX, 50, scorePaint);

        textBuilder.setLength(0);
    }

    private void renderMaxScore(int y) {
        textBuilder.append(string_max_score).append(bestScore);
        canvas.drawText(textBuilder.toString(), maxScoreX, y, scorePaint);

        textBuilder.setLength(0);
    }

    public void saveScore() {
        if (score > bestScore) {
            Clerk.saveBestScore(score);
            ClientServer.postAndGetBestScore(Clerk.nickname, score);
        }
    }

    public void getMaxScore() {
        bestScore = Clerk.getMaxScore();
    }

    private void removeSaturnTrash() {
        if (character == SATURN) {
            HardThread.doInBackGround(() -> {
                for (int i = 0; i < bullets.size(); i++) {
                    Sprite bullet = bullets.get(i);
                    if (bullet.name == BULLET_SATURN | bullet.name == BULLET_ORBIT) {
                        if (Math.getDistance(player.centerX() - bullet.centerX(), player.centerY() - bullet.centerY()) >= 550) {
                            bullet.intersection();
                        }
                    }
                }
            });
        }
    }

    public void confirmLanguage(boolean set) {
        String[] strings = new String[0];
        gameoverPaint.setTextSize(39);
        switch (language) {
            case "ru":
                strings = mainActivity.getResources().getStringArray(R.array.ru);
                buttonsPaint.setTextSize(33);
                break;
            case "en":
                strings = mainActivity.getResources().getStringArray(R.array.en);
                gameoverPaint.setTextSize(50);
                buttonsPaint.setTextSize(35);
                break;
            case "fr":
                strings = mainActivity.getResources().getStringArray(R.array.fr);
                buttonsPaint.setTextSize(34);
                break;
            case "sp":
                strings = mainActivity.getResources().getStringArray(R.array.sp);
                buttonsPaint.setTextSize(33);
                break;
            case "ge":
                strings = mainActivity.getResources().getStringArray(R.array.ge);
                buttonsPaint.setTextSize(34);
                break;
        }
        string_current_score = strings[0] + " ";
        string_max_score = strings[1] + " ";
        string_top = strings[2];
        string_start = strings[3];
        string_quit = strings[4];
        string_settings = strings[5];
        string_back = strings[6];
        string_resume = strings[7];
        string_restart = strings[8];
        string_to_menu = strings[9];
        string_choose_your_character = strings[10];
        string_thanks = strings[11];
        string_go_to_menu = strings[12];
        string_go_to_restart = strings[13];
        string_shoot = strings[14];
        string_volume = strings[15];
        string_loud_effects = strings[16];
        string_loud_music = strings[17];
        string_vibration = strings[18];
        string_enable = strings[19];
        string_disable = strings[20];
        string_choose_lang = strings[21];
        string_smooth = strings[22];

        if (set) {
            buttonQuit.setText(string_quit);
            buttonMenu.setText(string_to_menu);
        }

        makeScoresParams();
        chooseChX = (int) ((screenWidth - Game.paint50.measureText(string_choose_your_character)) / 2);
        chooseChY = (int) (screenHeight * 0.3);
        thanksX = (int) ((Game.screenWidth - winPaint.measureText(string_thanks)) / 2);
        thanksY = (int) ((Game.screenHeight + winPaint.getTextSize()) / 2.7);
        go_to_menuX = (int) ((Game.screenWidth - Game.gameoverPaint.measureText(string_go_to_menu)) / 2);
        go_to_menuY = (int) (Game.screenHeight * 0.65);
        shootX = (int) ((screenWidth - startPaint.measureText(string_shoot)) / 2);
        shootY = (int) ((screenHeight + startPaint.getTextSize()) / 2);
        go_to_restartX = (int) ((screenWidth - gameoverPaint.measureText(string_go_to_restart)) / 2);
        go_to_restartY = (int) (screenHeight * 0.7);
    }

    private void recoverySettings() {
        String[] settings = Clerk.getSettings().split(" ");
        AudioHub.newVolumeForBackground(Float.parseFloat(settings[0]));
        AudioHub.newVolumeForEffects(Float.parseFloat(settings[1]));
        vibrate = Integer.parseInt(settings[2]) == 1;
        language = settings[3];

        getMaxScore();
        confirmLanguage(false);
    }

    public void saveSettings() {
        saveScore();

        if (settings != null) {
            settings.saveSettings();
        }
    }

    public static void makeTopPlayersTable() {
        HardThread.doInBackGround(() -> {
            Table.newTable(tableY);
            for (int i = 0; i < ClientServer.info_from_server.length(); i++) {
                try {
                    String string = (i + 1) + ") " + ClientServer.namesPlayers.get(i) +
                            " - " + ClientServer.info_from_server.get(ClientServer.namesPlayers.get(i).toString());
                    if (Clerk.nickname.equals(ClientServer.namesPlayers.get(i))) {
                        Table.addMarkedText(string);
                    } else {
                        Table.addText(string);
                    }
                } catch (Exception e) {
                    print(e.toString());
                    Table.addText((i + 1) + ") Bad Boy");
                }
            }
            Table.makeTable();
        });
    }

    public void makeScoresParams() {
        scoreX = (int) (halfScreenWidth - scorePaint.measureText(string_current_score + score) / 2);
        maxScoreX = (int) (halfScreenWidth - scorePaint.measureText(string_max_score + bestScore) / 2);
    }


    public void hideSettings() {
        Service.runOnUiThread(() -> {
            settings.confirmSettings();
            settings = null;
        });
    }

    public void onLoading(Function function) {
        loadingScreen.launch(function, ImageHub.needImagesForFirstLevel());
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }
}
