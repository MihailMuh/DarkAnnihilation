package ru.startandroid.surfacedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    public static SurfaceHolder holder;
    public static Thread thread;
    public Canvas canvas;
    public Context context;

    public int screenWidth;
    public int screenHeight;
    private static final Paint textPaint = new Paint();
    private static final Paint startPaint = new Paint();
    private static final Paint gameoverPaint = new Paint();
    public volatile boolean playing = false;
    public int fps;
    private static final int MILLIS_IN_SECOND = 1000000000;
    private long timeFrame;
    public Player player;
    public AI ai;
    public Vader[] vaders = new Vader[12];
    public ArrayList<Bullet> bullets = new ArrayList<>(0);
    public ArrayList<Heart> hearts = new ArrayList<>(0);
    public Screen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public int vaderNumbers = vaders.length;
    public int heartsNumbers = 0;
    public int numberBullets = 0;
    public int gameStatus = 1;
    public int count = 0;
    public AudioPlayer audioPlayer;
    public int pointerCount;

    public Game(Context cont, AttributeSet attrs) {
        super(cont, attrs);
        context = cont;
    }

    public void initGame(int width, int height) {
        audioPlayer = new AudioPlayer(this);

        screenWidth = width;
        screenHeight = height;

        holder = getHolder();

        textPaint.setColor(Color.RED);
        textPaint.setTextSize(40);
        startPaint.setColor(Color.WHITE);
        startPaint.setTextSize(300);
        gameoverPaint.setColor(Color.WHITE);
        gameoverPaint.setTextSize(50);

        screen = new Screen(this);
        ai = new AI(this);
        vaderNumbers *= 2;
        vaders = new Vader[vaderNumbers];
        for (int i = 0; i < vaderNumbers; i++) {
            vaders[i] = new Vader(this);
        }
        buttonStart = new Button(this, "Start", screenWidth / 2, screenHeight - 70, "start");
        buttonQuit = new Button(this, "Quit", screenWidth / 2 - 300, screenHeight - 70, "quit");

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
                    bullets.get(i).bulletImage.recycle();
                    bullets.remove(i);
                    numberBullets -= 1;
                }
            }

            for (int i = 0; i < vaderNumbers; i++) {
                for (int j = 0; j < numberBullets; j++) {
                    vaders[i].check_intersectionBullet(bullets.get(j).x, bullets.get(j).y, bullets.get(j).width, bullets.get(j).height, bullets.get(j).damage);
                }
                vaders[i].check_intersectionPlayer(player.x, player.y, player.width, player.height);
                vaders[i].x -= player.speedX / 3;
                vaders[i].update();
            }

            player.update();
            if (player.health <= 0) {
                audioPlayer.gameoverSnd.start();
                screen.gameover = true;
                gameStatus = 3;
            }
            if (gameStatus == 2) {
                timerStart();
            }

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, 50, textPaint);
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
                    gameplay();
                    break;
                case 0:
                    gameplay();
                    break;
                case 3:
                    gameover();
                    break;
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        pointerCount = event.getPointerCount();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                if (buttonStart != null) {
                    buttonStart.mouseX = (int) event.getX();
                    buttonStart.mouseY = (int) event.getY();
                    buttonQuit.mouseX = (int) event.getX();
                    buttonQuit.mouseY = (int) event.getY();
                }
                if (player != null) {
                    player.endX = (int) (event.getX() - player.width / 2);
                    player.endY = (int) (event.getY() - player.height / 2);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (player != null) {
                    player.endX = (int) (event.getX() - player.width / 2);
                    player.endY = (int) (event.getY() - player.height / 2);
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

    public void pause() {
        playing = false;
        if (gameStatus == 1) {
            audioPlayer.menuMusic.pause();
        } else {
            audioPlayer.pirateMusic.pause();
        }
        try {
            thread.join();
        } catch(Exception e) {
            Log.e("Error: ", "Thread join");
        }
    }

    public void resume() {
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


    public void generateNewGame() {
        count = 0;
        screen.gameover = false;
        ai = null;
        player = new Player(this);
        player.lock = true;
        vaders = new Vader[12];
        vaderNumbers = 12;
        for (int i = 0; i < 12; i++) {
            vaders[i] = new Vader(this);
            vaders[i].lock = true;
        }
        bullets = new ArrayList<>(0);
        hearts = new ArrayList<>(0);
        numberBullets = 0;
        buttonStart = null;
        int c = 10;
        for (int i = 0; i < 5; i++) {
            Heart heart = new Heart(this, c, 10, "full");
            hearts.add(heart);
            c += 90;
        }
        heartsNumbers = hearts.size();

        if (gameStatus == 1) {
            audioPlayer.menuMusic.pause();
        } else {
            audioPlayer.pirateMusic.pause();
        }
        audioPlayer.pirateMusic.seekTo(0);
        audioPlayer.pirateMusic.start();

        gameStatus = 2;

    }

    public void gameover() {
        timeFrame = System.nanoTime();

        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            screen.update();

            if (pointerCount >= 4) {
                generateNewGame();
            }

            canvas.drawText("Tap this screen with four or more fingers to restart",
                    (screenWidth - gameoverPaint.measureText("Tap this screen with four or more fingers to restart")) / 2,
                    (float) (screenHeight * 0.7), gameoverPaint);

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, 50, textPaint);
            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }

    public void timerStart() {
        count += 1;
        audioPlayer.readySnd.start();
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
            ai.update();

            for (int i = 0; i < numberBullets; i++) {
                bullets.get(i).update();
                if (bullets.get(i).y < -50) {
                    bullets.get(i).bulletImage.recycle();
                    bullets.remove(i);
                    numberBullets -= 1;
                }
            }

            for (int i = 0; i < vaderNumbers; i++) {
                for (int j = 0; j < numberBullets; j++) {
                    vaders[i].check_intersectionBullet(bullets.get(j).x, bullets.get(j).y, bullets.get(j).width, bullets.get(j).height, bullets.get(j).damage);
                }
                vaders[i].check_intersectionAI(ai.x, ai.y, ai.width, ai.height);
                vaders[i].update();
            }

            buttonStart.update();
            buttonQuit.update();

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), screenWidth - 250, 50, textPaint);
            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }
}
