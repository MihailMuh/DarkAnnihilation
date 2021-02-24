package ru.startandroid.surfacedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

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
    public double resizeK;
    private static final Paint fpsPaint = new Paint();
    private static final Paint startPaint = new Paint();
    private static final Paint gameoverPaint = new Paint();
    private static final Paint scorePaint = new Paint();
    public volatile boolean playing = false;
    public int fps;
    private static final int MILLIS_IN_SECOND = 1_000_000_000;
    private long timeFrame;
    public Player player;
    public Vader[] vaders = new Vader[10];
    public ArrayList<Bullet> bullets = new ArrayList<>(0);
    public ArrayList<TripleFighter> tripleFighters = new ArrayList<>(0);
    public ArrayList<BulletEnemy> bulletEnemies = new ArrayList<>(0);
    public Heart[] hearts = new Heart[5];
    public Explosion[] explosions = new Explosion[40];
    public Screen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public PauseButton pauseButton;
    public int vaderNumbers = vaders.length;
    public int numberExplosions = explosions.length;
    public int numberBullets = 0;
    public int numberTripleFighters = 0;
    public int numberBulletsEnemy = 0;
    public int gameStatus = 1;
    public int count = 0;
    public int score = 0;
    public int lastMax = 0;
    public AudioPlayer audioPlayer;
    public int pointerCount;
    public static final Random random = new Random();
    public StringBuilder scoreBuilder = new StringBuilder();
    public String curScore;
    public String maxScore;
    public int action;
    public int clickX;
    public int clickY;
    public ImageHub imageHub;
    public Bitmap screenshot;

    public Game(Context cont, AttributeSet attrs) {
        super(cont, attrs);
        context = cont;
    }

    public void initGame(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        resizeK = (double) screenWidth / 1920;

        screenshot = Bitmap.createBitmap(screenWidth - 53, screenHeight, Bitmap.Config.ARGB_8888);

        imageHub = new ImageHub(this);
        audioPlayer = new AudioPlayer(this);

        holder = getHolder();

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

        vaderNumbers *= 2;
        vaders = new Vader[vaderNumbers];
        for (int i = 0; i < vaderNumbers; i++) {
            vaders[i] = new Vader(this);
        }
        for (int i = 0; i < numberExplosions; i++) {
            if (i < 20) {
                explosions[i] = new Explosion(this, "default");
            } else {
                explosions[i] = new Explosion(this, "small");
            }
        }
        buttonStart = new Button(this, "Start", screenWidth / 2, (int) (screenHeight - 70 * resizeK), "start");
        buttonQuit = new Button(this, "Quit", (int) (screenWidth / 2 - 300 * resizeK), (int) (screenHeight - 70 * resizeK), "quit");
        pauseButton = new PauseButton(this);

        audioPlayer.menuMusic.start();
    }

    public void gameplay() {
        timeFrame = System.nanoTime();

        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            screen.update();
            screen.x -= player.speedX / 3;

            for (int i = 0; i < numberBullets; i++) {
                bullets.get(i).x -= player.speedX / 3;
                bullets.get(i).update();
                if (bullets.get(i).y < -50) {
                    bullets.remove(i);
                    numberBullets -= 1;
                }
            }

            for (int i = 0; i < vaderNumbers; i++) {
                for (int j = 0; j < numberBullets; j++) {
                    vaders[i].check_intersectionBullet(bullets.get(j));
                }
                vaders[i].check_intersectionPlayer();
                vaders[i].x -= player.speedX / 3;
                vaders[i].update();
            }

            for (int i = 0; i < numberTripleFighters; i++) {
                for (int j = 0; j < numberBullets; j++) {
                    tripleFighters.get(i).check_intersectionBullet(bullets.get(j));
                }
                tripleFighters.get(i).check_intersectionPlayer();
                tripleFighters.get(i).x -= player.speedX / 3;
                tripleFighters.get(i).update();
            }

            for (int i = 0; i < bulletEnemies.size(); i++) {
                bulletEnemies.get(i).x -= player.speedX / 3;
                bulletEnemies.get(i).update();
                if (bulletEnemies.get(i).y > screenHeight | bulletEnemies.get(i).x < -100 | bulletEnemies.get(i).x > screenWidth) {
                    bulletEnemies.remove(i);
                    numberBulletsEnemy -= 1;
                } else {
                    player.check_intersectionBullet(bulletEnemies.get(i));
                }
            }

            player.update();
            if (player.health <= 0) {
                if (score > lastMax) {
                    scoreBuilder.append(" ").append(score);
                }

                audioPlayer.gameoverSnd.start();
                gameStatus = 3;
            }

            for (int i = 0; i < numberExplosions; i++) {
                if (!explosions[i].lock) {
                    explosions[i].update();
                    explosions[i].x -= player.speedX / 3;
                }
            }

            if (gameStatus == 2) {
                timerStart();
            }

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, 140, fpsPaint);
            curScore = "Current score: " + String.valueOf(score);
//            maxScore = "Max score: " + String.valueOf(lastMax);
            canvas.drawText(curScore, screenWidth / 2 - scorePaint.measureText(curScore) / 2, 50, scorePaint);
