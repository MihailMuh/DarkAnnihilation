package ru.startandroid.surfacedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    SurfaceHolder holder;
    Paint textPaint;
    Thread thread;

    private volatile boolean playing = false;
    private int fps;
    private static final int MILLIS_IN_SECOND = 1000000000;
    long timeFrame;
    public Player player;
    public Vader[] vaders = new Vader[12];


    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(40);
        player = new Player(context);
        for (int i = 0; i < vaders.length; i++) {
            Vader vader = new Vader(context);
            vaders[i] = vader;
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            timeFrame = System.nanoTime();
            canvas.drawColor(Color.BLUE);
<<<<<<< HEAD

            for (int i = 0; i < vaders.length; i++) {
//                if (player.x < vaders[i].x & vaders[i].x < player.x + player.width &
//                        player.y < vaders[i].y & vaders[i].y < player.y + player.height) {
//                    vaders[i].newStatus();
//                }
                vaders[i].check_intersection(player.x, player.y, player.width, player.height);
            }
=======
>>>>>>> acdb79ad105e33de70e1626dd116379ba67bf263

            for (int i = 0; i < vaders.length; i++) {
                vaders[i].update(canvas);
            }
            player.update(canvas);

            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + fps, 50, 50, textPaint);

            timeFrame = System.nanoTime();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch(Exception e) {
            Log.e("Error: ", "thread join");
        }
    }

    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(playing) {
            draw();

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
<<<<<<< HEAD
        player.endX = event.getX() - player.width / 2;
        player.endY = event.getY()  - player.height / 2;
=======
        player.endX = event.getX();
        player.endY = event.getY();
>>>>>>> acdb79ad105e33de70e1626dd116379ba67bf263
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

    public void setScreenSizes(int width, int height) {
        player.setCoords(width, height);
        for (int i = 0; i < vaders.length; i++) {
            vaders[i].setCoords(width, height);
        }
    }
}
