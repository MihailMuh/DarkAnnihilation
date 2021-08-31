package ru.warfare.darkannihilation.systemd;

import static ru.warfare.darkannihilation.constant.Colors.WIN_COLOR;
import static ru.warfare.darkannihilation.constant.Constants.DRAW_FPS;
import static ru.warfare.darkannihilation.constant.Constants.NANOS_IN_SECOND;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_ALL_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_LARGE_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_SMALL_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SKULL_EXPLOSIONS;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_LARGE_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_SMALL_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_VADER;
import static ru.warfare.darkannihilation.constant.Modes.AFTER_PAUSE;
import static ru.warfare.darkannihilation.constant.Modes.AFTER_SETTINGS;
import static ru.warfare.darkannihilation.constant.Modes.BOSS_PREVIEW;
import static ru.warfare.darkannihilation.constant.Modes.GAME;
import static ru.warfare.darkannihilation.constant.Modes.GAME_OVER;
import static ru.warfare.darkannihilation.constant.Modes.LOADING;
import static ru.warfare.darkannihilation.constant.Modes.MENU;
import static ru.warfare.darkannihilation.constant.Modes.PASS;
import static ru.warfare.darkannihilation.constant.Modes.PAUSE;
import static ru.warfare.darkannihilation.constant.Modes.QUIT;
import static ru.warfare.darkannihilation.constant.Modes.READY;
import static ru.warfare.darkannihilation.constant.Modes.RESTART;
import static ru.warfare.darkannihilation.constant.Modes.SETTINGS;
import static ru.warfare.darkannihilation.constant.Modes.TOP;
import static ru.warfare.darkannihilation.constant.Modes.WIN;
import static ru.warfare.darkannihilation.constant.NamesConst.BOSS_VADERS;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ORBIT;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_SATURN;
import static ru.warfare.darkannihilation.constant.NamesConst.DEATH_STAR;
import static ru.warfare.darkannihilation.constant.NamesConst.EMERALD;
import static ru.warfare.darkannihilation.constant.NamesConst.LARGE_EXPLOSION;
import static ru.warfare.darkannihilation.constant.NamesConst.MILLENNIUM_FALCON;
import static ru.warfare.darkannihilation.constant.NamesConst.SATURN;
import static ru.warfare.darkannihilation.constant.NamesConst.SMALL_EXPLOSION;
import static ru.warfare.darkannihilation.systemd.service.Py.print;
import static ru.warfare.darkannihilation.systemd.service.Service.activity;
import static ru.warfare.darkannihilation.systemd.service.Service.resources;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;
import static ru.warfare.darkannihilation.systemd.service.Windows.calculate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import ru.warfare.darkannihilation.CustomPaint;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.Table;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.audio.GameOver;
import ru.warfare.darkannihilation.base.BaseBoss;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.BaseExplosion;
import ru.warfare.darkannihilation.base.BaseScreen;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletEnemyOrbit;
import ru.warfare.darkannihilation.button.Button;
import ru.warfare.darkannihilation.button.ButtonEmerald;
import ru.warfare.darkannihilation.button.ButtonPlayer;
import ru.warfare.darkannihilation.button.ButtonSaturn;
import ru.warfare.darkannihilation.button.ChangerGuns;
import ru.warfare.darkannihilation.button.PauseButton;
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
import ru.warfare.darkannihilation.explosion.DefaultExplosion;
import ru.warfare.darkannihilation.explosion.ExplosionSkull;
import ru.warfare.darkannihilation.explosion.ExplosionTriple;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.screen.FightScreen;
import ru.warfare.darkannihilation.screen.LoadingScreen;
import ru.warfare.darkannihilation.screen.StarScreen;
import ru.warfare.darkannihilation.screen.ThunderScreen;
import ru.warfare.darkannihilation.support.HealthKit;
import ru.warfare.darkannihilation.support.ShotgunKit;
import ru.warfare.darkannihilation.systemd.service.Clerk;
import ru.warfare.darkannihilation.systemd.service.ClientServer;
import ru.warfare.darkannihilation.systemd.service.Service;
import ru.warfare.darkannihilation.systemd.service.Time;

public final class Game extends SurfaceView implements Runnable {
    private final SurfaceHolder holder = getHolder();
    private Thread thread;
    public static Canvas canvas;

    public static CustomPaint fpsPaint;
    public static CustomPaint startPaint;
    public static CustomPaint gameOverPaint;
    public static CustomPaint scorePaint;
    public static CustomPaint alphaEnemy;
    public static CustomPaint winPaint;
    public static CustomPaint paint50;
    public static CustomPaint buttonsPaint;

    private static final StringBuilder stringBuilder = new StringBuilder();

