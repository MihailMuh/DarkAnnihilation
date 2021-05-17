package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

@SuppressLint("ViewConstructor")
public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private final SurfaceHolder holder;
    public final Context context;
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
    public static final Paint blackPaint = new Paint();
    public static final Paint alphaPaint = new Paint();

    public static final Random random = new Random();
    private static final StringBuilder textBuilder = new StringBuilder();

    public static final BaseExplosion[] allExplosions = new BaseExplosion[73];
    public static ArrayList<Sprite> bullets = new ArrayList<>(0);
    public static ArrayList<Sprite> bosses = new ArrayList<>(0);
    public static ArrayList<Sprite> allSprites = new ArrayList<>(0);

    public BaseScreen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public Button buttonMenu;
    public Button buttonRestart;
    public PauseButton pauseButton;
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
    public ButtonSaturn buttonSaturn;
    public HardWorker hardWorker;
    public BaseCharacter player;
    public LoadingScreen loadingScreen;
    public Spider spider;
    public Sunrise sunrise;
    public Buffer buffer;

    public static final int numberVaders = 10;
    public static final int numberMediumExplosionsTriple = 20;
    public static final int numberSmallExplosionsTriple = 15 + numberMediumExplosionsTriple;
    public static final int numberMediumExplosionsDefault = numberSmallExplosionsTriple + 20;
    public static final int numberSmallExplosionsDefault = numberMediumExplosionsDefault + 15;
    public static final int numberExplosionsALL = 73;

    public static int level = 1;
    public static int gameStatus;
    private int count = 0;
    public static int score = 0;
    public int bestScore = 0;
    private int pointerCount = 0;
    private int moveAll = 0;
    private boolean playing = false;
    public static String character = "falcon";
    public static volatile boolean endImgInit = false;

    private static final int BOSS_TIME = 100_000;
    public static long lastBoss;
    public long pauseTimer = 0;

    private static final int MILLIS_IN_SECOND = 1_000_000_000;
    private long timeFrame;

    public Game(Context cont, int width, int height) {
        super(cont);
        context = cont;
        holder = getHolder();

        screenWidth = width;
        screenHeight = height;
        halfScreenWidth = screenWidth / 2;
        halfScreenHeight = screenHeight / 2;
        resizeK = (double) screenWidth / 1920;

        getMaxScore();

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
        blackPaint.setColor(Color.BLACK);
        blackPaint.setAlpha(0);

        while (!endImgInit) {}

        for (int i = 0; i < numberVaders * 2; i++) {
            allSprites.add(new Vader());
        }
        buttonStart = new Button(this, "Start", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit = new Button(this, "Quit", (int) (buttonStart.x - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        buttonMenu = new Button(this, "Top score", (int) (buttonStart.x + 300 * resizeK), (int) (screenHeight - 70 * resizeK), "top");
        buttonRestart = new Button(this, "Restart", screenWidth, 0, "restart");
        buttonPlayer = new ButtonPlayer(this);
        buttonSaturn = new ButtonSaturn(this);
        pauseButton = new PauseButton(this);
        player = new MillenniumFalcon(this);
        fightBg = new FightBg();
        healthKit = new HealthKit(this);
        shotgunKit = new ShotgunKit(this);
        changerGuns = new ChangerGuns(this);
        rocket = new Rocket();
        attention = new Attention(this);
        factory = new Factory();
        demoman = new Demoman();
        spider = new Spider();
        sunrise = new Sunrise();
        buffer = new Buffer();
        hardWorker = new HardWorker(this);
        screen = new StarScreen();
        loadingScreen = new LoadingScreen(this);

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

        AudioPlayer.menuMusic.start();

        gameStatus = 1;
    }

    public void gameplay() {
        long now = System.currentTimeMillis();
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
            Sprite sprite = allSprites.get(i);
            if (sprite != null) {
                if (!sprite.lock) {
                    sprite.x -= moveAll;
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
                    if (sprite.status.equals("bulletEnemy")) {
                        for (int j = 0; j < bullets.size(); j++) {
                            Sprite bullet = bullets.get(j);
                            if (bullet.status.equals("saturn")) {
                                if (sprite.getRect().intersect(bullet.getRect())) {
                                    sprite.intersectionPlayer();
                                    bullet.intersection();
                                    break;
                                }
                            }
                        }
                    }
                    if (sprite.status.equals("rocket")) {
                        for (int j = 0; j < bullets.size(); j++) {
                            Sprite bullet = bullets.get(j);
                            if (bullet.status.equals("saturn")) {
                                if (sprite.getRect().intersect(bullet.getRect())) {
                                    bullet.intersection();
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
            Sprite boss = null;
            switch (level)
            {
                case 1:
                    boss = new Boss(this);
                    fightBg.newImg(character);
                    break;
                case 2:
                    boss = new BossVaders(this);
                    fightBg.newImg(character + "-vaders");
                    break;
            }
            bosses.add(boss);
            allSprites.add(boss);
            for (int i = 0; i < bullets.size(); i++) {
                Sprite bullet = bullets.get(i);
                if (bullet.status.equals("saturn")) {
                    if (bullet.getDistance() >= screenWidth - 100) {
                        bullets.remove(bullet);
                        allSprites.remove(bullet);
                    }
                }
            }
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
        switch (level)
        {
            case 1:
                if (attention.lock & random.nextFloat() >= 0.996 & score > 50) {
                    attention.start();
                }
                if (factory.lock & random.nextFloat() >= 0.9991 & score >= 170 & bosses.size() == 0) {
                    factory.lock = false;
                }
                if (demoman.lock & random.nextFloat() >= 0.9979 & score > 70) {
                    demoman.lock = false;
                }
                break;
            case 2:
                if (spider.lock & random.nextFloat() >= 0.998) {
                    spider.lock = false;
                }
                if (bosses.size() == 0) {
                    if (sunrise.lock & random.nextFloat() >= 0.9991) {
                        sunrise.lock = false;
                    }
                    if (buffer.lock & random.nextFloat() >= 0.999) {
                        buffer.lock = false;
                    }
                }
                break;
        }

        pauseButton.render();
        changerGuns.render();

        renderFPS();
        renderCurrentScore();
    }

    @Override
    public void run() {
        while (playing) {
            timeFrame = System.nanoTime();
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                switch (gameStatus) {
                    case 0:
                        gameplay();
                        break;
                    case 1:
                        preview();
                        break;
                    case 2:
                        ready();
                        break;
                    case 3:
                        gameover();
                        break;
                    case 4:
                        pause();
                        break;
                    case 5:
                        bossIncoming();
                        break;
                    case 6:
                        portalTime();
                        break;
                    case 9:
                        afterPause();
                        break;
                    case 7:
                        win();
                        break;
                    case 8:
                        topScore();
                        break;
                    case 41:
                        onLoading();
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
        pointerCount = event.getPointerCount();
        int clickX = (int) event.getX(0);
        int clickY = (int) event.getY(0);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                switch (gameStatus)
                {
                    case 1:
                        buttonSaturn.setCoords(clickX, clickY);
                        buttonPlayer.setCoords(clickX, clickY);
                        buttonStart.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        buttonMenu.setCoords(clickX, clickY);
                        break;
                    case 4:
                        buttonRestart.setCoords(clickX, clickY);
                        buttonStart.setCoords(clickX, clickY);
                        buttonQuit.setCoords(clickX, clickY);
                        buttonMenu.setCoords(clickX, clickY);
                        break;
                    case 8:
                        buttonMenu.setCoords(clickX, clickY);
                        break;
                }

                boolean pb = false;
                boolean cg = false;
                if (gameStatus == 2 | gameStatus == 0 | gameStatus == 3) {
                    pb = pauseButton.checkCoords(clickX, clickY);
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
                if (pointerCount >= 2) {
                    changerGuns.setCoords((int) event.getX(1), (int) event.getY(1));
                }
                if ((gameStatus == 6 | gameStatus == 2 | gameStatus == 0) & !player.dontmove) {
                    player.endX = clickX - player.halfWidth;
                    player.endY = clickY - player.halfHeight;
                }
                break;
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
        if (gameStatus == 7) {
            AudioPlayer.winMusic.pause();
        }
        AudioPlayer.pauseMenuMusic();
        AudioPlayer.pauseBossMusic();
        AudioPlayer.pauseBackgroundMusic();
        AudioPlayer.pausePauseMusic();
        playing = false;
        try {
            thread.join();
        } catch (Exception e) {
            Service.print("Thread join " + e);
        }
    }

    public void onResume() {
        hardWorker.workOnResume();
        if (gameStatus == 7) {
            AudioPlayer.winMusic.start();
        } else {
            if (bosses.size() == 0) {
                switch (gameStatus)
                {
                    case 0:
                    case 2:
                    case 3:
                        AudioPlayer.resumeBackgroundMusic();
                        break;
                    case 1:
                        AudioPlayer.menuMusic.start();
                        break;
                    case 4:
                        AudioPlayer.pauseMusic.start();
                        break;
                }
            } else {
                AudioPlayer.resumeBossMusic();
            }
        }
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void generateTopScore() {
        buttonPlayer.hide();
        buttonSaturn.hide();
        buttonMenu.newFunc("Back", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 150 * resizeK), "menu");
        gameStatus = 8;
    }

    public void generateWin() {
        gameStatus = 7;
        AudioPlayer.portalSound.pause();
        AudioPlayer.winMusic.seekTo(0);
        AudioPlayer.winMusic.start();
        winScreen = new WinScreen();
        saveScore();
    }

    public void generateGameover() {
        gameStatus = 42;
        level = 1;
        saveScore();
        getMaxScore();
        AudioPlayer.gameoverSnd.start();
        gameStatus = 3;
    }

    public void generatePause() {
        hardWorker.workOnPause();
        AudioPlayer.pauseBackgroundMusic();
        if (bosses.size() == 0) {
            AudioPlayer.restartPauseMusic();
        }
        AudioPlayer.pauseReadySound();

        int X = halfScreenWidth - buttonQuit.halfWidth;

        buttonStart.newFunc("Resume", X, screenHeight / 3 - buttonStart.halfHeight, "pause");
        buttonRestart.newFunc("Restart", X, buttonStart.height + buttonStart.y + 30, "restart");
        buttonMenu.newFunc("To menu", X, buttonStart.height + buttonRestart.y + 30, "menu");
        buttonQuit.newFunc("Quit", X, buttonStart.height + buttonMenu.y + 30, "quit");
        gameStatus = 4;
    }

    public void generateMenu() {
        alphaPaint.setAlpha(255);

        if (AudioPlayer.winMusic.isPlaying()) {
            AudioPlayer.winMusic.pause();
        }

        AudioPlayer.pauseReadySound();
        AudioPlayer.pauseFlightMusic();
        AudioPlayer.restartMenuMusic();
        AudioPlayer.pausePauseMusic();
        AudioPlayer.pauseBackgroundMusic();
        AudioPlayer.pauseBossMusic();

        ImageHub.deleteThunderImages();
        ImageHub.deleteWinImages();
        if (ImageHub.needAStar()) {
            ImageHub.loadScreenImages(context);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Service.print(e.toString());
            }
        }

        getMaxScore();
        count = 0;
        score = 0;
        level = 1;

        allSprites = new ArrayList<>(0);
        bullets = new ArrayList<>(0);
        bosses = new ArrayList<>(0);

        screen = new StarScreen();
        player = new MillenniumFalcon(this);
        shotgunKit.picked = false;

        for (int i = 0; i < numberVaders * 2; i++) {
            allSprites.add(new Vader());
        }

        buttonStart.newFunc("Start", (int) (halfScreenWidth - 150 * resizeK), (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit.newFunc("Quit", (int) (buttonStart.x - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        buttonMenu.newFunc("Top score", (int) (buttonStart.x + 300 * resizeK), (int) (screenHeight - 70 * resizeK), "top");

        for (int i = 0; i < numberExplosionsALL; i++) {
            allExplosions[i].stop();
            allSprites.add(allExplosions[i]);
        }
        gameStatus = 1;
    }

    public void generateNewGame() {
        AudioPlayer.pauseReadySound();
        AudioPlayer.pauseBossMusic();
        AudioPlayer.pausePauseMusic();

        hardWorker.workOnPause();
        hardWorker.workOnResume();
        count = 0;

        bosses = new ArrayList<>(0);
        bullets = new ArrayList<>(0);
        allSprites = new ArrayList<>(0);

        if ("saturn".equals(character)) {
            player = new Saturn(this);
        }

        shotgunKit.hide();
        healthKit.hide();
        attention.hide();
        rocket.hide();
        factory.hide();
        demoman.hide();
        buttonPlayer.hide();
        buttonSaturn.hide();
        pauseButton.show();
        spider.hide();
        sunrise.hide();
        player.PLAYER();
        buffer.hide();
        if (portal != null) {
            portal.kill();
        }

        allSprites.add(healthKit);
        for (int i = 0; i < numberExplosionsALL; i++) {
            allExplosions[i].stop();
            allSprites.add(allExplosions[i]);
        }

        switch (level)
        {
            case 1:
                score = 0;

                ImageHub.deleteThunderImages();
                ImageHub.deleteWinImages();
                if (ImageHub.needAStar()) {
                    ImageHub.loadScreenImages(context);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Service.print(e.toString());
                    }
                }

                shotgunKit.picked = false;
                screen = new StarScreen();
                alphaPaint.setAlpha(255);

                allSprites.add(demoman);
                allSprites.add(factory);
                allSprites.add(rocket);
                allSprites.add(attention);

                gameStatus = 2;
                allSprites.add(new TripleFighter());
                for (int i = 0; i < numberVaders; i++) {
                    if (random.nextFloat() <= 0.15) {
                        allSprites.add(new TripleFighter());
                    }
                    allSprites.add(new Vader());
                }
                break;
            case 2:
                ImageHub.deleteScreenImages();

                screen = new ThunderScreen();
                alphaPaint.setAlpha(165);

                gameStatus = 2;
                for (int i = 0; i < numberVaders + 3; i++) {
                    if (random.nextFloat() <= 0.2) {
                        allSprites.add(new XWing(this));
                    }
                    allSprites.add(new Vader());
                }

                allSprites.add(spider);
                allSprites.add(sunrise);
                allSprites.add(buffer);
                break;
        }
        changerGuns.hide();

        AudioPlayer.restartBackgroundMusic();
        AudioPlayer.restartReadySound();
    }

    public void renderSprites() {
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

    public void renderFPS() {
        textBuilder.append("FPS: ").append(MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
        canvas.drawText(textBuilder.toString(), screenWidth - 250, 170, fpsPaint);

        textBuilder.setLength(0);
    }

    public void renderCurrentScore() {
        textBuilder.append("Current score: ").append(score);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 50, scorePaint);

        textBuilder.setLength(0);
    }

    public void renderMaxScore() {
        textBuilder.append("Max score: ").append(bestScore);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 50, scorePaint);

        textBuilder.setLength(0);
    }

    public void gameover() {
        screen.render(ImageHub.gameoverScreen);

        pauseButton.render();

        for (int i = 0; i < numberExplosionsALL; i++) {
            if (!allExplosions[i].lock) {
                allExplosions[i].render();
                allExplosions[i].update();
            }
        }

        canvas.drawText("Tap this screen with four or more fingers to restart",
                (screenWidth - gameoverPaint.measureText("Tap this screen with four or more fingers to restart")) / 2,
                (float) (screenHeight * 0.7), gameoverPaint);

        renderFPS();
        renderCurrentScore();

        textBuilder.append("Max score: ").append(bestScore);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 130, scorePaint);

        textBuilder.setLength(0);

        if (pointerCount >= 4) {
            loadingScreen.newJob("newGame");
        }
    }

    public void bossIncoming() {
        renderSprites();
        renderFPS();
        renderCurrentScore();
        Sprite boss = bosses.get(bosses.size()-1);
        if (boss.y >= -200 | pointerCount >= 4) {
            if (portal == null) {
                gameStatus = 0;
            } else {
                gameStatus = 6;
            }
            boss.y = -boss.height;
        }
        boss.update();
        fightBg.render();
    }

    public void afterPause() {
        renderSprites();
        renderFPS();
        renderCurrentScore();

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
    }

    public void pause() {
        if (PauseButton.oldStatus != 3) {
            renderSprites();

            if (PauseButton.oldStatus == 2) {
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

        renderFPS();
        renderCurrentScore();

        textBuilder.append("Max score: ").append(bestScore);
        canvas.drawText(textBuilder.toString(), halfScreenWidth - scorePaint.measureText(textBuilder.toString()) / 2, 130, scorePaint);

        textBuilder.setLength(0);

        buttonStart.render();
        buttonQuit.render();
        buttonMenu.render();
        buttonRestart.render();
        pauseTimer += 20;
    }

    public void ready() {
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
        screen.update();
        screen.render();

        player.update();
        player.render();

        pauseButton.render();

        renderCurrentScore();
        renderFPS();

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
        buttonSaturn.render();

        renderMaxScore();
        renderFPS();

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

            for (int i = 0; i < ClientServer.info_from_server.length(); i++) {
                String str = (i + 1) + ") " + ClientServer.namesPlayers.get(i) +
                        " - " + ClientServer.info_from_server.get(ClientServer.namesPlayers.get(i).toString());
                if (Clerk.nickname.equals(ClientServer.namesPlayers.get(i))) {
                    canvas.drawText(str, halfScreenWidth - topPaint.measureText(str) / 2, (i + 1) * 45, topPaintRed);
                } else {
                    canvas.drawText(str, halfScreenWidth - topPaint.measureText(str) / 2, (i + 1) * 45, topPaint);
                }
            }

            renderFPS();
        } catch (Exception e) {
            Service.print(e.toString());
        }
    }

    public void win() {
        winScreen.update();
        winScreen.render();

        if (pointerCount >= 4) {
            loadingScreen.newJob("menu");
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
            Sprite sprite = allSprites.get(i);
            if (sprite != null) {
                if (!sprite.lock) {
                    sprite.x -= moveAll;
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
                    if (sprite.status.equals("bulletEnemy")) {
                        for (int j = 0; j < bullets.size(); j++) {
                            Sprite bullet = bullets.get(j);
                            if (bullet.status.equals("saturn")) {
                                if (sprite.getRect().intersect(bullet.getRect())) {
                                    sprite.intersectionPlayer();
                                    bullet.intersection();
                                    break;
                                }
                            }
                        }
                    }
                    if (sprite.status.equals("rocket")) {
                        for (int j = 0; j < bullets.size(); j++) {
                            Sprite bullet = bullets.get(j);
                            if (bullet.status.equals("saturn")) {
                                if (sprite.getRect().intersect(bullet.getRect())) {
                                    bullet.intersection();
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

        renderFPS();
        renderCurrentScore();

        if (!portal.touch) {
            player.checkIntersections(portal);
        }
        if (portal != null) {
            portal.x -= moveAll;
            portal.update();
            portal.render();
        }
    }

    public void saveScore() {
        if (score > bestScore) {
            Clerk.saveBestScore(score);
        }
    }

    public void getMaxScore() {
        bestScore = Clerk.getMaxScore();
    }

    public void onLoading() {
        loadingScreen.render();
        loadingScreen.update();
    }
}
