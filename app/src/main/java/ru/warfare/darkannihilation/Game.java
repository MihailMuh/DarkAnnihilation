package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private final SurfaceHolder holder = getHolder();
    public MainActivity context;
    private Thread thread;
    public static Canvas canvas;

    public static int screenWidth;
    public static int screenHeight;
    public static int halfScreenWidth;
    public static int halfScreenHeight;
    public static double resizeK;

    public static final Paint fpsPaint = new Paint();
    public static final Paint startPaint = new Paint();
    public static final Paint gameoverPaint = new Paint();
    public static final Paint scorePaint = new Paint();
    public static final Paint topPaint = new Paint();
    public static final Paint topPaintRed = new Paint();
    public static final Paint alphaPaint = new Paint();
    public static final Paint winPaint = new Paint();
    public static final Paint paint50 = new Paint();
    public static final Paint buttonsPaint = new Paint();

    public static final Random random = new Random();
    private static final StringBuilder textBuilder = new StringBuilder();

    public static final BaseExplosion[] allExplosions = new BaseExplosion[73];
    public static ArrayList<Sprite> bullets = new ArrayList<>(0);
    public static ArrayList<Sprite> bosses = new ArrayList<>(0);
    public static ArrayList<Sprite> allSprites = new ArrayList<>(0);

    public HardThread hardThread;
    public BaseScreen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public Button buttonMenu;
    public Button buttonRestart;
    public PauseButton pauseButton;
    public FightBg fightBg;
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
    public Settings settings;
    public AtomicBomb atomicBomb;

    public static final int numberVaders = 10;
    public static final int numberMediumExplosionsTriple = 20;
    public static final int numberSmallExplosionsTriple = 15 + numberMediumExplosionsTriple;
    public static final int numberMediumExplosionsDefault = numberSmallExplosionsTriple + 20;
    public static final int numberSmallExplosionsDefault = numberMediumExplosionsDefault + 15;
    public static final int numberExplosionsALL = 73;

    public static int level = 1;
    public static int gameStatus = 1;
    private int count = 0;
    public static int score = 0;
    public int oldScore;
    public int bestScore = 0;
    private int pointerCount;
    private int moveAll;
    private boolean playing = true;
    public static volatile String character = "falcon";
    public static volatile boolean endImgInit = false;
    private static final boolean drawFPS = false;
    public static volatile boolean vibrate;
    public static volatile String language = "en";

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

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(MainActivity mainActivity) {
        count++;
        if (count == 1) {
            context = mainActivity;

            screenWidth = Service.getScreenWidth();
            screenHeight = Service.getScreenHeight();
            halfScreenWidth = screenWidth / 2;
            halfScreenHeight = screenHeight / 2;
            resizeK = Service.getResizeCoefficient();
            buttonsY = (int) (screenHeight - (ImageHub.eX70 * 1.5));

            fpsPaint.setColor(Color.RED);
            fpsPaint.setTextSize(40);

            startPaint.setColor(Color.WHITE);
            startPaint.setTextSize(300);
            startPaint.setFilterBitmap(true);
            startPaint.setDither(true);

            gameoverPaint.setColor(Color.WHITE);
            gameoverPaint.setFilterBitmap(true);
            gameoverPaint.setDither(true);

            scorePaint.setColor(Color.WHITE);
            scorePaint.setTextSize(40);
            scorePaint.setFilterBitmap(true);
            scorePaint.setDither(true);

            topPaint.setColor(Color.WHITE);
            topPaint.setTextSize(30);
            topPaint.setFilterBitmap(true);
            topPaint.setDither(true);

            topPaintRed.setColor(Color.RED);
            topPaintRed.setTextSize(30);
            topPaintRed.setFilterBitmap(true);
            topPaintRed.setDither(true);

            winPaint.setColor(Color.WHITE);
            winPaint.setTextSize(100);
            winPaint.setFilterBitmap(true);
            winPaint.setDither(true);

            paint50.setColor(Color.WHITE);
            paint50.setTextSize(50);
            paint50.setFilterBitmap(true);
            paint50.setDither(true);

            buttonsPaint.setColor(Color.WHITE);
            buttonsPaint.setFilterBitmap(true);
            buttonsPaint.setDither(true);

            alphaPaint.setFilterBitmap(true);
            alphaPaint.setDither(true);

            getMaxScore();

            while (!endImgInit) {
            }

            buttonMenu = new Button(this);
            buttonStart = new Button(this);
            buttonQuit = new Button(this);
            buttonRestart = new Button(this);

            settings = new Settings(mainActivity);

            new Thread(() -> {
                fightBg = new FightBg();
                buttonPlayer = new ButtonPlayer(this);
                buttonSaturn = new ButtonSaturn(this);
                buttonEmerald = new ButtonEmerald(this);
                pauseButton = new PauseButton(this);
                player = new Bot();
                healthKit = new HealthKit(this);
                shotgunKit = new ShotgunKit(this);
                changerGuns = new ChangerGuns(this);
                rocket = new Rocket();
                attention = new Attention(this);
                factory = new Factory();
                demoman = new Demoman();
                screen = new StarScreen();
                loadingScreen = new LoadingScreen(this);

                fpsY = pauseButton.bottom() + 100;
                fpsX = screenWidth - 250;
                scoreX = (int) (halfScreenWidth - scorePaint.measureText(string_current_score + "88") / 2);
                maxScoreX = (int) (halfScreenWidth - scorePaint.measureText(string_max_score + "" + bestScore) / 2);
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

                while (buttonStart.right() > buttonMenu.x) {
                    buttonMenu.newFunc(string_top, halfScreenWidth, buttonsY, "top");
                    buttonStart.newFunc(string_start, halfScreenWidth - buttonMenu.width, buttonsY, "start");
                    buttonQuit.newFunc(string_quit, buttonStart.x - buttonStart.width, buttonsY, "quit");
                    buttonRestart.newFunc(string_settings, buttonMenu.x + buttonQuit.width, buttonsY, "settings");
                }
            }).start();

            for (int i = 0; i < numberVaders * 2; i++) {
                allSprites.add(new Vader());
            }
            hardThread = new HardThread(this);

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
    }

    private void gameplay() {
        long now = System.currentTimeMillis();
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
                    if (!anySprite.isBullet) {
                        for (int j = 0; j < bullets.size(); j++) {
                            anySprite.check_intersectionBullet(bullets.get(j));
                        }
                    }
                    if (character.equals("saturn")) {
                        if (anySprite.status.equals("bulletEnemy")) {
                            for (int j = 0; j < bullets.size(); j++) {
                                Sprite bulletPlayer = bullets.get(j);
                                if (bulletPlayer.status.equals("saturn")) {
                                    if (anySprite.getRect().intersect(bulletPlayer.getRect())) {
                                        if (random.nextFloat() <= 0.5) {
                                            Object[] info = bulletPlayer.getBox(anySprite.x, anySprite.y,
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
                        } else {
                            if (anySprite.status.equals("rocket")) {
                                for (int j = 0; j < bullets.size(); j++) {
                                    Sprite bullet = bullets.get(j);
                                    if (bullet.status.equals("saturn")) {
                                        if (anySprite.getRect().intersect(bullet.getRect())) {
                                            bullet.intersection();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        player.update();
        player.render();

        if (now - lastBoss > BOSS_TIME) {
            lastBoss = now;
            Sprite boss;
            if (level == 1) {
                boss = new Boss(this);
                fightBg.newImg(character);
            } else {
                boss = new BossVaders(this);
                fightBg.newImg(character + "-vaders");
            }
            bosses.add(boss);
            allSprites.add(boss);
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
            if (random.nextFloat() <= 0.0015) {
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
                    if (random.nextFloat() <= 0.0024) {
                        atomicBomb.lock = false;
                    }
                }
            }
        }

        pauseButton.render();
        changerGuns.render();
    }

    @Override
    public void run() {
        while (playing) {
            if (drawFPS) {
                timeFrame = System.nanoTime();
            }
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                switch (gameStatus) {
                    case 0:
                        gameplay();
                        renderCurrentScore();
                        break;
                    case 1:
                        preview();
                        renderMaxScore(50);
                        break;
                    case 2:
                        ready();
                        renderCurrentScore();
                        break;
                    case 3:
                        gameover();
                        renderCurrentScore();
                        renderMaxScore(130);
                        break;
                    case 4:
                        pause();
                        renderCurrentScore();
                        renderMaxScore(130);
                        break;
                    case 5:
                        renderSprites();
                        bossIncoming();
                        renderCurrentScore();
                        break;
                    case 6:
                        portalTime();
                        renderCurrentScore();
                        break;
                    case 7:
                        win();
                        break;
                    case 8:
                        topScore();
                        break;
                    case 9:
                        renderSprites();
                        afterPause();
                        renderCurrentScore();
                        break;
                    case 10:
                        settings();
                        break;
                    case 41:
                        onLoading();
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
                    case 1:
                        buttonSaturn.setCoords(clickX, clickY);
                        buttonPlayer.setCoords(clickX, clickY);
                        buttonEmerald.setCoords(clickX, clickY);
                        buttonStart.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        buttonMenu.setCoords(clickX, clickY);
                        buttonRestart.setCoords(clickX, clickY);
                        break;
                    case 4:
                        buttonRestart.setCoords(clickX, clickY);
                        buttonStart.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        buttonMenu.setCoords(clickX, clickY);
                        break;
                    case 10:
                        buttonMenu.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        break;
                    case 8:
                        buttonMenu.setCoords(clickX, clickY);
                        break;
                    case 0:
                    case 2:
                    case 3:
                        pb = pauseButton.checkCoords(clickX, clickY);
                        break;
                }
                if (gameStatus == 6 | gameStatus == 0) {
                    cg = changerGuns.checkCoords(clickX, clickY);
                }
                if (!pb && cg) {
                    player.dontmove = true;
                    changerGuns.make();
                    break;
                } else {
                    if (pb && !cg) {
                        player.dontmove = true;
                        pauseButton.make();
                        break;
                    } else {
                        player.dontmove = false;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (gameStatus) {
                    case 1:
                        buttonStart.sweep(clickX, clickY);
                        buttonQuit.sweep(clickX, clickY);
                        buttonMenu.sweep(clickX, clickY);
                        buttonRestart.sweep(clickX, clickY);
                        break;
                    case 4:
                        buttonRestart.sweep(clickX, clickY);
                        buttonStart.sweep(clickX, clickY);
                        buttonQuit.sweep(clickX, clickY);
                        buttonMenu.sweep(clickX, clickY);
                        break;
                    case 0:
                    case 2:
                    case 6:
                        if (!player.dontmove) {
                            player.setCoords(clickX, clickY);
                        }
                        if (pointerCount >= 2) {
                            changerGuns.setCoords((int) event.getX(1), (int) event.getY(1));
                        }
                        break;
                    case 10:
                        buttonMenu.sweep(clickX, clickY);
                        buttonQuit.sweep(clickX, clickY);
                        break;
                    case 8:
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
        hardThread.workOnPause();
        if (gameStatus == 7) {
            AudioHub.winMusic.pause();
        }
        AudioHub.pauseMenuMusic();
        AudioHub.pauseBossMusic();
        AudioHub.pauseBackgroundMusic();
        AudioHub.pausePauseMusic();
        playing = false;
        try {
            thread.join();
        } catch (Exception e) {
            Service.print("Thread join " + e);
        }
    }

    public void onResume() {
        hardThread.workOnResume();
        if (gameStatus == 7) {
            AudioHub.winMusic.start();
        } else {
            if (bosses.size() == 0) {
                switch (gameStatus) {
                    case 0:
                    case 2:
                    case 3:
                        AudioHub.resumeBackgroundMusic();
                        break;
                    case 1:
                        AudioHub.menuMusic.start();
                        break;
                    case 4:
                        AudioHub.pauseMusic.start();
                        break;
                }
            } else {
                AudioHub.resumeBossMusic();
            }
        }
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void generateTopScore() {
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonEmerald.hide();
        buttonMenu.newFunc(string_back, (int) (halfScreenWidth - 150 * resizeK), buttonsY, "menu");

        Table.newTable(buttonMenu.y - buttonMenu.height);
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
                Service.print(e.toString());
                Table.addText((i + 1) + ") Bad Boy");
            }
        }
        Table.makeTable();

        gameStatus = 8;
    }

    public void generateWin() {
        gameStatus = 7;
        AudioHub.pauseBossMusic();
        AudioHub.portalSound.pause();
        AudioHub.winMusic.seekTo(0);
        AudioHub.winMusic.start();
        saveScore();
        portal.kill();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            Service.print("Can't sleep when win " + e);
        }
    }

    public void generateGameover() {
        maxScoreX = (int) (halfScreenWidth - scorePaint.measureText(string_max_score + "" + bestScore) / 2);
        saveScore();
        getMaxScore();
        AudioHub.gameoverSnd.start();
        gameStatus = 3;
    }

    public void generatePause() {
        pauseTimer = System.currentTimeMillis();

        AudioHub.pauseBackgroundMusic();
        if (bosses.size() == 0) {
            AudioHub.restartPauseMusic();
        }
        AudioHub.pauseReadySound();

        maxScoreX = (int) (halfScreenWidth - scorePaint.measureText(string_max_score + "" + bestScore) / 2);
        int X = halfScreenWidth - buttonQuit.halfWidth;

        buttonStart.newFunc(string_resume, X, screenHeight / 3 - buttonStart.halfHeight, "pause");
        buttonRestart.newFunc(string_restart, X, buttonStart.bottom() + 30, "restart");
        buttonMenu.newFunc(string_to_menu, X, buttonRestart.bottom() + 30, "menu");
        buttonQuit.newFunc(string_quit, X, buttonMenu.bottom() + 30, "quit");
        gameStatus = 4;
    }

    public void generateSettings() {
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonEmerald.hide();
        buttonQuit.newFunc(string_quit, halfScreenWidth + 30, buttonsY, "quit");
        buttonMenu.newFunc(string_to_menu, halfScreenWidth - 30 - buttonQuit.width, buttonsY, "fromSetting");
        settings.showSettings();
    }

    public void generateMenu() {
        getMaxScore();
        alphaPaint.setAlpha(255);
        endImgInit = false;
        maxScoreX = (int) (halfScreenWidth - scorePaint.measureText(string_max_score + "" + bestScore) / 2);

        if (AudioHub.winMusic.isPlaying()) {
            AudioHub.winMusic.pause();
        }

        AudioHub.pauseReadySound();
        AudioHub.pauseFlightMusic();
        AudioHub.restartMenuMusic();
        AudioHub.pausePauseMusic();
        AudioHub.pauseBackgroundMusic();
        AudioHub.pauseBossMusic();

        ImageHub.deleteSecondLevelImages();
        if (ImageHub.needImagesForFirstLevel()) {
            ImageHub.loadFirstLevelImages();
            while (!endImgInit) {}
        }
        ImageHub.loadCharacterImages("falcon");
        while (!endImgInit) {}

        score = 0;
        level = 1;

        allSprites = new ArrayList<>(0);
        bullets = new ArrayList<>(0);
        bosses = new ArrayList<>(0);

        spider = null;
        sunrise = null;
        buffer = null;
        screen = new StarScreen();
        shotgunKit.picked = false;

        player = new Bot();

        for (int i = 0; i < numberVaders * 2; i++) {
            allSprites.add(new Vader());
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
        gameStatus = 1;
    }

    public void generateNewGame() {
       AudioHub.pauseMenuMusic();
        AudioHub.pauseReadySound();
        AudioHub.pauseBossMusic();
        AudioHub.pausePauseMusic();
        ImageHub.deleteSettingsImages();
        ImageHub.loadCharacterImages(character);

        hardThread.workOnPause();
        hardThread.workOnResume();
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

        while (!endImgInit) {}
        switch (character)
        {
            case "saturn":
                player = new Saturn(this);
                break;
            case "falcon":
                player = new MillenniumFalcon(this);
                break;
            case "emerald":
                player = new Emerald(this);
                break;
        }

        allSprites.add(healthKit);

        int len;
        switch (level) {
            case 1:
                score = 0;

                spider = null;
                sunrise = null;
                buffer = null;
                atomicBomb = null;

                ImageHub.deleteSecondLevelImages();
                if (ImageHub.needImagesForFirstLevel()) {
                    ImageHub.loadFirstLevelImages();
                    while (!endImgInit) {}
                }

                shotgunKit.picked = false;

                rocket = new Rocket();
                attention = new Attention(this);
                factory = new Factory();
                demoman = new Demoman();
                screen = new StarScreen();
                alphaPaint.setAlpha(255);

                allSprites.add(new TripleFighter());
                len = numberVaders - 1;
                for (int i = 0; i < len; i++) {
                    if (random.nextFloat() <= 0.12) {
                        allSprites.add(new TripleFighter());
                    } else {
                        allSprites.add(new Vader());
                    }
                }

                allSprites.add(factory);
                allSprites.add(demoman);
                allSprites.add(rocket);
                allSprites.add(attention);

                break;
            case 2:
                oldScore = score;

                attention = null;
                rocket = null;
                factory = null;
                demoman = null;

                ImageHub.deleteFirstLevelImages();

                spider = new Spider();
                sunrise = new Sunrise();
                buffer = new Buffer();
                screen = new ThunderScreen();
                atomicBomb = new AtomicBomb();
                alphaPaint.setAlpha(165);

                len = numberVaders + 3;
                for (int i = 0; i < len; i++) {
                    if (random.nextFloat() <= 0.18) {
                        allSprites.add(new XWing());
                    } else {
                        allSprites.add(new Vader(true));
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

        AudioHub.restartBackgroundMusic();
        AudioHub.restartReadySound();

        gameStatus = 2;
    }

    private void gameover() {
        screen.render(ImageHub.gameoverScreen);

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
            loadingScreen.newJob("newGame");
        }
    }

    private void bossIncoming() {
        Sprite boss = bosses.get(bosses.size() - 1);
        if (boss.y >= -400 | pointerCount >= 4) {
            if (portal == null) {
                gameStatus = 0;
            } else {
                gameStatus = 6;
            }
            boss.y = -boss.height;
            boss.speedY = 3;
        }
        boss.update();
        fightBg.render();
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
                        gameStatus = 0;
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
                                canvas.drawText(string_shoot, shootX,shootY, startPaint);
                            }
                        }
                    }
                }
            }
        } else {
            screen.render(ImageHub.gameoverScreen);
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

        count += 1;
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
                            gameStatus = 0;
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
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if (endImgInit & !ImageHub.isWin()) {
            if (count < 100_000) {
                count = 100_101;
                ImageHub.deleteWinImages(context);
            }

            canvas.drawText(string_thanks, thanksX, thanksY, winPaint);
            canvas.drawText(string_go_to_menu, go_to_menuX, go_to_menuY, gameoverPaint);

            if (pointerCount >= 4) {
                loadingScreen.newJob("menu");
            }
        }
    }

    private void portalTime() {
        moveAll = player.speedX / 3;

        int sX = screen.x + screen.width;
        if (screen.x < 0 & sX > screenWidth) {
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
                    if (!anySprite.isBullet) {
                        for (int j = 0; j < bullets.size(); j++) {
                            anySprite.check_intersectionBullet(bullets.get(j));
                        }
                    }
                    if (character.equals("saturn")) {
                        if (anySprite.status.equals("rocket")) {
                            for (int j = 0; j < bullets.size(); j++) {
                                Sprite bullet = bullets.get(j);
                                if (bullet.status.equals("saturn")) {
                                    if (anySprite.getRect().intersect(bullet.getRect())) {
                                        bullet.intersection();
                                    }
                                }
                            }
                        } else {
                            if (anySprite.status.equals("bulletEnemy")) {
                                for (int j = 0; j < bullets.size(); j++) {
                                    Sprite bulletPlayer = bullets.get(j);
                                    if (bulletPlayer.status.equals("saturn")) {
                                        if (anySprite.getRect().intersect(bulletPlayer.getRect())) {
                                            if (random.nextFloat() <= 0.5) {
                                                Object[] info = bulletPlayer.getBox(anySprite.x, anySprite.y,
                                                        (Bitmap) anySprite.getBox(0, 0, null)[0]);
                                                if ((boolean) info[3]) {
                                                    BulletEnemyOrbit bulletEnemyOrbit = new BulletEnemyOrbit(info);
                                                    allSprites.add(bulletEnemyOrbit);
                                                    bullets.add(bulletEnemyOrbit);

                                                    allSprites.remove(anySprite);
                                                } else {
                                                    anySprite.intersectionPlayer();
                                                    bulletPlayer.intersection();
                                                }
                                            } else {
                                                anySprite.intersectionPlayer();
                                                bulletPlayer.intersection();
                                            }
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

        player.update();
        player.render();
        changerGuns.render();

        if (portal != null) {
            portal.x -= moveAll;
            portal.render();
            portal.update();
        }
    }

    private void renderSprites() {
        screen.render();
        for (int i = 0; i < allSprites.size(); i++) {
            if (allSprites.get(i) != null) {
                if (!allSprites.get(i).lock) {
                    allSprites.get(i).render();
                }
            }
        }
        shotgunKit.render();
        player.render();
        changerGuns.render();
        if (portal != null) {
            portal.render();
        }
    }

    private void renderFPS() {
        if (drawFPS) {
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
        }
    }

    public void getMaxScore() {
        bestScore = Clerk.getMaxScore();
    }

    private void onLoading() {
        loadingScreen.render();
        loadingScreen.update();
    }

    public void makeLanguage(boolean set) {
        String[] strings = new String[0];
        gameoverPaint.setTextSize(39);
        switch (language)
        {
            case "ru":
                strings = context.getResources().getStringArray(R.array.ru);
                buttonsPaint.setTextSize(33);
                break;
            case "en":
                strings = context.getResources().getStringArray(R.array.en);
                gameoverPaint.setTextSize(50);
                buttonsPaint.setTextSize(35);
                break;
            case "fr":
                strings = context.getResources().getStringArray(R.array.fr);
                buttonsPaint.setTextSize(34);
                break;
            case "sp":
                strings = context.getResources().getStringArray(R.array.sp);
                buttonsPaint.setTextSize(33);
                break;
            case "ge":
                strings = context.getResources().getStringArray(R.array.ge);
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
        scoreX = (int) (halfScreenWidth - scorePaint.measureText(string_current_score + "88") / 2);
        maxScoreX = (int) (halfScreenWidth - scorePaint.measureText(string_max_score + "" + bestScore) / 2);
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

    public void setAntiAlias(boolean antiAlias) {
        startPaint.setAntiAlias(antiAlias);
        gameoverPaint.setAntiAlias(antiAlias);
        scorePaint.setAntiAlias(antiAlias);
        topPaint.setAntiAlias(antiAlias);
        topPaintRed.setAntiAlias(antiAlias);
        winPaint.setAntiAlias(antiAlias);
        paint50.setAntiAlias(antiAlias);
        buttonsPaint.setAntiAlias(antiAlias);
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