    public ExplosionSkull[] skullExplosion = new ExplosionSkull[NUMBER_SKULL_EXPLOSIONS];
    public DefaultExplosion[] defaultSmallExplosion = new DefaultExplosion[NUMBER_DEFAULT_SMALL_EXPLOSION];
    public DefaultExplosion[] defaultLargeExplosion = new DefaultExplosion[NUMBER_DEFAULT_LARGE_EXPLOSION];
    public ExplosionTriple[] tripleSmallExplosion = new ExplosionTriple[NUMBER_TRIPLE_SMALL_EXPLOSION];
    public ExplosionTriple[] tripleLargeExplosion = new ExplosionTriple[NUMBER_TRIPLE_LARGE_EXPLOSION];
    private static final BaseExplosion[] allExplosion = new BaseExplosion[NUMBER_ALL_EXPLOSION];

    public ArrayList<BaseBullet> bullets = new ArrayList<>(0);
    public ArrayList<Sprite> intersectOnlyPlayer = new ArrayList<>(0);
    public ArrayList<Sprite> ghosts = new ArrayList<>(0);
    public ArrayList<Sprite> enemies = new ArrayList<>(0);

    private static final HardThread hardThread = new HardThread();
    private Settings settings;
    private Table table;
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
    public BaseBoss boss;

    private int count = 0;
    private int oldScore;
    private int pointerCount;
    private int moveAll;
    private volatile boolean isPause = false;
    private volatile boolean isFirstRun = true;
    public volatile boolean playing = false;
    public static volatile byte level = 1;
    public static volatile byte gameStatus = PASS;
    public static volatile byte character = MILLENNIUM_FALCON;
    public static volatile boolean vibrate;
    public String language = "en";
    public static int score = 0;
    public int bestScore = 0;

    private int fpsX;
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
    private int tableY;
    private int _3;
    private int _2;
    private int _1;
    private int _321Y;

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

    private long lastBoss;
    public int BOSS_TIME;
    public long pauseTimer;

    private long timeFrame = System.nanoTime();

    public Game(Context mainActivity, AttributeSet attrs) {
        super(mainActivity, attrs);
    }

    public void init() {
        alphaEnemy = new CustomPaint();
        gameOverPaint = new CustomPaint();

        buttonsPaint = new CustomPaint();
        buttonsPaint.setTextSize(25);

        fpsPaint = new CustomPaint();
        fpsPaint.setColor(Color.RED);
        fpsPaint.setTextSize(40);

        startPaint = new CustomPaint();
        startPaint.setTextSize(300);

        scorePaint = new CustomPaint();
        scorePaint.setTextSize(40);

        winPaint = new CustomPaint();
        winPaint.setTextSize(100);

        paint50 = new CustomPaint();
        paint50.setTextSize(50);

        recoverySettings();

        buttonsY = (int) (SCREEN_HEIGHT - (ImageHub._70 * 1.5));
        fpsX = SCREEN_WIDTH - 250;
        tableY = buttonsY - (ImageHub._70 * 2);

        Time.waitImg();

        buttonMenu = new Button(this);
        buttonStart = new Button(this);
        buttonQuit = new Button(this);
        buttonRestart = new Button(this);
        screen = new StarScreen();
        player = new Bot(this);

        HardThread.doInPool(() -> {
            pauseButton = new PauseButton(this);
            healthKit = new HealthKit(this);
            shotgunKit = new ShotgunKit(this);
            loadingScreen = new LoadingScreen(this);

            newCharacterButtons();

            resizeButtons(new String[]{string_top, string_settings, string_start, string_quit, string_back, string_resume, string_to_menu});
            updateMenuButtons();
        });

        count = NUMBER_VADER * 2;
        for (int i = 0; i < count; i++) {
            generateVader();
        }

        for (int i = 0; i < NUMBER_SKULL_EXPLOSIONS; i++) {
            skullExplosion[i] = new ExplosionSkull(this);
            allExplosion[i] = skullExplosion[i];
        }
        count = NUMBER_SKULL_EXPLOSIONS;
        for (int i = 0; i < NUMBER_DEFAULT_LARGE_EXPLOSION; i++) {
            defaultLargeExplosion[i] = new DefaultExplosion(this, LARGE_EXPLOSION);
            allExplosion[i + count] = defaultLargeExplosion[i];
        }
        count += NUMBER_DEFAULT_LARGE_EXPLOSION;
        for (int i = 0; i < NUMBER_DEFAULT_SMALL_EXPLOSION; i++) {
            defaultSmallExplosion[i] = new DefaultExplosion(this, SMALL_EXPLOSION);
            allExplosion[i + count] = defaultSmallExplosion[i];
        }
        count += NUMBER_DEFAULT_SMALL_EXPLOSION;
        for (int i = 0; i < NUMBER_TRIPLE_LARGE_EXPLOSION; i++) {
            tripleLargeExplosion[i] = new ExplosionTriple(this, LARGE_EXPLOSION);
            allExplosion[i + count] = tripleLargeExplosion[i];
        }
        count += NUMBER_TRIPLE_LARGE_EXPLOSION;
        for (int i = 0; i < NUMBER_TRIPLE_SMALL_EXPLOSION; i++) {
            tripleSmallExplosion[i] = new ExplosionTriple(this, SMALL_EXPLOSION);
            allExplosion[i + count] = tripleSmallExplosion[i];
        }

        if (!isPause) {
            startGame();
        }
    }

