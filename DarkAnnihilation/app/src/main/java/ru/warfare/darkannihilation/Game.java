package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PixelCopy;
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
    public static SurfaceHolder holder;
    public static Thread thread;
    public Canvas canvas;
    public Context context;

    public int screenWidth;
    public int screenHeight;
    public int halfScreenWidth;
    public int halfScreenHeight;
    public double resizeK;

    private static final Paint fpsPaint = new Paint();
    private static final Paint startPaint = new Paint();
    private static final Paint gameoverPaint = new Paint();
    private static final Paint scorePaint = new Paint();

    public static final Random random = new Random();
    public StringBuilder scoreBuilder = new StringBuilder();

    public Vader[] vaders = new Vader[10];
    public Heart[] hearts = new Heart[5];
    public Explosion[] explosions = new Explosion[45];
    public ArrayList<Bullet> bullets = new ArrayList<>(0);
    public ArrayList<TripleFighter> tripleFighters = new ArrayList<>(0);
    public ArrayList<BulletEnemy> bulletEnemies = new ArrayList<>(0);
    public ArrayList<BulletBoss> bulletBosses = new ArrayList<>(0);
    public ArrayList<Boss> bosses = new ArrayList<>(0);

    public Player player;
    public Screen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public Button buttonMenu;
    public PauseButton pauseButton;
    public AudioPlayer audioPlayer;
    public ImageHub imageHub;

    public int numberVaders = vaders.length;
    public int numberExplosions = explosions.length;
    public int numberBullets = 0;
    public int numberTripleFighters = 0;
    public int numberBulletsEnemy = 0;
    public int numberBulletsBoss = 0;
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
    public String curScore = "";
    public String maxScore = "";
    public volatile boolean playing = false;

    private static final long BOSS_TIME = 80_000_000_000L;
    private static long lastBoss;
    private static long now;

    public static int fps;
    private static final int MILLIS_IN_SECOND = 1_000_000_000;
    private static long timeFrame;

    public Game(Context cont, AttributeSet attrs) {
        super(cont, attrs);
        context = cont;
        holder = getHolder();
    }

    public void initGame(int width, int height) {
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
        for (int i = 0; i < numberExplosions; i++) {
            if (i < 20) {
                explosions[i] = new Explosion(this, "default");
            } else {
                if (i < 40) {
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

        AudioPlayer.menuMusic.start();
    }

    public void gameplay() {
        timeFrame = System.nanoTime();

        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            now = System.nanoTime();

            moveAll = player.speedX / 3;

            screen.update();
            screen.x -= moveAll;
            screen.render();

            if (now - lastBoss > BOSS_TIME) {
                lastBoss = now;
                bosses.add(new Boss(this));
                numberBosses += 1;
            }

            for (int i = 0; i < 150; i++) {
                if (i < numberVaders) {
                    for (int j = 0; j < numberBullets; j++) {
                        vaders[i].check_intersectionBullet(bullets.get(j));
                    }
                    vaders[i].x -= moveAll;
                    vaders[i].update();
                    vaders[i].render();
                }

                if (i < numberTripleFighters) {
                    for (int j = 0; j < numberBullets; j++) {
                        tripleFighters.get(i).check_intersectionBullet(bullets.get(j));
                    }
                    tripleFighters.get(i).x -= moveAll;
                    tripleFighters.get(i).update();
                    tripleFighters.get(i).render();
                }

                if (i < numberBullets) {
                    bullets.get(i).x -= moveAll;
                    bullets.get(i).update();
                    bullets.get(i).render();
                    if (bullets.get(i).y < -50) {
                        bullets.remove(i);
                        numberBullets -= 1;
                    }
                }

                if (i < numberBulletsEnemy) {
                    bulletEnemies.get(i).x -= moveAll;
                    bulletEnemies.get(i).update();
                    bulletEnemies.get(i).render();
                    if (bulletEnemies.get(i).y > screenHeight | bulletEnemies.get(i).x < -100 | bulletEnemies.get(i).x > screenWidth) {
                        bulletEnemies.remove(i);
                        numberBulletsEnemy -= 1;
                    } else {
                        player.check_intersectionBullet(bulletEnemies.get(i));
                    }
                }

                if (i < numberBulletsBoss) {
                    bulletBosses.get(i).x -= moveAll;
                    bulletBosses.get(i).update();
                    bulletBosses.get(i).render();
                    if (bulletBosses.get(i).y > screenHeight | bulletBosses.get(i).x < -100 | bulletBosses.get(i).x > screenWidth) {
                        bulletBosses.remove(i);
                        numberBulletsBoss -= 1;
                    } else {
                        player.check_intersectionBullet(bulletBosses.get(i));
                    }
                }

                if (i < numberExplosions) {
                    explosions[i].x -= moveAll;
                    explosions[i].update();
                }

                if (i < numberBosses) {
                    bosses.get(i).x -= moveAll;
                    bosses.get(i).update();
                    bosses.get(i).render();
                    for (int j = 0; j < numberBullets; j++) {
                        bosses.get(i).check_intersectionBullet(bullets.get(j));
                    }
                    if (bosses.get(i).health <= 0) {
                        bosses.get(i).kill();
                    }
                }
            }

            player.update();
            if (player.health <= 0) {
                if (score > lastMax) {
                    scoreBuilder.append(" ").append(score);
                }
                AudioPlayer.gameoverSnd.start();
                gameStatus = 3;
            }
            player.render();

            pauseButton.render();

            if (gameStatus == 2) {
                timerStart();
            }

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            curScore = "Current score: " + String.valueOf(score);

            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);
            canvas.drawText(curScore, halfScreenWidth - scorePaint.measureText(curScore) / 2, 50, scorePaint);
            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }

    @Override
    public void run() {
        while(playing) {
            switch (gameStatus)
            {
                case 1:
                    preview();
                    break;
                case 2:
                case 0:
                    gameplay();
                    break;
                case 3:
                    gameover();
                    break;
                case 4:
                    pause();
                    break;
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        action = event.getAction();
        pointerCount = event.getPointerCount();
        clickX = (int) event.getX();
        clickY = (int) event.getY();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                buttonStart.mouseX = clickX;
                buttonStart.mouseY = clickY;
                buttonQuit.mouseX = clickX;
                buttonQuit.mouseY = clickY;
                buttonMenu.mouseX = clickX;
                buttonMenu.mouseY = clickY;
                pauseButton.setCoords(clickX, clickY);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!player.dontmove) {
                    player.endX = clickX - player.halfWidth;
                    player.endY = clickY - player.halfHeight;
                }
                break;
            }
//            case MotionEvent.ACTION_UP: {
//                break;
//            }
//            case MotionEvent.ACTION_CANCEL: {
//                break;
//            }
//            case MotionEvent.ACTION_POINTER_UP: {
//                break;
//            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {}

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder){}

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
                }
            }
        }
        try {
            thread.join();
        } catch(Exception e) {
            Log.e("Error ", "Thread join " + e);
        }
    }

    public void onResume() {
        checkMaxScore();
        thread = new Thread(this);
        thread.start();
        playing = true;
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
    }

    public void generatePause() {
        AudioPlayer.pirateMusic.pause();
        AudioPlayer.pauseMusic.seekTo(0);
        AudioPlayer.pauseMusic.start();
        AudioPlayer.buttonSnd.start();
        if (gameStatus == 2) {
            AudioPlayer.readySnd.pause();
        }
        gameStatus = 4;

        player.dontmove = true;

        buttonStart.newFunc("Resume", halfScreenWidth - buttonQuit.halfWidth,screenHeight / 3 - buttonStart.halfHeight, "pause");
        buttonMenu.x = halfScreenWidth - buttonQuit.halfWidth;
        buttonMenu.y = buttonStart.height + buttonStart.y + 30;
        buttonQuit.newFunc("Quit", halfScreenWidth - buttonQuit.halfWidth,buttonStart.height + buttonMenu.y + 30, "quit");
    }

    public void generateMenu() {
        AudioPlayer.pauseMusic.pause();
        AudioPlayer.menuMusic.start();

        saveScore();
        checkMaxScore();
        count = 0;
        score = 0;
        numberBosses = 0;

        screen.x = (int) (screenWidth * -0.2);

        player.AI();

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
        for (int i = 0; i < numberExplosions; i++) {
            explosions[i].stop();
        }
    }

    public void generateNewGame() {
        saveScore();
        checkMaxScore();
        count = 0;
        score = 0;
        screen.x = (int) (screenWidth * -0.2);
        player.PLAYER();
        bulletEnemies = new ArrayList<>(0);
        bulletBosses = new ArrayList<>(0);
        tripleFighters = new ArrayList<>(0);
        bullets = new ArrayList<>(0);
        bulletEnemies = new ArrayList<>(0);
        for (int i = 0; i < numberExplosions; i++) {
            explosions[i].stop();
        }

        numberVaders = 12;
        numberBullets = 0;
        numberBulletsEnemy = 0;
        numberBosses = 0;
        numberBulletsBoss = 0;
        buttonStart.x = screenWidth * 2;
        buttonQuit.x = screenWidth * 2;
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

        if (gameStatus == 1) {
            AudioPlayer.menuMusic.pause();
        } else {
            AudioPlayer.pirateMusic.pause();
        }
        AudioPlayer.pirateMusic.seekTo(0);
        AudioPlayer.pirateMusic.start();

        gameStatus = 2;

        lastBoss = System.nanoTime();
        AudioPlayer.readySnd.start();
    }

    public void gameover() {
        timeFrame = System.nanoTime();

        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

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

            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }

    public void pause() {
        timeFrame = System.nanoTime();
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

//            if (pauseButton.oldStatus == 0) {

            if (player.health > 0) {
                screen.render();
                for (int i = 0; i < 150; i++) {
                    if (i < numberVaders) {
                        vaders[i].render();
                    }

                    if (i < numberTripleFighters) {
                        tripleFighters.get(i).render();
                    }

                    if (i < numberBullets) {
                        bullets.get(i).render();
                    }

                    if (i < numberBulletsEnemy) {
                        bulletEnemies.get(i).render();
                    }

                    if (i < numberBulletsBoss) {
                        bulletBosses.get(i).render();
                    }

                    if (i < numberExplosions) {
                        explosions[i].render();
                    }

                    if (i < numberBosses) {
                        bosses.get(i).render();
                    }
                }
                player.render();
                if (pauseButton.oldStatus == 2) {
                    if (0 <= count & count < 70) {
                        canvas.drawText("1", (screenWidth - startPaint.measureText("1")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                    } else {
                        if (70 <= count & count < 140) {
                            canvas.drawText("2", (screenWidth - startPaint.measureText("2")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
                        } else {
                            if (140 <= count & count < 210) {
                                canvas.drawText("3", (screenWidth - startPaint.measureText("3")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
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

            buttonStart.update();
            buttonStart.render();
            buttonQuit.update();
            buttonQuit.render();
            buttonMenu.update();
            buttonMenu.render();

            pauseButton.render();

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            curScore = "Current score: " + String.valueOf(score);
            maxScore = "Max score: " + String.valueOf(lastMax);


            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, pauseButton.y + pauseButton.width + 50, fpsPaint);
            canvas.drawText(curScore, halfScreenWidth - scorePaint.measureText(curScore) / 2, 50, scorePaint);
            canvas.drawText(maxScore, halfScreenWidth - scorePaint.measureText(maxScore) / 2, 130, scorePaint);

            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }


    public void timerStart() {
        count += 1;
        if (0 <= count & count < 70) {
            canvas.drawText("1", (screenWidth - startPaint.measureText("1")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
        } else {
            if (70 <= count & count < 140) {
                canvas.drawText("2", (screenWidth - startPaint.measureText("2")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
            } else {
                if (140 <= count & count < 210) {
                    canvas.drawText("3", (screenWidth - startPaint.measureText("3")) / 2, (screenHeight + startPaint.getTextSize()) / 2, startPaint);
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
                        }
                    }
                }
            }
        }
    }

    public void preview() {
        timeFrame = System.nanoTime();
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
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
                    bullets.get(i).update();
                    bullets.get(i).render();
                    if (bullets.get(i).y < -50) {
                        bullets.remove(i);
                        numberBullets -= 1;
                    }
                }

                if (i < numberExplosions) {
                    explosions[i].update();
                }
            }

            buttonStart.update();
            buttonStart.render();
            buttonQuit.update();
            buttonQuit.render();

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, 50, fpsPaint);

            maxScore = "Max score: " + String.valueOf(lastMax);
            canvas.drawText(maxScore, halfScreenWidth - scorePaint.measureText(maxScore) / 2, 50, scorePaint);

            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
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
