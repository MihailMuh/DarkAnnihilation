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

    private SurfaceHolder holder;
    private Thread thread;
    private Canvas canvas;
    private Context context;

    public int screenWidth;
    public int screenHeight;
    private final Paint textPaint = new Paint();
    private volatile boolean playing = false;
    private int fps;
    private static final int MILLIS_IN_SECOND = 1000000000;
    private long timeFrame;
    public Player player;
    public final Vader[] vaders = new Vader[12];
    public ArrayList<Object> allSprites = new ArrayList<>(0);
    public LinkedList<Bullet> bullets = new LinkedList<>();
    public Screen screen;
    public Button button;
    private final int vaderNumbers = vaders.length;
    public int preview = 1;

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

        screen = new Screen(context, width, height);
        player = new Player(context, width, height);
        allSprites.add(player);
        for (int i = 0; i < vaderNumbers; i++) {
            vaders[i] = new Vader(context, width, height);
            allSprites.add(vaders[i]);
        }
        button = new Button(context, "Start", width, height, screenWidth / 2 + 150, screenHeight - 70, this);
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

    @Override
    public void run() {
        while(playing) {
            timeFrame = System.nanoTime();
            Log.i("preview", "" + preview);
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                screen.update(canvas);
                if (preview == 1) {
                    player.update(canvas, bullets);

                    screen.x -= player.speedX / 3;

                    for (int i = 0; i < bullets.size(); i++) {
                        bullets.get(i).x -= player.speedX / 3;
                        bullets.get(i).update(canvas);
                        if (bullets.get(i).y < -50) {
                            bullets.get(i).bulletImage.recycle();
                            bullets.remove(i);
                        }
                    }

                    for (int i = 0; i < vaderNumbers; i++) {
                        for (int j = 0; j < bullets.size(); j++) {
                            vaders[i].check_intersection(bullets.get(j).x, bullets.get(j).y, bullets.get(j).width, bullets.get(j).height);
                        }
                        vaders[i].check_intersection(player.x, player.y, player.width, player.height);
                        vaders[i].x -= player.speedX / 3;
                        vaders[i].update(canvas);
                    }

                    button.update(canvas);
                } else {
                    player.update(canvas, bullets);

                    screen.x -= player.speedX / 3;

                    for (int i = 0; i < bullets.size(); i++) {
                        bullets.get(i).x -= player.speedX / 3;
                        bullets.get(i).update(canvas);
                        if (bullets.get(i).y < -50) {
                            bullets.get(i).bulletImage.recycle();
                            bullets.remove(i);
                        }
                    }

                    for (int i = 0; i < vaderNumbers; i++) {
                        for (int j = 0; j < bullets.size(); j++) {
                            vaders[i].check_intersection(bullets.get(j).x, bullets.get(j).y, bullets.get(j).width, bullets.get(j).height);
                        }
                        vaders[i].check_intersection(player.x, player.y, player.width, player.height);
                        vaders[i].x -= player.speedX / 3;
                        vaders[i].update(canvas);
                    }
                }

                fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
                canvas.drawText("FPS: " + fps, 50, 50, textPaint);
                holder.unlockCanvasAndPost(canvas);
            }
            timeFrame = System.nanoTime();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        player.endX = event.getX() - player.width / 2;
        player.endY = event.getY()  - player.height / 2;
        button.mouseX = event.getX();
        button.mouseY = event.getY();
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