    private void gameplay() {
        moveAll = player.speedX / 3;

        if (screen.x < 0 & screen.right() > SCREEN_WIDTH) {
            screen.x -= moveAll;
        } else {
            moveAll = 0;
            if (screen.x >= 0) {
                screen.x -= 2;
            } else {
                screen.x += 2;
            }
        }
        screen.update();
        screen.render();

        turnEnemies();
        turnGhosts();
        turnBullets();

        player.update();
        player.render();

        turnIntersectOnlyPlayer();
        turnExplosions();

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

        checkTimeForBoss();

        if (score >= 50) {
            if (shotgunKit.lock && !shotgunKit.picked) {
                if (Randomize.randFloat() <= 0.01) {
                    shotgunKit.lock = false;
                }
            }
        }

        if (healthKit.lock) {
            if (Randomize.randFloat() <= 0.001) {
                healthKit.lock = false;
            }
        }

        if (level == 1) {
            if (score > 50) {
                if (attention.lock) {
                    if (Randomize.randFloat() <= 0.004) {
                        attention.start();
                    }
                }
            }
            if (score > 170) {
                if (boss == null) {
                    if (factory.lock) {
                        if (Randomize.randFloat() <= 0.0009) {
                            factory.lock = false;
                            removeSaturnTrash();
                        }
                    }
                }
            }
            if (score > 70) {
                if (demoman.lock) {
                    if (Randomize.randFloat() <= 0.0021) {
                        demoman.start();
                    }
                }
            }

        } else {
            int curScore = score - oldScore;
            if (curScore > 30) {
                if (spider.lock) {
                    if (Randomize.randFloat() <= 0.001) {
                        spider.start();
                    }
                }
            }
            if (boss == null) {
                if (curScore > 100) {
                    if (sunrise.lock) {
                        if (Randomize.randFloat() <= 0.0009) {
                            sunrise.start();
                        }
                    }
                }
                if (curScore > 50) {
                    if (buffer.lock) {
                        if (Randomize.randFloat() <= 0.001) {
                            buffer.start();
                        }
                    }
                }
                if (atomicBomb.lock) {
                    if (Randomize.randFloat() <= 0.0022) {
                        atomicBomb.start();
                    }
                }
            }
        }

        changerGuns.render();
        pauseButton.render();
    }

