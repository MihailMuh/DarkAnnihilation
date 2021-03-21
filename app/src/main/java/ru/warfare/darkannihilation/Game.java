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

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private static SurfaceHolder holder;
    private static Thread thread;
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

    public static final Random random = new Random();
    public StringBuilder scoreBuilder = new StringBuilder();
    public Vibrator vibrator;

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

    public Player player;
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
    public String curScore = "";
    public String maxScore = "";
    public volatile boolean playing = false;

    private static final int BOSS_TIME = 100_000;
    public static long lastBoss;
    private long now;
    public long pauseTimer = 0;

    public static int fps;
    private static final int MILLIS_IN_SECOND = 1_000_000_000;
    private static long timeFrame;

    public Game(Context cont, AttributeSet attrs) {
        super(cont, attrs);
        context = cont;
        holder = getHolder();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public synchronized void initGame(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        halfScreenWidth = screenWidth / 2;
        halfScreenHeight = screenHeight / 2;
        resizeK = (double) screenWidth / 1920;

        imageHub = new ImageHub(this);
        audioPlayer = new AudioPlayer(this);

        fpsPaint.setColor(Color.RED);
        fpsPaint.setTextSize(40);
        startPaint.setColor(Color.WHITE);
        startPaint.setTextSize(300);
        gameoverPaint.setColor(Color.WHITE);
        gameoverPaint.setTextSize(50);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);

        screen = new Screen(this);
        player = new Player(this);

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
        buttonStart = new Button(this, "Start", halfScreenWidth, (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit = new Button(this, "Quit", (int) (halfScreenWidth - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        buttonMenu = new Button(this, "To Menu", screenWidth * 2, 0, "menu");
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

        AudioPlayer.menuMusic.start();
    }

    public void gameplay() {
        now = System.currentTimeMillis();
        moveAll = player.speedX / 3;

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

        if (now - lastBoss > BOSS_TIME) {
            lastBoss = now;
            bosses.add(new Boss(this));
            numberBosses += 1;
        }
        if (gameStatus == 0) {
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
                factory.lock = false;;
            }
            if (random.nextFloat() >= 0.9979 & demoman.lock & score > 70) {
                demoman.lock = false;
            }
        }

        for (int i = 0; i < 150; i++) {
            if (i < numberVaders) {
                Vader vader = vaders[i];
                for (int j = 0; j < numberBullets; j++) {
                    vader.check_intersectionBullet(bullets.get(j));
                }
                for (int j = 0; j < numberBuckshots; j++) {
                    vader.check_intersectionBullet(buckshots.get(j));
                }
                vader.x -= moveAll;
                vader.update();
                vader.render();
            }

            if (i < numberTripleFighters) {
                TripleFighter tripleFighter = tripleFighters.get(i);
                for (int j = 0; j < numberBullets; j++) {
                    tripleFighter.check_intersectionBullet(bullets.get(j));
                }
                for (int j = 0; j < numberBuckshots; j++) {
                    tripleFighter.check_intersectionBullet(buckshots.get(j));
                }
                tripleFighter.x -= moveAll;
                tripleFighter.update();
                tripleFighter.render();
            }

            if (i < numberMinions) {
                Minion minion = minions.get(i);
                minion.render();
                minion.x -= moveAll;
                minion.update();
                for (int j = 0; j < numberBullets; j++) {
                    minion.check_intersectionBullet(bullets.get(j));
                }
                for (int j = 0; j < numberBuckshots; j++) {
                    minion.check_intersectionBullet(buckshots.get(j));
                }
            }

            if (i < numberBullets) {
                Bullet bullet = bullets.get(i);
                bullet.render();
                bullet.x -= moveAll;
                bullet.update();
                if (!factory.lock) {
                    factory.check_intersectionBullet(bullet);
                }
                if (!demoman.lock) {
                    demoman.check_intersectionBullet(bullet);
                }
            }

            if (i < numberBuckshots) {
                Buckshot buckshot = buckshots.get(i);
                buckshot.render();
                buckshot.x -= moveAll;
                buckshot.update();
                if (!factory.lock) {
                    factory.check_intersectionBullet(buckshot);
                }
                if (!demoman.lock) {
                    demoman.check_intersectionBullet(buckshot);
                }
            }

            if (i < numberBulletsEnemy) {
                BulletEnemy bulletEnemy = bulletEnemies.get(i);
                bulletEnemy.render();
                bulletEnemy.x -= moveAll;
                bulletEnemy.update();
            }

            if (i < numberBombs) {
                Bomb bomb = bombs.get(i);
                bomb.render();
                bomb.x -= moveAll;
                bomb.update();
            }

            if (i < numberBulletsBoss) {
                BulletBoss bulletBoss = bulletBosses.get(i);
                bulletBoss.render();
                bulletBoss.x -= moveAll;
                bulletBoss.update();
            }

            if (i < numberExplosionsAll) {
                explosions[i].x -= moveAll;
                explosions[i].update();
                explosions[i].render();
            }

            if (i < numberBosses) {
                Boss boss = bosses.get(i);
                boss.render();
                boss.x -= moveAll;
                boss.update();
                for (int j = 0; j < numberBullets; j++) {
                    boss.check_intersectionBullet(bullets.get(j));
                }
                for (int j = 0; j < numberBuckshots; j++) {
                    boss.check_intersectionBullet(buckshots.get(j));
                }
                if (boss.y == -400) {
                    AudioPlayer.bossMusic.seekTo(0);
                    AudioPlayer.bossMusic.start();
                    AudioPlayer.pirateMusic.pause();
                    gameStatus = 5;
                }
            }
        }

        if (!healthKit.lock) {
            healthKit.x -= moveAll;
            healthKit.update();
            healthKit.render();
        }
        if (!shotgunKit.lock & !shotgunKit.picked) {
            shotgunKit.x -= moveAll;
            shotgunKit.update();
            shotgunKit.render();
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
        }
        if (!factory.lock) {
            factory.x -= moveAll;
            factory.update();
            factory.render();
        }
        if (!demoman.lock) {
            demoman.x -= moveAll;
            demoman.update();
            demoman.render();
        }
        if (gameStatus == 6) {
            portal.x -= moveAll;
            portal.update();
            portal.render();
        }
        player.update();
        player.render();

        pauseButton.render();
        changerGuns.render();

        if (gameStatus == 2) {
            timerStart();
        }

        fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        curScore = "Current score: " + score;

        canvas.drawText("FPS: " + fps, screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);
        canvas.drawText(curScore, halfScreenWidth - scorePaint.measureText(curScore) / 2, 50, scorePaint);
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
                    if (!player.dontmove) {
                        player.endX = clickX - player.halfWidth;
                        player.endY = clickY - player.halfHeight;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    if (gameStatus != 7) {
                        buttonStart.mouseX = clickX;
                        buttonStart.mouseY = clickY;
                        buttonQuit.mouseX = clickX;
                        buttonQuit.mouseY = clickY;
                        buttonMenu.mouseX = clickX;
                        buttonMenu.mouseY = clickY;
                        pauseButton.setCoords(clickX, clickY);
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
            Log.e("Error ", "Thread join " + e);
        }
    }

    public void onResume() {
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

    public void generateGameover() {
        if (score > lastMax) {
            scoreBuilder.append(" ").append(score);
        }
        AudioPlayer.gameoverSnd.start();
        gameStatus = 3;
    }

    public void generatePause() {
        if (bosses.size() == 0) {
            AudioPlayer.pirateMusic.pause();
            AudioPlayer.pauseMusic.seekTo(0);
            AudioPlayer.pauseMusic.start();
        }
        if (gameStatus == 2) {
            AudioPlayer.readySnd.pause();
        }
        gameStatus = 4;

        player.dontmove = true;

        buttonStart.newFunc("Resume", halfScreenWidth - buttonQuit.halfWidth, screenHeight / 3 - buttonStart.halfHeight, "pause");
        buttonMenu.x = halfScreenWidth - buttonQuit.halfWidth;
        buttonMenu.y = buttonStart.height + buttonStart.y + 30;
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
        AudioPlayer.pauseMusic.pause();
        AudioPlayer.menuMusic.seekTo(0);
        AudioPlayer.menuMusic.start();

        if (score > lastMax) {
            scoreBuilder.append(" ").append(score);
            saveScore();
        }
        checkMaxScore();
        count = 0;
        score = 0;
        numberBosses = 0;

        screen.x = (int) (screenWidth * -0.2);
        player.AI();
        shotgunKit.picked = false;

        gameStatus = 1;
        numberVaders *= 2;
        vaders = new Vader[numberVaders];
        for (int i = 0; i < numberVaders; i++) {
            vaders[i] = new Vader(this);
        }
        buttonStart.newFunc("Start", halfScreenWidth, (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit.newFunc("Quit", (int) (halfScreenWidth - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        buttonMenu.x = screenWidth * 2;

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
        player.PLAYER();
        healthKit.hide();
        shotgunKit.hide();
        shotgunKit.picked = false;
        changerGuns = new ChangerGuns(this);
        attention.hide();
        rocket.hide();
        factory.hide();
        demoman.hide();
        portal.hide();

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

        fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        curScore = "Current score: " + String.valueOf(score);
        maxScore = "Max score: " + String.valueOf(lastMax);

        canvas.drawText("Tap this screen with four or more fingers to restart",
                (screenWidth - gameoverPaint.measureText("Tap this screen with four or more fingers to restart")) / 2,
                (float) (screenHeight * 0.7), gameoverPaint);
        canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);
        canvas.drawText(curScore, halfScreenWidth - scorePaint.measureText(curScore) / 2, 50, scorePaint);
        canvas.drawText(maxScore, halfScreenWidth - scorePaint.measureText(maxScore) / 2, 130, scorePaint);
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
            player.render();
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

        fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        curScore = "Current score: " + String.valueOf(score);
        maxScore = "Max score: " + String.valueOf(lastMax);


        canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);
        canvas.drawText(curScore, halfScreenWidth - scorePaint.measureText(curScore) / 2, 50, scorePaint);
        switch (gameStatus) {
            case 4:
                canvas.drawText(maxScore, halfScreenWidth - scorePaint.measureText(maxScore) / 2, 130, scorePaint);
                buttonStart.update();
                buttonStart.render();
                buttonQuit.update();
                buttonQuit.render();
                buttonMenu.update();
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
                            player.lock = false;
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

        player.update();
        player.render();

        for (int i = 0; i < 100; i++) {
            if (i < numberVaders) {
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

        buttonStart.update();
        buttonStart.render();
        buttonQuit.update();
        buttonQuit.render();

        fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        canvas.drawText("FPS: " + fps, screenWidth - 250, 50, fpsPaint);

        maxScore = "Max score: " + lastMax;
        canvas.drawText(maxScore, halfScreenWidth - scorePaint.measureText(maxScore) / 2, 50, scorePaint);
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
                FileOutputStream writer = context.getApplicationContext().openFileOutput("SCORE.txt", Context.MODE_PRIVATE);
                OutputStreamWriter writer_str = new OutputStreamWriter(writer);

                writer_str.write(scoreBuilder.toString());
                writer_str.close();
            } catch (IOException e) {
                Log.e("Error ", "Can't save SCORE " + e);
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
            Log.e("Error ", "Can't recovery SCORE: " + e);
        }
    }
}
