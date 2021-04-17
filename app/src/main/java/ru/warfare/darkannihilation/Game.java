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

@SuppressLint("ViewConstructor")
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

    public static final Paint fpsPaint = new Paint();
    public static final Paint startPaint = new Paint();
    public static final Paint gameoverPaint = new Paint();
    public static final Paint scorePaint = new Paint();
    public static final Paint topPaint = new Paint();
    public static final Paint topPaintRed = new Paint();

    public static final Random random = new Random();
    public static JSONObject jsonScore = new JSONObject();
    public static final StringBuilder scoreBuilder = new StringBuilder();
    public final Vibrator vibrator;
    private static final StringBuilder textBuilder = new StringBuilder();

    public Explosion[] allExplosions = new Explosion[73];
    public ArrayList<BulletBase> bullets = new ArrayList<>(0);
    public ArrayList<Boss> bosses = new ArrayList<>(0);
    public ArrayList<Sprite> allSprites = new ArrayList<>(0);

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
    public Character player;

    public final int numberVaders = 10;
    public int numberMediumExplosionsTriple = 20;
    public int numberSmallExplosionsTriple = 15 + numberMediumExplosionsTriple;
    public int numberMediumExplosionsDefault = numberSmallExplosionsTriple + 20;
    public int numberSmallExplosionsDefault = numberMediumExplosionsDefault + 15;
    public int numberExplosionsALL = allExplosions.length;
    public int numberBosses = 0;

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

    public Game(Context cont, int width, int height) {
        super(cont);
        context = cont;
        holder = getHolder();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

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

        screenWidth = width;
        screenHeight = height;
        halfScreenWidth = screenWidth / 2;
        halfScreenHeight = screenHeight / 2;
        resizeK = (double) screenWidth / 1920;

        imageHub = new ImageHub(this);
        audioPlayer = new AudioPlayer(this);
        hardWorker = new HardWorker(this);

        screen = new Screen(this);
        player = new Player(this);

        for (int i = 0; i < numberVaders * 2; i++) {
            allSprites.add(new Vader(this));
        }
        for (int i = 0; i < numberExplosionsALL; i++) {
            if (i < numberMediumExplosionsTriple) {
                allExplosions[i] = new ExplosionTriple(this, "default");
            } else {
                if (i < numberSmallExplosionsTriple) {
                    allExplosions[i] = new ExplosionTriple(this, "small");
                } else {
                    if (i < numberMediumExplosionsDefault) {
                        allExplosions[i] = new DefaultExplosion(this, "default");
                    } else {
                        if (i < numberSmallExplosionsDefault) {
                            allExplosions[i] = new DefaultExplosion(this, "small");
                        } else {
                            allExplosions[i] = new ExplosionSkull(this);
                        }
                    }
                }
            }
            allSprites.add(allExplosions[i]);
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

        allSprites.add(factory);
        allSprites.add(demoman);
        allSprites.add(healthKit);
        allSprites.add(rocket);
        allSprites.add(attention);

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

        if (gameStatus == 0) {
            if (now - lastBoss > BOSS_TIME) {
                lastBoss = now;
                Boss boss = new Boss(this);
                bosses.add(boss);
                allSprites.add(boss);
                numberBosses += 1;
            }

            if (!shotgunKit.picked) {
                if (!shotgunKit.lock) {
                    shotgunKit.x -= moveAll;
                    shotgunKit.update();
                    shotgunKit.render();
                    player.checkIntersections(shotgunKit);
                } else {
                    if (random.nextFloat() >= 0.99 & score >= 50) {
                        shotgunKit.lock = false;
                    }
                }
            }

            if (healthKit.lock & random.nextFloat() >= 0.9985) {
                healthKit.lock = false;
            }
            if (attention.lock & random.nextFloat() >= 0.996 & score > 50) {
                attention.start();
            }
            if (factory.lock & random.nextFloat() >= 0.9991 & score >= 170 & numberBosses == 0) {
                factory.lock = false;
            }
            if (demoman.lock & random.nextFloat() >= 0.9979 & score > 70) {
                demoman.lock = false;
            }
        }

        if (gameStatus == 6) {
            portal.x -= moveAll;
            portal.update();
            portal.render();
            player.checkIntersections(portal);
        }

        for (int i = 0; i < allSprites.size(); i++) {
            Sprite sprite = allSprites.get(i);
            if (!sprite.lock) {
                sprite.render();
                sprite.update();
                sprite.x -= moveAll;
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
                    case 6:
                    case 2:
                    case 0:
                        gameplay();
                        break;
                    case 1:
                        preview();
                        break;
                    case 3:
                        gameover();
                        break;
                    case 4:
                    case 5:
                    case 9:
                        pause();
                        break;
                    case 7:
                        win();
                        break;
                    case 8:
                        topScore();
                        break;
                    case 42:
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
        synchronized (getHolder()) {
            pointerCount = event.getPointerCount();
            action = event.getActionMasked();
            clickX = (int) event.getX(0);
            clickY = (int) event.getY(0);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (gameStatus != 0) {
                        buttonStart.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        buttonMenu.setCoords(clickX, clickY);
                    }
                    if (gameStatus == 1) {
                        buttonGunner.setCoords(clickX, clickY);
                        buttonPlayer.setCoords(clickX, clickY);
                    }
                    pauseButton.setCoords(clickX, clickY);
                    changerGuns.setCoords(clickX, clickY, 1);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (pointerCount >= 2) {
                        changerGuns.setCoords((int) event.getX(1), (int) event.getY(1));
                        pauseButton.setCoords((int) event.getX(1), (int) event.getY(1));
                    }
                    if ((gameStatus == 6 | gameStatus == 2 | gameStatus == 0) & !player.dontmove) {
                        player.endX = clickX - player.halfWidth;
                        player.endY = clickY - player.halfHeight;
                    }
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
        gameStatus = 42;
        pauseButton.x = screenWidth;
        buttonStart.x = screenWidth * 2;
        buttonQuit.x = screenWidth * 2;
        buttonMenu.x = screenWidth * 2;
        buttonMenu = new Button(this, "Back", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 150 * resizeK), "menu");
        MainActivity.getTop();
        gameStatus = 8;
    }

    public void generateGameover() {
        gameStatus = 42;
        buttonStart.x = screenWidth * 2;
        buttonQuit.x = screenWidth * 2;
        buttonMenu.x = screenWidth * 2;
        if (score > lastMax) {
            scoreBuilder.append(" ").append(score);
        }
        AudioPlayer.gameoverSnd.start();
        gameStatus = 3;
    }

    public void generatePause() {
        gameStatus = 42;
        hardWorker.workOnPause();
        if (bosses.size() == 0) {
            AudioPlayer.pirateMusic.pause();
            AudioPlayer.pauseMusic.seekTo(0);
            AudioPlayer.pauseMusic.start();
        }
        if (gameStatus == 2) {
            AudioPlayer.readySnd.pause();
        }

        pauseButton.x = screenWidth;
        buttonStart.newFunc("Resume", halfScreenWidth - buttonQuit.halfWidth, screenHeight / 3 - buttonStart.halfHeight, "pause");
        buttonMenu.newFunc("To menu", halfScreenWidth - buttonQuit.halfWidth, buttonStart.height + buttonStart.y + 30, "menu");
        buttonQuit.newFunc("Quit", halfScreenWidth - buttonQuit.halfWidth, buttonStart.height + buttonMenu.y + 30, "quit");
        gameStatus = 4;
    }

    public void generateMenu() {
        gameStatus = 42;
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
        if (AudioPlayer.readySnd.isPlaying()) {
            AudioPlayer.readySnd.pause();
            AudioPlayer.readySnd.seekTo(0);
        }

        if (score > lastMax) {
            scoreBuilder.append(" ").append(score);
            saveScore();
        }
        checkMaxScore();
        count = 0;
        score = 0;
        numberBosses = 0;

        allSprites = new ArrayList<>(0);
        bullets = new ArrayList<>(0);

        screen.x = (int) (screenWidth * -0.2);
        player = new Player(this);
        shotgunKit.picked = false;

        for (int i = 0; i < numberVaders * 2; i++) {
            allSprites.add(new Vader(this));
        }

        pauseButton.x = screenWidth;
        buttonStart = new Button(this, "Start", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit = new Button(this, "Quit", (int) (buttonStart.x - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        buttonMenu = new Button(this, "Top score", (int) (buttonStart.x + 300 * resizeK), (int) (screenHeight - 70 * resizeK), "top");

        for (int i = 0; i < numberExplosionsALL; i++) {
            allExplosions[i].stop();
            allSprites.add(allExplosions[i]);
        }
        gameStatus = 1;
    }

    public void generateNewGame() {
        gameStatus = 42;
        if (AudioPlayer.bossMusic.isPlaying()) {
            AudioPlayer.bossMusic.pause();
        }
        if (AudioPlayer.menuMusic.isPlaying()) {
            AudioPlayer.menuMusic.pause();
        } else {
            AudioPlayer.pirateMusic.pause();
        }
        AudioPlayer.pirateMusic.seekTo(0);

        saveScore();
        checkMaxScore();
        hardWorker.workOnPause();
        hardWorker.workOnResume();
        count = 0;
        score = 0;

        bosses = new ArrayList<>(0);
        allSprites = new ArrayList<>(0);

        numberBosses = 0;
        pauseButton.show();
        buttonStart.x = screenWidth * 2;
        buttonQuit.x = screenWidth * 2;
        buttonMenu.x = screenWidth * 2;

        screen.x = (int) (screenWidth * -0.2);

        if ("gunner".equals(character)) {
            player = new Gunner(this);
        }
//        switch (character)
//        {
//            case "gunner":
//                player[0] = new Gunner(this);
//                break;
//        }
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
        buttonPlayer.hide();
        buttonGunner.hide();

        allSprites.add(demoman);
        allSprites.add(factory);
        allSprites.add(healthKit);
        allSprites.add(rocket);
        allSprites.add(attention);
        for (int i = 0; i < numberExplosionsALL; i++) {
            allExplosions[i].stop();
            allSprites.add(allExplosions[i]);
        }

        gameStatus = 2;
        for (int i = 0; i < numberVaders; i++) {
            if (random.nextFloat() <= 0.15) {
                allSprites.add(new TripleFighter(this));
            }
            Vader v = new Vader(this);
            v.lock = true;
            allSprites.add(v);
        }

        AudioPlayer.pirateMusic.start();
        AudioPlayer.readySnd.start();
    }

    public void gameover() {
        screen.render(ImageHub.gameoverScreen);

        pauseButton.render();

        for (int i = 0; i < numberExplosionsALL; i++) {
            if (!allExplosions[i].lock) {
                allExplosions[i].update();
                allExplosions[i].render();
            }
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
        if (pointerCount >= 4) {
            generateNewGame();
        }
    }

    public void pause() {
        if (pauseButton.oldStatus != 3) {
            screen.render();
            for (int i = 0; i < allSprites.size(); i++) {
                if (!allSprites.get(i).lock) {
                    allSprites.get(i).render();
                }
            }
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
            case 9:
                count += 1;
                if (0 <= count & count < 23) {
                    canvas.drawText("3", (screenWidth - startPaint.measureText("1")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                } else {
                    if (23 <= count & count < 46) {
                        canvas.drawText("2", (screenWidth - startPaint.measureText("2")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                    } else {
                        if (46 <= count & count < 69) {
                            canvas.drawText("1", (screenWidth - startPaint.measureText("3")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                        } else {
                            if (count >= 70) {
                                pauseButton.show();
                                gameStatus = 0;
                                count = 0;
                            }
                        }
                    }
                }
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
                            for (int i = 0; i < allSprites.size(); i++) {
                                allSprites.get(i).empireStart();
                            }
                            player.lock = false;
                            changerGuns.unHide();
                            count = 0;
                            lastBoss = System.currentTimeMillis();
                        }
                    }
                }
            }
        }
    }

    public void preview() {
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
        buttonPlayer.render();
        buttonGunner.render();

        textBuilder.append("Max score: ").append(lastMax);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 50, scorePaint);

        textBuilder.setLength(0);

        textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        canvas.drawText(textBuilder.toString(), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);

        textBuilder.setLength(0);

        if (buttonPlayer.x < screenWidth) {
            canvas.drawText("Choose your character",
                    (screenWidth - Game.gameoverPaint.measureText("Choose your character")) / 2,
                    (float) (screenHeight * 0.3), Game.gameoverPaint);
        }
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