    @Override
    public void run() {
        while (playing) {
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
                        gameOver();
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
                        loadingScreen.turn();
                        break;
                    case PASS:
                        Time.sleep(50);
                        break;
                }

                if (DRAW_FPS) {
                    renderFPS();
                }
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        pointerCount = event.getPointerCount();
        int clickX = (int) event.getX();
        int clickY = (int) event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                boolean cg = false;

                switch (gameStatus) {
                    case GAME:
                        cg = changerGuns.checkCoords(clickX, clickY);
                    case READY:
                    case GAME_OVER:
                        boolean pb = pauseButton.checkCoords(clickX, clickY);

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
                    case TOP:
                        if (table != null) {
                            table.startMove(clickX);
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (gameStatus) {
                    case GAME:
                        if (pointerCount >= 2) {
                            changerGuns.setCoords((int) event.getX(1), (int) event.getY(1));
                        }
                    case READY:
                        if (!player.dontmove) {
                            player.setCoords(clickX, clickY);
                        }
                        break;
                    case MENU:
                    case PAUSE:
                        buttonRestart.sweep(clickX, clickY);
                        buttonStart.sweep(clickX, clickY);
                    case SETTINGS:
                        buttonQuit.sweep(clickX, clickY);
                    case TOP:
                        buttonMenu.sweep(clickX, clickY);
                        if (table != null) {
                            table.setCoords(clickX);
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (gameStatus) {
                    case MENU:
                        buttonSaturn.setCoords(clickX, clickY);
                        buttonPlayer.setCoords(clickX, clickY);
                        buttonEmerald.setCoords(clickX, clickY);
                    case PAUSE:
                        buttonRestart.setCoords(clickX, clickY);
                        buttonStart.setCoords(clickX, clickY);
                    case SETTINGS:
                        buttonQuit.setCoords(clickX, clickY);
                    case TOP:
                        buttonMenu.setCoords(clickX, clickY);
                        if (table != null) {
                            table.stopMove();
                        }
                        break;
                }
                break;
        }
        return true;
    }

    public void onPause() {
        playing = false;

        if (!isFirstRun) {
            AudioHub.stopAndSavePlaying();
            while (!thread.isInterrupted()) {
                try {
                    thread.interrupt();
                } catch (Exception e) {
                    print("Game thread " + e.toString());
                }
            }
            hardThread.stopJob();
        } else {
            isPause = true;
        }
    }

    public void onResume() {
        if (!isFirstRun) {
            hardThread.startJob();
            AudioHub.whoIsPlayed();
            playing = true;
            thread = new Thread(this);
            thread.start();
        } else {
            if (isPause) {
                startGame();
            }
        }
    }

    public void generateTopScore() {
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonEmerald.hide();
        buttonMenu.newFunc(string_back, HALF_SCREEN_WIDTH - ImageHub._150, buttonsY, MENU);

        gameStatus = TOP;
    }

    public void generateWin() {
        AudioHub.playWinMusic();
        AudioHub.pauseBossMusic();
        AudioHub.pauseBackgroundMusic();
        saveScore();
        portal.kill();
        gameStatus = PASS;
    }

    public void generateGameOver() {
        GameOver.play();
        makeScoresParams();
        saveScore();
        getMaxScore();

        moveAll = 0;

        gameStatus = GAME_OVER;
    }

    public void generatePause() {
        pauseTimer = System.currentTimeMillis();

        AudioHub.stopAndSavePlaying();
        AudioHub.playPauseMusic();

        makeScoresParams();
        int X = HALF_SCREEN_WIDTH - buttonQuit.halfWidth;

        buttonStart.newFunc(string_resume, X, SCREEN_HEIGHT / 3 - buttonStart.halfHeight, PAUSE);
        buttonRestart.newFunc(string_restart, X, buttonStart.bottom() + 30, RESTART);
        buttonMenu.newFunc(string_to_menu, X, buttonRestart.bottom() + 30, MENU);
        buttonQuit.newFunc(string_quit, X, buttonMenu.bottom() + 30, QUIT);

        gameStatus = PAUSE;
    }

    public void generateSettings() {
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonEmerald.hide();

        buttonQuit.newFunc(string_quit, HALF_SCREEN_WIDTH + 30, buttonsY, QUIT);
        buttonMenu.newFunc(string_to_menu, HALF_SCREEN_WIDTH - 30 - buttonQuit.width, buttonsY, AFTER_SETTINGS);

        settings = new Settings(this);

        gameStatus = SETTINGS;
    }

    public void generateMenu() {
        AudioHub.clearStatus();
        GameOver.pause();
        AudioHub.deleteWinMusic();
        AudioHub.deletePauseMusic();
        AudioHub.pauseBackgroundMusic();
        AudioHub.pauseBossMusic();
        AudioHub.loadMenuSnd();

        level = 1;
        enemies = new ArrayList<>(0);

        HardThread.doInPool(() -> {
            bullets = new ArrayList<>(0);
            ghosts = new ArrayList<>(0);
            intersectOnlyPlayer = new ArrayList<>(0);

            stopExplosions();
            newCharacterButtons();

            score = 0;
            moveAll = 0;

            ImageHub.deleteSecondLevelImages();
            ImageHub.deleteSettingsImages();

            getMaxScore();
            alphaEnemy.setAlpha(255);
            makeScoresParams();

            table = null;
            boss = null;
            spider = null;
            sunrise = null;
            buffer = null;
            attention = null;
            rocket = null;
            factory = null;
            demoman = null;
            changerGuns = null;
            shotgunKit.picked = false;

            updateMenuButtons();
        });

        ImageHub.loadFirstLevelAndCharacterImages(MILLENNIUM_FALCON);

        count = NUMBER_VADER * 2;
        for (int i = 0; i < count; i++) {
            generateVader();
        }

        screen = new StarScreen();
        player = new Bot(this);

        gameStatus = MENU;
    }

    public void generateNewGame() {
        GameOver.pause();
        AudioHub.clearStatus();
        AudioHub.deleteMenuSnd();
        AudioHub.pauseBossMusic();
        AudioHub.deletePauseMusic();

        enemies = new ArrayList<>(0);
        ghosts = new ArrayList<>(0);
        intersectOnlyPlayer = new ArrayList<>(0);

        HardThread.doInBackGround(() -> {
            bullets = new ArrayList<>(0);

            stopExplosions();

            makeScoresParams();

            BOSS_TIME = 100_000;
            count = 0;

            shotgunKit.kill();
            healthKit.kill();
            pauseButton.show();
            if (portal != null) {
                portal.kill();
            }

            boss = null;
            buttonPlayer = null;
            buttonSaturn = null;
            buttonEmerald = null;

            intersectOnlyPlayer.add(healthKit);
            intersectOnlyPlayer.add(shotgunKit);
        });

        changerGuns = new ChangerGuns();

        switch (level) {
            case 1:
                AudioHub.loadFirstLevelSounds();
                ImageHub.loadFirstLevelAndCharacterImages(character);

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

                screen = new StarScreen();

                HardThread.doInPool(() -> {
                    ImageHub.loadGunsImages(character);

                    rocket = new Rocket(this);
                    attention = new Attention(this);
                    factory = new Factory(this);
                    demoman = new Demoman(this);

                    enemies.add(new TripleFighter(this));
                    for (int i = 0; i < NUMBER_VADER; i++) {
                        if (Randomize.randFloat() <= 0.12) {
                            enemies.add(new TripleFighter(this));
                        } else {
                            generateVader();
                        }
                    }

                    intersectOnlyPlayer.add(attention);
                    ghosts.add(factory);
                    enemies.add(demoman);

                    score = 0;

                    spider = null;
                    sunrise = null;
                    buffer = null;
                    atomicBomb = null;

                    ImageHub.deleteSecondLevelImages();

                    alphaEnemy.setAlpha(255);
                    shotgunKit.picked = false;

                    Time.waitImg();

                    changerGuns = new ChangerGuns(this);
                });
                break;
            case 2:
                AudioHub.loadSecondLevelSounds();
                ImageHub.loadSecondLevelImages();

                screen = new ThunderScreen();

                HardThread.doInPool(() -> {
                    player.newStatus();

                    spider = new Spider(this);
                    sunrise = new Sunrise(this);
                    buffer = new Buffer(this);
                    atomicBomb = new AtomicBomb(this);
                    changerGuns = new ChangerGuns(this);

                    int len = NUMBER_VADER + 6;
                    for (int i = 0; i < len; i++) {
                        if (Randomize.randFloat() <= 0.18) {
                            enemies.add(new XWing(this));
                        } else {
                            generateVader();
                        }
                    }

                    enemies.add(spider);
                    enemies.add(sunrise);
                    enemies.add(buffer);
                    enemies.add(atomicBomb);

                    oldScore = score;

                    attention = null;
                    rocket = null;
                    factory = null;
                    demoman = null;

                    ImageHub.deleteFirstLevelImages();

                    alphaEnemy.setAlpha(165);
                });
                break;
        }

        AudioHub.playReadySnd();

        gameStatus = READY;
    }

    private void gameOver() {
        canvas.drawBitmap(ImageHub.gameoverScreen, 0, 0, null);

        pauseButton.render();

        turnExplosions();

        canvas.drawText(string_go_to_restart, go_to_restartX, go_to_restartY, gameOverPaint);

        if (pointerCount >= 4) {
            level = 1;
            onLoading(this::generateNewGame);
        }
    }

    private void bossIncoming() {
        boss.update();
        fightScreen.render();
        if (boss.y >= -400 | pointerCount >= 4) {
            gameStatus = GAME;
            boss.y = -boss.height;
            boss.speedY = 3;
            ImageHub.deleteFightScreen();
            fightScreen = null;
        }
    }

    private void afterPause() {
        count++;
        if (0 <= count & count < 23) {
            canvas.drawText("3", _3, _321Y, startPaint);
        } else {
            if (23 <= count & count < 46) {
                canvas.drawText("2", _2, _321Y, startPaint);
            } else {
                if (46 <= count & count < 69) {
                    canvas.drawText("1", _1, _321Y, startPaint);
                } else {
                    if (count >= 70) {
                        pauseButton.show();
                        gameStatus = GAME;
                        count = 0;
                        player.dontmove = false;
                    }
                }
            }
        }
    }

    private void pause() {
        if (PauseButton.oldStatus != GAME_OVER) {
            renderSprites();

            if (PauseButton.oldStatus == READY) {
                if (0 <= count & count < 70) {
                    canvas.drawText("3", _3, _321Y, startPaint);
                } else {
                    if (70 <= count & count < 140) {
                        canvas.drawText("2", _2, _321Y, startPaint);
                    } else {
                        if (140 <= count & count < 210) {
                            canvas.drawText("1", _1, _321Y, startPaint);
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
            canvas.drawText(string_go_to_restart, go_to_restartX, go_to_restartY, gameOverPaint);
        }

        buttonStart.render();
        buttonQuit.render();
        buttonMenu.render();
        buttonRestart.render();
    }

    private void ready() {
        moveAll = player.speedX / 3;

        if (screen.x < 0 & screen.right() > SCREEN_WIDTH) {
            screen.x -= moveAll;
        }
        screen.turn();

        player.turn();

        pauseButton.render();

        count++;
        if (0 <= count & count < 70) {
            canvas.drawText("3", _3, _321Y, startPaint);
        } else {
            if (70 <= count & count < 140) {
                canvas.drawText("2", _2, _321Y, startPaint);
            } else {
                if (140 <= count & count < 210) {
                    canvas.drawText("1", _1, _321Y, startPaint);
                } else {
                    if (210 <= count & count < 280) {
                        canvas.drawText(string_shoot, shootX, shootY, startPaint);
                    } else {
                        if (count >= 280) {
                            player.lock = false;
                            count = 0;
                            changerGuns.x = 0;
                            gameStatus = GAME;
                            lastBoss = System.currentTimeMillis();
                        }
                    }
                }
            }
        }
    }

    private void preview() {
        screen.turn();

        for (int i = 0; i < enemies.size(); i++) {
            Sprite sprite = enemies.get(i);
            sprite.x -= moveAll;
            sprite.turn();
            for (int j = 0; j < bullets.size(); j++) {
                sprite.check_intersectionBullet(bullets.get(j));
            }
            player.checkIntersections(sprite);
        }
        turnBullets();
        turnExplosions();

        player.turn();

        buttonStart.render();
        buttonQuit.render();
        buttonMenu.render();
        buttonRestart.render();

        if (buttonPlayer.x < SCREEN_WIDTH) {
            buttonPlayer.render();
            buttonSaturn.render();
            buttonEmerald.render();
            canvas.drawText(string_choose_your_character, chooseChX, chooseChY, paint50);
        }
    }

    private void topScore() {
        screen.turn();

        buttonMenu.render();

        if (table != null) {
            table.drawTable();
        }
    }

    private void settings() {
        screen.turn();

        buttonMenu.render();
        buttonQuit.render();
    }

    private void win() {
        canvas.drawColor(WIN_COLOR);

        canvas.drawText(string_thanks, thanksX, thanksY, winPaint);
        canvas.drawText(string_go_to_menu, go_to_menuX, go_to_menuY, gameOverPaint);

        if (pointerCount >= 4) {
            onLoading(this::generateMenu);
        }
    }

    private void renderSprites() {
        screen.render();

        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).lock) {
                enemies.get(i).render();
            }
        }
        for (int i = 0; i < ghosts.size(); i++) {
            if (!ghosts.get(i).lock) {
                ghosts.get(i).render();
            }
        }
        for (int i = 0; i < intersectOnlyPlayer.size(); i++) {
            if (!intersectOnlyPlayer.get(i).lock) {
                intersectOnlyPlayer.get(i).render();
            }
        }

        player.render();

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render();
        }
        for (int i = 0; i < NUMBER_ALL_EXPLOSION; i++) {
            if (!allExplosion[i].lock) {
                allExplosion[i].render();
            }
        }

        changerGuns.render();
    }

    private void stopExplosions() {
        for (int i = 0; i < NUMBER_ALL_EXPLOSION; i++) {
            allExplosion[i].lock = true;
        }
    }

    private void turnIntersectOnlyPlayer() {
        if (character == SATURN) {
            for (int i = 0; i < intersectOnlyPlayer.size(); i++) {
                Sprite sprite = intersectOnlyPlayer.get(i);
                if (sprite != null) {
                    if (!sprite.lock) {
                        sprite.x -= moveAll;
                        sprite.turn();
                        player.checkIntersections(sprite);
                        if (sprite.name == BULLET_ENEMY) {
                            for (int j = 0; j < bullets.size(); j++) {
                                Sprite bulletPlayer = bullets.get(j);
                                if (bulletPlayer != null) {
                                    if (bulletPlayer.name == BULLET_SATURN) {
                                        if (sprite.intersect(bulletPlayer)) {
                                            if (Randomize.randBoolean()) {
                                                Object[] info = bulletPlayer
                                                        .getBox(bulletPlayer.centerX(), bulletPlayer.centerY(),
                                                                (Bitmap) sprite.getBox(0, 0, null)[0]);

                                                if ((boolean) info[3]) {
                                                    BulletEnemyOrbit bulletEnemyOrbit = new BulletEnemyOrbit(info);
                                                    bullets.add(bulletEnemyOrbit);

                                                    intersectOnlyPlayer.remove(sprite);
                                                    break;
                                                }
                                            }
                                            sprite.intersectionPlayer();
                                            bulletPlayer.kill();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < intersectOnlyPlayer.size(); i++) {
                Sprite sprite = intersectOnlyPlayer.get(i);
                if (sprite != null) {
                    if (!sprite.lock) {
                        sprite.x -= moveAll;
                        sprite.turn();
                        player.checkIntersections(sprite);
                    }
                }
            }
        }
    }

    private void turnExplosions() {
        for (int i = 0; i < NUMBER_ALL_EXPLOSION; i++) {
            BaseExplosion explosion = allExplosion[i];
            if (!explosion.lock) {
                explosion.x -= moveAll;
                explosion.turn();
            }
        }
    }

    private void turnGhosts() {
        for (int i = 0; i < ghosts.size(); i++) {
            Sprite sprite = ghosts.get(i);
            if (!sprite.lock) {
                sprite.x -= moveAll;
                sprite.turn();
                for (int j = 0; j < bullets.size(); j++) {
                    sprite.check_intersectionBullet(bullets.get(j));
                }
            }
        }
    }

    private void turnEnemies() {
        if (level == 1) {
            for (int i = 0; i < enemies.size(); i++) {
                Sprite sprite = enemies.get(i);
                if (!sprite.lock) {
                    sprite.x -= moveAll;
                    sprite.turn();
                    for (int j = 0; j < bullets.size(); j++) {
                        sprite.check_intersectionBullet(bullets.get(j));
                    }
                    player.checkIntersections(sprite);

                    if (!rocket.lock) {
                        rocket.checkIntersections(sprite);
                    }
                }
            }

            if (!rocket.lock) {
                rocket.x -= moveAll;
                rocket.turn();
                for (int j = 0; j < bullets.size(); j++) {
                    rocket.checkIntersections(bullets.get(j));
                }
                player.checkIntersections(rocket);
            }
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                Sprite sprite = enemies.get(i);
                if (!sprite.lock) {
                    sprite.x -= moveAll;
                    sprite.turn();
                    for (int j = 0; j < bullets.size(); j++) {
                        sprite.check_intersectionBullet(bullets.get(j));
                    }
                    player.checkIntersections(sprite);
                }
            }
        }
    }

    private void turnBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Sprite bullet = bullets.get(i);
            if (bullet != null) {
                bullet.x -= moveAll;
                bullet.turn();
            }
        }
    }

    private void newCharacterButtons() {
        buttonPlayer = new ButtonPlayer(this);
        buttonSaturn = new ButtonSaturn(this);
        buttonEmerald = new ButtonEmerald(this);
    }

    private void updateMenuButtons() {
        buttonMenu.newFunc(string_top, HALF_SCREEN_WIDTH, buttonsY, TOP);
        buttonRestart.newFunc(string_settings, buttonMenu.right(), buttonsY, SETTINGS);
        buttonStart.newFunc(string_start, HALF_SCREEN_WIDTH - buttonMenu.width, buttonsY, GAME);
        buttonQuit.newFunc(string_quit, buttonStart.x - buttonMenu.width, buttonsY, QUIT);
    }

    private void resizeButtons(String[] texts) {
        if (ImageHub.buttonImagePressed != null) {
            int max = 0;
            for (String text : texts) {
                int len = (int) buttonsPaint.measureText(text);
                if (len > max) {
                    max = len;
                }
            }
            max += 70;

            if (max != ImageHub.buttonImagePressed.getWidth()) {
                ImageHub.buttonImagePressed = ImageHub.resizeBitmap(ImageHub.buttonImagePressed, max, ImageHub._70);
                ImageHub.buttonImageNotPressed = ImageHub.resizeBitmap(ImageHub.buttonImageNotPressed, max, ImageHub._70);
            }
        }
    }

    private void renderFPS() {
        stringBuilder.append("FPS: ").append(NANOS_IN_SECOND / (System.nanoTime() - timeFrame));
        timeFrame = System.nanoTime();

        canvas.drawText(stringBuilder.toString(), fpsX, 300, fpsPaint);

        stringBuilder.setLength(0);
    }

    private void renderCurrentScore() {
        stringBuilder.append(string_current_score).append(score);
        canvas.drawText(stringBuilder.toString(), scoreX, 50, scorePaint);

        stringBuilder.setLength(0);
    }

    private void renderMaxScore(int y) {
        stringBuilder.append(string_max_score).append(bestScore);
        canvas.drawText(stringBuilder.toString(), maxScoreX, y, scorePaint);

        stringBuilder.setLength(0);
    }

    public void saveScore() {
        if (score > bestScore) {
            Clerk.saveBestScore(score);
            HardThread.doInPool(() -> ClientServer.postBestScore(Clerk.nickname, score));
        }
    }

    public void getMaxScore() {
        bestScore = Clerk.getMaxScore();
    }

    private void removeSaturnTrash() {
        if (character == SATURN) {
            HardThread.doInPool(() -> {
                for (int i = 0; i < bullets.size(); i++) {
                    Sprite bullet = bullets.get(i);
                    if (bullet.name == BULLET_SATURN | bullet.name == BULLET_ORBIT) {
                        if (Math.getDistance(player.centerX() - bullet.centerX(), player.centerY() - bullet.centerY()) >= 550) {
                            bullet.kill();
                        }
                    }
                }
            });
        }
    }

    private void checkTimeForBoss() {
        if (System.currentTimeMillis() - lastBoss > BOSS_TIME) {
            lastBoss = System.currentTimeMillis();
            HardThread.doInPool(() -> {
                if (boss == null && portal == null) {
                    byte bossType = DEATH_STAR;
                    if (level == 1) {
                        boss = new DeathStar(this);
                    } else {
                        bossType = BOSS_VADERS;
                        boss = new BossVaders(this);
                    }

                    ImageHub.loadFightScreen(character, bossType);

                    fightScreen = new FightScreen();
                    ghosts.add(boss);
                }
            });
        }
    }

    private void startGame() {
        Service.runOnUiThread(() -> {
            setBackground(null);
            holder.setFixedSize(SCREEN_WIDTH, SCREEN_HEIGHT);

            String[] settings = Clerk.getSettings().split(" ");
            AudioHub.newVolumeForBackground(Float.parseFloat(settings[0]));
            AudioHub.newVolumeForEffects(Float.parseFloat(settings[1]));

            AudioHub.loadMenuSnd();
        });

        playing = true;
        gameStatus = MENU;

        thread = new Thread(this);
        thread.start();

        isFirstRun = false;
    }

    public void newPortal() {
        if (portal == null) {
            portal = new Portal(this);
            intersectOnlyPlayer.add(portal);
        }
    }

    public void startEmpire() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).empireStart();
        }
    }

    public void generateVader() {
        enemies.add(new Vader(this));
    }

    public void killBoss() {
        ghosts.remove(boss);
        boss = null;
        lastBoss = System.currentTimeMillis();
    }

    public void confirmLanguage(boolean set) {
        String[] strings = new String[0];
        switch (language) {
            case "ru":
                strings = resources.getStringArray(R.array.ru);
                break;
            case "en":
                strings = resources.getStringArray(R.array.en);
                break;
            case "fr":
                strings = resources.getStringArray(R.array.fr);
                break;
            case "sp":
                strings = resources.getStringArray(R.array.sp);
                break;
            case "ge":
                strings = resources.getStringArray(R.array.ge);
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

        calculatePaint(gameOverPaint, string_go_to_menu, 50, SCREEN_WIDTH - calculate(200));
        calculatePaint(startPaint, string_shoot, 270, SCREEN_WIDTH - calculate(200));
        resizeButtons(new String[]{string_top, string_settings, string_start, string_quit, string_back, string_resume, string_to_menu});

        if (set) {
            buttonQuit.newFunc(string_quit, HALF_SCREEN_WIDTH + 30, buttonsY, QUIT);
            buttonMenu.newFunc(string_to_menu, HALF_SCREEN_WIDTH - 30 - buttonQuit.width, buttonsY, AFTER_SETTINGS);
        }

        makeScoresParams();
        chooseChX = (int) ((SCREEN_WIDTH - paint50.measureText(string_choose_your_character)) / 2);
        chooseChY = (int) (SCREEN_HEIGHT * 0.3);
        thanksX = (int) ((SCREEN_WIDTH - winPaint.measureText(string_thanks)) / 2);
        thanksY = (int) ((SCREEN_HEIGHT + winPaint.getTextSize()) / 2.7);
        go_to_menuX = (int) ((SCREEN_WIDTH - gameOverPaint.measureText(string_go_to_menu)) / 2);
        go_to_menuY = (int) (SCREEN_HEIGHT * 0.65);
        shootX = (int) ((SCREEN_WIDTH - startPaint.measureText(string_shoot)) / 2);
        shootY = (int) ((SCREEN_HEIGHT + startPaint.getTextSize()) / 2);
        go_to_restartX = (int) ((SCREEN_WIDTH - gameOverPaint.measureText(string_go_to_restart)) / 2);
        go_to_restartY = (int) (SCREEN_HEIGHT * 0.7);
        _3 = (int) ((SCREEN_WIDTH - startPaint.measureText("3")) / 2);
        _2 = (int) ((SCREEN_WIDTH - startPaint.measureText("2")) / 2);
        _1 = (int) ((SCREEN_WIDTH - startPaint.measureText("1")) / 2);
        _321Y = (int) ((SCREEN_HEIGHT + startPaint.getTextSize()) / 2);
    }

    private static void calculatePaint(CustomPaint paint, String text, int startValue, int textLen) {
        paint.setTextSize(startValue);

        while (paint.measureText(text) >= textLen) {
            paint.setTextSize(paint.getTextSize() - 2);
        }
    }

    private void recoverySettings() {
        String[] settings = Clerk.getSettings().split(" ");
        vibrate = Integer.parseInt(settings[2]) == 1;
        language = settings[3];

        getMaxScore();
        confirmLanguage(false);
        Clerk.getNickname();
    }

    public void saveSettings() {
        saveScore();

        if (settings != null) {
            settings.saveSettings();
        }
    }

    public void generateTable() {
        if (activity.isOnline()) {
            table = new Table(tableY);

            count = ClientServer.info_from_server.length();

            if (count != 0) {
                for (int i = 0; i < count; i++) {
                    try {
                        String curNickName = ClientServer.nicksPlayers.get(i).toString();
                        stringBuilder.append(i + 1).append(") ").append(curNickName).append(" - ")
                                .append(ClientServer.info_from_server.get(curNickName));

                        if (!Clerk.nickname.equals(curNickName)) {
                            table.addText(stringBuilder.toString());
                        } else {
                            table.addMarkedText(stringBuilder.toString());
                        }
                        stringBuilder.setLength(0);
                    } catch (Exception e) {
                        print(e);
                        table.addText((i + 1) + ") ERR - ERR");
                    }
                }
                count = 0;

                table.makeTable();
            } else {
                table = new Table(language, true);
            }
        } else {
            table = new Table(language, false);
        }
    }

    private void makeScoresParams() {
        scoreX = (int) (HALF_SCREEN_WIDTH - scorePaint.measureText(string_current_score + score) / 2);
        maxScoreX = (int) (HALF_SCREEN_WIDTH - scorePaint.measureText(string_max_score + bestScore) / 2);
    }

    public void hideSettings() {
        settings.confirmSettings();
        settings = null;
    }

    public void onLoading(Runnable function) {
        loadingScreen.launch(function, ImageHub.needImagesForFirstLevel());
    }
}