//            canvas.drawText(maxScore, screenWidth / 2 - scorePaint.measureText(maxScore) / 2, 130, scorePaint);
            pauseButton.update();
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
                pauseButton.setCoords(clickX, clickY);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!player.dontmove) {
                    player.endX = clickX - player.width / 2;
                    player.endY = clickY - player.height / 2;
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
            audioPlayer.menuMusic.pause();
        } else {
            audioPlayer.pirateMusic.pause();
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
            audioPlayer.menuMusic.seekTo(0);
            audioPlayer.menuMusic.start();
        } else {
            audioPlayer.pirateMusic.seekTo(0);
            audioPlayer.pirateMusic.start();
        }
    }

    public void makeScreenshot() {
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        PixelCopy.request(this, screenshot, (copyResult) -> {
            if (copyResult != PixelCopy.SUCCESS) {
                Log.e("Error ", "Can't create screenshot ");
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }


    public void generateNewGame() {
        saveScore();
        checkMaxScore();
        count = 0;
        score = 0;
        player = new Player(this);
        player.lock = true;
        player.ai = 0;
        vaders = new Vader[12];
        tripleFighters = new ArrayList<>(0);
        vaderNumbers = 12;
        for (int i = 0; i < 12; i++) {
            if (random.nextFloat() <= 0.15) {
                tripleFighters.add(new TripleFighter(this));
            }
            vaders[i] = new Vader(this);
            vaders[i].lock = true;
        }
        numberTripleFighters = tripleFighters.size();

        for (int i = 0; i < numberExplosions; i++) {
            explosions[i].stop();
        }

        bullets = new ArrayList<>(0);
        bulletEnemies = new ArrayList<>(0);
        numberBullets = 0;
        buttonStart.x = screenWidth * 2;
        buttonQuit.x = screenWidth * 2;
        int c = 370;
        for (int i = 0; i < 5; i++) {
            Heart heart = new Heart(this, c, 10);
            hearts[i] = heart;
            c -= 90;
        }

        pauseButton.lock = false;

        if (gameStatus == 1) {
            audioPlayer.menuMusic.pause();
        } else {
            audioPlayer.pirateMusic.pause();
        }
        audioPlayer.pirateMusic.seekTo(0);
        audioPlayer.pirateMusic.start();

        gameStatus = 2;
        makeScreenshot();
        audioPlayer.readySnd.start();
    }

    public void gameover() {
        timeFrame = System.nanoTime();

        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            screen.update(ImageHub.gameoverScreen);

            if (pointerCount >= 4) {
                generateNewGame();
            }
            pauseButton.update();

            canvas.drawText("Tap this screen with four or more fingers to restart",
                    (screenWidth - gameoverPaint.measureText("Tap this screen with four or more fingers to restart")) / 2,
                    (float) (screenHeight * 0.7), gameoverPaint);

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, 140, fpsPaint);

            curScore = "Current score: " + String.valueOf(score);
            maxScore = "Max score: " + String.valueOf(lastMax);
            canvas.drawText(curScore, screenWidth / 2 - scorePaint.measureText(curScore) / 2, 50, scorePaint);
            canvas.drawText(maxScore, screenWidth / 2 - scorePaint.measureText(maxScore) / 2, 130, scorePaint);

            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }

    public void pause() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            screen.update(screenshot);
            buttonQuit.update();
            buttonStart.update();

            maxScore = "Max score: " + String.valueOf(lastMax);
            canvas.drawText(maxScore, screenWidth / 2 - scorePaint.measureText(maxScore) / 2, 130, scorePaint);

            holder.unlockCanvasAndPost(canvas);
        }
    }


    public void timerStart() {
        count += 1;
        if (0 <= count & count < 70) {
            canvas.drawText("1", screenWidth / 2 - startPaint.measureText("1") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
        } else {
            if (70 <= count & count < 140) {
                canvas.drawText("2", screenWidth / 2 - startPaint.measureText("2") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
            } else {
                if (140 <= count & count < 210) {
                    canvas.drawText("3", screenWidth / 2 - startPaint.measureText("3") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
                } else {
                    if (210 <= count & count < 280) {
                        canvas.drawText("SHOOT!", screenWidth / 2 - startPaint.measureText("SHOOT!") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
                    } else {
                        if (count >= 280) {
                            gameStatus = 0;
                            for (int i = 0; i < vaderNumbers; i++) {
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
            player.update();

            for (int i = 0; i < numberBullets; i++) {
                bullets.get(i).update();
                if (bullets.get(i).y < -50) {
                    bullets.remove(i);
                    numberBullets -= 1;
                }
            }

            for (int i = 0; i < vaderNumbers; i++) {
                for (int j = 0; j < numberBullets; j++) {
                    vaders[i].check_intersectionBullet(bullets.get(j));
                }
                vaders[i].check_intersectionPlayer();
                vaders[i].update();
            }

            for (int i = 0; i < numberExplosions; i++) {
                if (!explosions[i].lock) {
                    explosions[i].update();
                }
            }

            buttonStart.update();
            buttonQuit.update();

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, 50, fpsPaint);

            maxScore = "Max score: " + String.valueOf(lastMax);
            canvas.drawText(maxScore, screenWidth / 2 - scorePaint.measureText(maxScore) / 2, 50, scorePaint);

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
//            Log.e("Recovery", builder.toString() + " max: " + lastMax);

            reader_cooler.close();
        } catch (IOException e) {
            Log.e("Error ", "Can't recovery SCORE: " + e);
        }
    }
}
