package ru.startandroid.surfacedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    public static SurfaceHolder holder;
    public static Thread thread;
    public Canvas canvas;
    public Context context;

    public int screenWidth;
    public int screenHeight;
    private static final Paint textPaint = new Paint();
    private static final Paint startPaint = new Paint();
    public volatile boolean playing = false;
    public int fps;
    private static final int MILLIS_IN_SECOND = 1000000000;
    private long timeFrame;
    public Player player;
    public AI ai;
    public Vader[] vaders = new Vader[12];
    public ArrayList<Bullet> bullets = new ArrayList<>(0);
    public Screen screen;
    public Button buttonStart;
    public Button buttonQuit;
    public int vaderNumbers = vaders.length;
    public int numberBullets = bullets.size();
    public int preview = 1;
    public int count = 0;
    public Rect bounds = new Rect();

    public Game(Context cont, AttributeSet attrs) {
        super(cont, attrs);
        context = cont;
    }

    public void initGame(int width, int height) {
        screenWidth = width;
        screenHeight = height;

        holder = getHolder();

        textPaint.setColor(Color.RED);
        textPaint.setTextSize(40);
        startPaint.setColor(Color.WHITE);
        startPaint.setTextSize(400);

        screen = new Screen(this);
        if (preview == 1) {
            ai = new AI(this);
            vaderNumbers *= 2;
            vaders = new Vader[vaderNumbers];
            for (int i = 0; i < vaderNumbers; i++) {
                vaders[i] = new Vader(this);
            }
            buttonStart = new Button(this, "Start", screenWidth / 2, screenHeight - 70, "start");
            buttonQuit = new Button(this, "Quit", screenWidth / 2 - 300, screenHeight - 70, "quit");
        } else {
            player = new Player(this);
            for (int i = 0; i < vaderNumbers; i++) {
                vaders[i] = new Vader(this);
            }
        }
    }

    public void pause() {
        playing = false;
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
                    vaders[i].check_intersection(bullets.get(j).x, bullets.get(j).y, bullets.get(j).width, bullets.get(j).height);
                }
                vaders[i].check_intersection(ai.x, ai.y, ai.width, ai.height);
                vaders[i].update();
            }

            buttonStart.update();
            buttonQuit.update();

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + fps, 50, 50, textPaint);
            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }

    public void gameplay() {
        timeFrame = System.nanoTime();
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            screen.update();
            player.update();

            if (preview == 2) {
                count += 1;
                if (0 <= count & count < 60) {
                    canvas.drawText("1", screenWidth / 2 - startPaint.measureText("1") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
                } else {
                    if (60 <= count & count < 120) {
                        canvas.drawText("2", screenWidth / 2 - startPaint.measureText("2") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
                    } else {
                        if (120 <= count & count < 180) {
                            canvas.drawText("3", screenWidth / 2 - startPaint.measureText("3") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
                        } else {
                            if (180 <= count & count < 240) {
                                canvas.drawText("SHOOT!", screenWidth / 2 - startPaint.measureText("SHOOT!") / 2, screenHeight / 2 + startPaint.getTextSize() / 2, startPaint);
                            } else {
                                if (count >= 240) {
                                    preview = 0;
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
                    vaders[i].check_intersection(bullets.get(j).x, bullets.get(j).y, bullets.get(j).width, bullets.get(j).height);
                }
                vaders[i].check_intersection(player.x, player.y, player.width, player.height);
                vaders[i].x -= player.speedX / 3;
                vaders[i].update();
            }

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + String.valueOf(fps), 50, 50, textPaint);
            holder.unlockCanvasAndPost(canvas);
        }
        timeFrame = System.nanoTime();
    }

    @Override
    public void run() {
        while(playing) {
            if (preview == 1) {
                preview();
            } else {
                gameplay();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (player != null) {
            player.endX = (int) (event.getX() - player.width / 2);
            player.endY = (int) (event.getY() - player.height / 2);
        }
        if (buttonStart != null) {
            buttonStart.mouseX = (int) event.getX();
            buttonStart.mouseY = (int) event.getY();
            buttonQuit.mouseX = (int) event.getX();
            buttonQuit.mouseY = (int) event.getY();
        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        resume();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        pause();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        pause();
    }
}
