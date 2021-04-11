package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private final SurfaceHolder holder;
    private Thread thread;
    public Canvas canvas;
    public Context context;

    public int screenWidth;
    public int screenHeight;
    public int halfScreenWidth;
    public int halfScreenHeight;
    public double resizeK;

    private static final Paint fpsPaint = new Paint();
    private static final Paint startPaint = new Paint();
    public static final Paint gameoverPaint = new Paint();
    private static final Paint scorePaint = new Paint();
    private static final Paint topPaint = new Paint();
    private static final Paint topPaintRed = new Paint();

    public static final Random random = new Random();
    public JSONObject jsonScore = new JSONObject();
    public StringBuilder scoreBuilder = new StringBuilder();
    public Vibrator vibrator;
    private static final StringBuilder textBuilder = new StringBuilder();

    public Vader[] vaders = new Vader[10];
    public Heart[] hearts = new Heart[5];
    public Explosion[] explosions = new Explosion[85];
    public ArrayList<Bullet> bullets = new ArrayList<>(0);
    public ArrayList<TripleFighter> tripleFighters = new ArrayList<>(0);
    public ArrayList<BulletEnemy> bulletEnemies = new ArrayList<>(0);
    public ArrayList<BulletBoss> bulletBosses = new ArrayList<>(0);
    public ArrayList<Boss> bosses = new ArrayList<>(0);
    public ArrayList<Buckshot> buckshots = new ArrayList<>(0);
    public ArrayList<Minion> minions = new ArrayList<>(0);
    public ArrayList<Bomb> bombs = new ArrayList<>(0);
    public Character[] player = new Character[1];

    public Screen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public Button buttonMenu;
    public PauseButton pauseButton;
    public AudioPlayer audioPlayer;
    public ImageHub imageHub;
    public FightBg fightBg;
    public HealthKit healthKit;
    public ShotgunKit shotgunKit;
    public ChangerGuns changerGuns;
    public Rocket rocket;
    public Attention attention;
    public Factory factory;
    public Demoman demoman;
    public WinScreen winScreen;
    public Portal portal;
    public ButtonPlayer buttonPlayer;
    public ButtonGunner buttonGunner;
    public HardWorker hardWorker;

    public int numberVaders = vaders.length;
    public int numberLargeExplosions = 5;
    public int numberDefaultExplosions = explosions.length - 20 - numberLargeExplosions;
    public int numberSmallExplosions = numberDefaultExplosions + 20;
    public int numberExplosionsAll = explosions.length;
    public int numberBullets = 0;
    public int numberTripleFighters = 0;
    public int numberBulletsEnemy = 0;
    public int numberBulletsBoss = 0;
    public int numberBosses = 0;
    public int numberBuckshots = 0;
    public int numberMinions = 0;
    public int numberBombs = 0;

    public int gameStatus = 1;
    public int count = 0;
    public int score = 0;
    public int lastMax = 0;
    public int pointerCount = 0;
    public int action = 0;
    public int clickX = 0;
    public int clickY = 0;
    public int moveAll = 0;
    public volatile boolean playing = false;
    public static String character = "ship";

    private static final int BOSS_TIME = 100_000;
    public static long lastBoss;
    public long pauseTimer = 0;

    private static final int MILLIS_IN_SECOND = 1_000_000_000;
    private static long timeFrame;
    private static long now;

    public Game(Context cont, AttributeSet attrs) {
        super(cont, attrs);
        context = cont;
        holder = getHolder();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void initGame(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        halfScreenWidth = screenWidth / 2;
        halfScreenHeight = screenHeight / 2;
        resizeK = (double) screenWidth / 1920;

        imageHub = new ImageHub(this);
        audioPlayer = new AudioPlayer(this);
        hardWorker = new HardWorker(this);

        fpsPaint.setColor(Color.RED);
        fpsPaint.setTextSize(40);
        startPaint.setColor(Color.WHITE);
        startPaint.setTextSize(300);
        gameoverPaint.setColor(Color.WHITE);
        gameoverPaint.setTextSize(50);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
        topPaint.setColor(Color.WHITE);
        topPaint.setTextSize(30);
        topPaintRed.setColor(Color.RED);
        topPaintRed.setTextSize(30);

        screen = new Screen(this);
        player[0] = new Player(this);

        numberVaders *= 2;
        vaders = new Vader[numberVaders];
        for (int i = 0; i < numberVaders; i++) {
            vaders[i] = new Vader(this);
        }
        for (int i = 0; i < numberExplosionsAll; i++) {
            if (i < numberDefaultExplosions) {
                explosions[i] = new Explosion(this, "default");
            } else {
                if (i < numberSmallExplosions) {
                    explosions[i] = new Explosion(this, "small");
                } else {
                    explosions[i] = new Explosion(this, "large");
                }
            }
        }
        buttonStart = new Button(this, "Start", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit = new Button(this, "Quit", (int) (buttonStart.x - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        buttonMenu = new Button(this, "Top score", (int) (buttonStart.x + 300 * resizeK), (int) (screenHeight - 70 * resizeK), "top");

        pauseButton = new PauseButton(this);

        fightBg = new FightBg(this);
        healthKit = new HealthKit(this);
        shotgunKit = new ShotgunKit(this);
        changerGuns = new ChangerGuns(this);
        rocket = new Rocket(this);
        attention = new Attention(this);
        factory = new Factory(this);
        demoman = new Demoman(this);
        portal = new Portal(this);
        buttonPlayer = new ButtonPlayer(this);
        buttonGunner = new ButtonGunner(this);

        AudioPlayer.menuMusic.start();
    }

    public void gameplay() {
        now = System.currentTimeMillis();
        moveAll = player[0].speedX / 3;

        if (screen.x < 0 & screen.x + screen.width > screenWidth) {
            screen.x -= moveAll;
        } else {
            if (screen.x >= 0) {
                screen.x -= 10;
            } else {
                if (screen.x + screen.width <= screenWidth) {
                    screen.x += 10;
                }
            }
        }
        screen.render();
        screen.update();

        if (gameStatus == 0) {
            if (now - lastBoss > BOSS_TIME) {
                lastBoss = now;
                bosses.add(new Boss(this));
                numberBosses += 1;
            }
            if (random.nextFloat() >= 0.9985 & healthKit.lock) {
                healthKit.lock = false;
            }
            if (random.nextFloat() >= 0.99 & shotgunKit.lock & score >= 50 & !shotgunKit.picked) {
                shotgunKit.lock = false;
            }
            if (random.nextFloat() >= 0.996 & attention.lock & score > 50) {
                attention.start();
            }
            if (random.nextFloat() >= 0.9991 & factory.lock & score >= 170 & numberBosses == 0) {
                factory.lock = false;
            }
            if (random.nextFloat() >= 0.9979 & demoman.lock & score > 70) {
                demoman.lock = false;
            }
        }

        if (!healthKit.lock) {
            healthKit.x -= moveAll;
            healthKit.update();
            healthKit.render();
            player[0].check_intersectionHealthKit(healthKit);
        }
        if (!shotgunKit.lock & !shotgunKit.picked) {
            shotgunKit.x -= moveAll;
            shotgunKit.update();
            shotgunKit.render();
            player[0].check_intersectionShotgunKit(shotgunKit);
        }
        if (!attention.lock) {
            attention.x -= moveAll;
            attention.update();
            attention.render();
        }
        if (!rocket.lock) {
            rocket.x -= moveAll;
            rocket.update();
            rocket.render();
            player[0].check_intersectionRocket(rocket);
        }
        if (!factory.lock) {
            factory.x -= moveAll;
            factory.update();
            factory.render();
            for (int j = 0; j < 30; j++) {
                if (j < numberBullets) {
                    factory.check_intersectionBullet(bullets.get(j));
                }
                if (j < numberBuckshots) {
                    factory.check_intersectionBullet(buckshots.get(j));
                }
            }
        }
        if (!demoman.lock) {
            demoman.x -= moveAll;
            demoman.update();
            demoman.render();
            player[0].check_intersectionDemoman(demoman);
            for (int j = 0; j < 30; j++) {
                if (j < numberBullets) {
                    demoman.check_intersectionBullet(bullets.get(j));
                }
                if (j < numberBuckshots) {
                    demoman.check_intersectionBullet(buckshots.get(j));
                }
            }
        }
        if (gameStatus == 6) {
            portal.x -= moveAll;
            portal.update();
            portal.render();
            player[0].check_intersectionPortal(portal);
        }

        for (int i = 0; i < 150; i++) {
            Vader vader = null;
            TripleFighter tripleFighter = null;
            Minion minion = null;
            Boss boss = null;

            if (i < numberVaders) {
                vader = vaders[i];
                player[0].check_intersectionVader(vader);
                vader.x -= moveAll;
                vader.update();
                vader.render();
            }

            if (i < numberTripleFighters) {
                tripleFighter = tripleFighters.get(i);
                player[0].check_intersectionTripleFighter(tripleFighter);
                tripleFighter.x -= moveAll;
                tripleFighter.update();
                tripleFighter.render();
            }

            if (i < numberMinions) {
                minion = minions.get(i);
                minion.render();
                minion.x -= moveAll;
                minion.update();
                player[0].check_intersectionMinion(minion);
            }

            if (i < numberBosses) {
                boss = bosses.get(i);
                boss.render();
                boss.x -= moveAll;
                boss.update();
                if (boss.y == -400) {
                    AudioPlayer.bossMusic.seekTo(0);
                    AudioPlayer.bossMusic.start();
                    AudioPlayer.pirateMusic.pause();
                    gameStatus = 5;
                }
            }

            for (int j = 0; j < 30; j++) {
                if (j < numberBullets) {
                    Bullet bullet = bullets.get(j);
                    if (vader != null) {
                        vader.check_intersectionBullet(bullet);
                    }
                    if (tripleFighter != null) {
                        tripleFighter.check_intersectionBullet(bullet);
                    }
                    if (minion != null) {
                        minion.check_intersectionBullet(bullet);
                    }
                    if (boss != null) {
                        boss.check_intersectionBullet(bullet);
                    }
                }
                if (j < numberBuckshots) {
                    Buckshot buckshot = buckshots.get(j);
                    if (vader != null) {
                        vader.check_intersectionBullet(buckshot);
                    }
                    if (tripleFighter != null) {
                        tripleFighter.check_intersectionBullet(buckshot);
                    }
                    if (minion != null) {
                        minion.check_intersectionBullet(buckshot);
                    }
                    if (boss != null) {
                        boss.check_intersectionBullet(buckshot);
                    }
                }
            }

            if (i < numberBulletsEnemy) {
                BulletEnemy bulletEnemy = bulletEnemies.get(i);
                bulletEnemy.render();
                bulletEnemy.x -= moveAll;
                bulletEnemy.update();
                player[0].check_intersectionBullet(bulletEnemy);
            }

            if (i < numberBombs) {
                Bomb bomb = bombs.get(i);
                bomb.render();
                bomb.x -= moveAll;
                bomb.update();
                player[0].check_intersectionBullet(bomb);
            }

            if (i < numberBulletsBoss) {
                BulletBoss bulletBoss = bulletBosses.get(i);
                bulletBoss.render();
                bulletBoss.x -= moveAll;
                bulletBoss.update();
                player[0].check_intersectionBullet(bulletBoss);
            }

            if (i < numberBullets) {
                Bullet bullet = bullets.get(i);
                bullet.render();
                bullet.x -= moveAll;
                bullet.update();
            }

            if (i < numberBuckshots) {
                Buckshot buckshot = buckshots.get(i);
                buckshot.render();
                buckshot.x -= moveAll;
                buckshot.update();
            }

            if (i < numberExplosionsAll) {
                explosions[i].x -= moveAll;
                explosions[i].update();
                explosions[i].render();
            }
        }

        player[0].update();
        player[0].render();

        pauseButton.render();
        changerGuns.render();

        if (gameStatus == 2) {
            timerStart();
        }

        textBuilder.append("Current score: ").append(score);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 50, scorePaint);

        textBuilder.setLength(0);

        textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        canvas.drawText(textBuilder.toString(), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);

        textBuilder.setLength(0);
    }

    @Override
    public void run() {
        while (playing) {
            timeFrame = System.nanoTime();
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                switch (gameStatus) {
                    case 1:
                        preview();
                        break;
                    case 6:
                    case 2:
                    case 0:
                        gameplay();
                        break;
                    case 3:
                        gameover();
                        break;
                    case 4:
                    case 5:
                        pause();
                        break;
                    case 7:
                        win();
                        break;
                    case 8:
                        topScore();
                        break;
                }
                holder.unlockCanvasAndPost(canvas);
            }
            timeFrame = System.nanoTime();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointerCount = event.getPointerCount();
        if (gameStatus != 5) {
            action = event.getActionMasked();
            clickX = (int) event.getX(0);
            clickY = (int) event.getY(0);
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    if (pointerCount >= 2) {
                        changerGuns.setCoords((int) event.getX(1), (int) event.getY(1), 2);
                        pauseButton.setCoords((int) event.getX(1), (int) event.getY(1), 2);
                    }
                    if (!player[0].dontmove) {
                        player[0].endX = clickX - player[0].halfWidth;
                        player[0].endY = clickY - player[0].halfHeight;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    if (gameStatus != 7) {
                        buttonStart.setCoords(clickX, clickY);
                        if (gameStatus != 2) {
                            buttonQuit.setCoords(clickX, clickY);
                            buttonMenu.setCoords(clickX, clickY);
                        }
                        pauseButton.setCoords(clickX, clickY);
                    }
                    if (gameStatus == 1) {
                        buttonGunner.setCoords(clickX, clickY);
                        buttonPlayer.setCoords(clickX, clickY);
                    }
                    changerGuns.setCoords(clickX, clickY);
                    break;
            }
        }
        return true;
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

    public void onPause() {
        hardWorker.workOnPause();
        playing = false;
        if (gameStatus == 1) {
            AudioPlayer.menuMusic.pause();
        } else {
            if (gameStatus == 0 | gameStatus == 2 | gameStatus == 3) {
                AudioPlayer.pirateMusic.pause();
            } else {
                if (gameStatus == 4) {
                    AudioPlayer.pauseMusic.pause();
                } else {
                    if (gameStatus == 7) {
                        AudioPlayer.winMusic.pause();
                    }
                }
            }
        }
        if (AudioPlayer.bossMusic.isPlaying()) {
            AudioPlayer.bossMusic.pause();
        }
        try {
            thread.join();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Thread join " + e);
        }
    }

    public void onResume() {
        hardWorker.workOnResume();
        checkMaxScore();
        thread = new Thread(this);
        thread.start();
        playing = true;
        if (gameStatus == 7) {
            AudioPlayer.winMusic.start();
        } else {
            if (numberBosses == 0) {
                if (gameStatus == 1) {
                    AudioPlayer.menuMusic.start();
                } else {
                    if (gameStatus == 0 | gameStatus == 2 | gameStatus == 3) {
                        AudioPlayer.pirateMusic.start();
                    } else {
                        if (gameStatus == 4) {
                            AudioPlayer.pauseMusic.start();
                        }
                    }
                }
            } else {
                AudioPlayer.bossMusic.start();
            }
        }
    }

    public void generateTopScore() {
        gameStatus = 8;
        buttonMenu = new Button(this, "Back", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 150 * resizeK), "menu");
        MainActivity.getTop();
    }

    public void generateGameover() {
        if (score > lastMax) {
            scoreBuilder.append(" ").append(score);
        }
        AudioPlayer.gameoverSnd.start();
        gameStatus = 3;
    }

    public void generatePause() {
        hardWorker.workOnPause();
        if (bosses.size() == 0) {
            AudioPlayer.pirateMusic.pause();
            AudioPlayer.pauseMusic.seekTo(0);
            AudioPlayer.pauseMusic.start();
        }
        if (gameStatus == 2) {
            AudioPlayer.readySnd.pause();
        }
        gameStatus = 4;

        player[0].dontmove = true;

        buttonStart.newFunc("Resume", halfScreenWidth - buttonQuit.halfWidth, screenHeight / 3 - buttonStart.halfHeight, "pause");
        buttonMenu.newFunc("To menu", halfScreenWidth - buttonQuit.halfWidth, buttonStart.height + buttonStart.y + 30, "menu");
        buttonQuit.newFunc("Quit", halfScreenWidth - buttonQuit.halfWidth, buttonStart.height + buttonMenu.y + 30, "quit");
    }

    public void generateMenu() {
        if (AudioPlayer.winMusic.isPlaying()) {
            AudioPlayer.winMusic.pause();
        }
        if (AudioPlayer.flightSnd.isPlaying()) {
            AudioPlayer.flightSnd.pause();
        }
        if (AudioPlayer.bossMusic.isPlaying()) {
            AudioPlayer.bossMusic.pause();
        }
        if (AudioPlayer.pauseMusic.isPlaying()) {
            AudioPlayer.pauseMusic.pause();
        }
        if (!AudioPlayer.menuMusic.isPlaying()) {
            AudioPlayer.menuMusic.pause();
            AudioPlayer.menuMusic.seekTo(0);
            AudioPlayer.menuMusic.start();
        }

        if (score > lastMax) {
            scoreBuilder.append(" ").append(score);
            saveScore();
        }
        checkMaxScore();
        count = 0;
        score = 0;
        numberBosses = 0;

        screen.x = (int) (screenWidth * -0.2);
        player[0] = new Player(this);
        shotgunKit.picked = false;

        gameStatus = 1;
        numberVaders = 20;
        vaders = new Vader[numberVaders];
        for (int i = 0; i < numberVaders; i++) {
            vaders[i] = new Vader(this);
        }
        buttonStart = new Button(this, "Start", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit = new Button(this, "Quit", (int) (buttonStart.x - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        buttonMenu = new Button(this, "Top score", (int) (buttonStart.x + 300 * resizeK), (int) (screenHeight - 70 * resizeK), "top");

        bullets = new ArrayList<>(0);
        numberBullets = 0;
        for (int i = 0; i < numberExplosionsAll; i++) {
            explosions[i].stop();
        }
    }

    public void generateNewGame() {
        if (AudioPlayer.bossMusic.isPlaying()) {
            AudioPlayer.bossMusic.pause();
        }
        saveScore();
        checkMaxScore();
        hardWorker.workOnPause();
        hardWorker.workOnResume();
        count = 0;
        score = 0;
        gameStatus = 2;

        bulletEnemies = new ArrayList<>(0);
        bulletBosses = new ArrayList<>(0);
        bosses = new ArrayList<>(0);
        tripleFighters = new ArrayList<>(0);
        bullets = new ArrayList<>(0);
        bulletEnemies = new ArrayList<>(0);
        buckshots = new ArrayList<>(0);
        minions = new ArrayList<>(0);
        bombs = new ArrayList<>(0);

        for (int i = 0; i < numberExplosionsAll; i++) {
            explosions[i].stop();
        }

        numberVaders = 12;
        numberBullets = 0;
        numberBulletsEnemy = 0;
        numberBosses = 0;
        numberBulletsBoss = 0;
        buttonStart.x = screenWidth * 2;
        buttonQuit.x = screenWidth * 2;
        numberBuckshots = 0;
        numberMinions = 0;
        numberBombs = 0;

        screen.x = (int) (screenWidth * -0.2);

        switch (character)
        {
            case "gunner":
                player[0] = new Gunner(this);
                break;
        }

        player[0].PLAYER();
        healthKit.hide();
        shotgunKit.hide();
        shotgunKit.picked = false;
        changerGuns = new ChangerGuns(this);
        attention.hide();
        rocket.hide();
        factory.hide();
        demoman.hide();
        portal.hide();
        buttonPlayer.hide();
        buttonGunner.hide();

        int c = 370;
        for (int i = 0; i < 5; i++) {
            Heart heart = new Heart(this, c, 10);
            hearts[i] = heart;
            c -= 90;
        }

        for (int i = 0; i < numberVaders; i++) {
            if (random.nextFloat() <= 0.15) {
                tripleFighters.add(new TripleFighter(this));
            }
            vaders[i] = new Vader(this);
            vaders[i].lock = true;
        }
        numberTripleFighters = tripleFighters.size();

        if (AudioPlayer.menuMusic.isPlaying()) {
            AudioPlayer.menuMusic.pause();
        } else {
            AudioPlayer.pirateMusic.pause();
        }
        AudioPlayer.pirateMusic.seekTo(0);
        AudioPlayer.pirateMusic.start();

        lastBoss = System.currentTimeMillis();
        AudioPlayer.readySnd.start();
    }

    public void gameover() {
        screen.render(ImageHub.gameoverScreen);

        if (pointerCount >= 4) {
            generateNewGame();
        }
        pauseButton.render();

        for (int i = 0; i < numberExplosionsAll; i++) {
            explosions[i].update();
            explosions[i].render();
        }

        canvas.drawText("Tap this screen with four or more fingers to restart",
                (screenWidth - gameoverPaint.measureText("Tap this screen with four or more fingers to restart")) / 2,
                (float) (screenHeight * 0.7), gameoverPaint);

        textBuilder.append("Current score: ").append(score);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 50, scorePaint);

        textBuilder.setLength(0);

        textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        canvas.drawText(textBuilder.toString(), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);

        textBuilder.setLength(0);

        textBuilder.append("Max score: ").append(lastMax);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 130, scorePaint);

        textBuilder.setLength(0);
    }

    public void pause() {
        if (pauseButton.oldStatus != 3) {
            screen.render();
            for (int i = 0; i < 150; i++) {
                if (i < numberVaders) {
                    vaders[i].render();
                }

                if (i < numberTripleFighters) {
                    tripleFighters.get(i).render();
                }

                if (i < numberMinions) {
                    minions.get(i).render();
                }

                if (i < numberBullets) {
                    bullets.get(i).render();
                }

                if (i < numberBulletsEnemy) {
                    bulletEnemies.get(i).render();
                }

                if (i < numberBombs) {
                    bombs.get(i).render();
                }

                if (i < numberBuckshots) {
                    buckshots.get(i).render();
                }

                if (i < numberBulletsBoss) {
                    bulletBosses.get(i).render();
                }

                if (i < numberExplosionsAll) {
                    explosions[i].render();
                }

                if (i < numberBosses) {
                    bosses.get(i).render();
                }
            }
            healthKit.render();
            shotgunKit.render();
            attention.render();
            rocket.render();
            factory.render();
            demoman.render();
            player[0].render();
            changerGuns.render();
            portal.render();

            if (pauseButton.oldStatus == 2) {
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
                                canvas.drawText("SHOOT!", (screenWidth - startPaint.measureText("SHOOT!")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                            }
                        }
                    }
                }
            }
        } else {
            screen.render(ImageHub.gameoverScreen);
            canvas.drawText("Tap this screen with four or more fingers to restart",
                    (screenWidth - gameoverPaint.measureText("Tap this screen with four or more fingers to restart")) / 2,
                    (float) (screenHeight * 0.7), gameoverPaint);
        }

        textBuilder.append("Current score: ").append(score);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 50, scorePaint);

        textBuilder.setLength(0);

        textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        canvas.drawText(textBuilder.toString(), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);

        textBuilder.setLength(0);

        switch (gameStatus) {
            case 4:
                textBuilder.append("Max score: ").append(lastMax);
                canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 130, scorePaint);

                textBuilder.setLength(0);

                buttonStart.render();
                buttonQuit.render();
                buttonMenu.render();
                pauseTimer += 20;
                break;
            case 5:
                if (bosses.get(bosses.size()-1).y >= -200 | pointerCount >= 4) {
                    if (portal.lock) {
                        gameStatus = 0;
                    } else {
                        gameStatus = 6;
                    }
                    bosses.get(bosses.size()-1).y = -200;
                }
                bosses.get(bosses.size()-1).update();
                fightBg.render();
                break;
        }
    }

    public void timerStart() {
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
                        canvas.drawText("SHOOT!", (screenWidth - startPaint.measureText("SHOOT!")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                    } else {
                        if (count >= 280) {
                            gameStatus = 0;
                            for (int i = 0; i < numberVaders; i++) {
                                vaders[i].lock = false;
                            }
                            for (int i = 0; i < numberTripleFighters; i++) {
                                tripleFighters.get(i).lock = false;
                            }
                            player[0].lock = false;
                            changerGuns.unHide();
                        }
                    }
                }
            }
        }
    }

    public void preview() {
        screen.update();
        screen.render();

        for (int i = 0; i < 150; i++) {
            if (i < numberVaders) {
                player[0].check_intersectionVader(vaders[i]);
                for (int j = 0; j < numberBullets; j++) {
                    vaders[i].check_intersectionBullet(bullets.get(j));
                }
                vaders[i].update();
                vaders[i].render();
            }

            if (i < numberBullets) {
                bullets.get(i).render();
                bullets.get(i).update();
            }

            if (i < numberExplosionsAll) {
                explosions[i].update();
                explosions[i].render();
            }
        }

        player[0].update();
        player[0].render();

        buttonStart.render();
        buttonQuit.render();
        buttonMenu.render();
        buttonPlayer.render();
        buttonGunner.render();

        textBuilder.append("Max score: ").append(lastMax);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 50, scorePaint);

        textBuilder.setLength(0);

        textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        canvas.drawText(textBuilder.toString(), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);

        textBuilder.setLength(0);
    }

    public void topScore() {
        try {
            screen.update();
            screen.render();

            buttonMenu.render();

            for (int i = 0; i < MainActivity.json.length(); i++) {
                String str = (i + 1) + ") " + MainActivity.names.get(i) + " - " + MainActivity.json.get(MainActivity.names.get(i).toString());
                if (MainActivity.nickname.equals(MainActivity.names.get(i))) {
                    canvas.drawText(str, halfScreenWidth - topPaint.measureText(str) / 2, (i + 1) * 45, topPaintRed);
                } else {
                    canvas.drawText(str, halfScreenWidth - topPaint.measureText(str) / 2, (i + 1) * 45, topPaint);
                }
            }

            textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText(textBuilder.toString(), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);

            textBuilder.setLength(0);
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "" + e);
        }
    }

    public void win() {
        winScreen.update();
        winScreen.render();

        if (pointerCount >= 4) {
            generateMenu();
        }
    }

    public void saveScore() {
        if (scoreBuilder.length() > 0) {
            try {
                if (score > lastMax) {
                    jsonScore = new JSONObject();
                    jsonScore.put(MainActivity.nickname, score);
                    MainActivity.postScore(jsonScore.toString());
                }

                FileOutputStream writer = context.getApplicationContext().openFileOutput("SCORE.txt", Context.MODE_PRIVATE);
                OutputStreamWriter writer_str = new OutputStreamWriter(writer);

                writer_str.write(scoreBuilder.toString());
                writer_str.close();
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "Can't save SCORE " + e);
            }
        }
    }

    public void checkMaxScore() {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(context.getApplicationContext().openFileInput("SCORE.txt"));
            BufferedReader reader_buffer = new BufferedReader(reader_cooler);
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader_buffer.readLine()) != null) {
                builder.append(line);
            }
            String[] scoreList = builder.toString().split(" ");
            for (int i = 1; i < scoreList.length; i++) {
                int j = Integer.parseInt(scoreList[i]);
                if (j > lastMax) {
                    lastMax = j;
                }
            }

            reader_cooler.close();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Can't recovery SCORE: " + e);
        }
    }
}
