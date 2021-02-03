package ru.startandroid.surfacedemo;

<<<<<<< HEAD
import android.annotation.SuppressLint;
=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
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
<<<<<<< HEAD

    private volatile boolean playing = false;
    private int fps;
=======
    private volatile boolean playing = false;

    private float fps;
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    private static final int MILLIS_IN_SECOND = 1000000000;
    long timeFrame;
    public Player player;
    public Vader[] vaders = new Vader[40];


    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        textPaint = new Paint();
<<<<<<< HEAD
        textPaint.setColor(Color.RED);
=======
        textPaint.setColor(Color.WHITE);
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
        textPaint.setTextSize(40);
        player = new Player(context);
        for (int i = 0; i < vaders.length; i++) {
            Vader vader = new Vader(context);
            vaders[i] = vader;
        }
<<<<<<< HEAD
=======

>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            timeFrame = System.nanoTime();
<<<<<<< HEAD
            canvas.drawColor(Color.BLUE);
=======

            canvas.drawColor(Color.BLUE);
            canvas.drawText("FPS: " + fps, 50, 50, textPaint);
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6

            for (int i = 0; i < vaders.length; i++) {
                vaders[i].update(canvas);
            }
            player.update(canvas);

<<<<<<< HEAD
            fps = (int) (MILLIS_IN_SECOND / (System.nanoTime() - timeFrame));
            canvas.drawText("FPS: " + fps, 50, 50, textPaint);
=======
            fps = MILLIS_IN_SECOND / (System.nanoTime() - timeFrame);
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6

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

<<<<<<< HEAD
    @SuppressLint("ClickableViewAccessibility")
=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        player.endX = event.getX();
        player.endY = event.getY();
<<<<<<< HEAD
=======

        player.get_info();
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
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
<<<<<<< HEAD

    public void setScreenSizes(int width, int height) {
        player.setCoords(width, height);
        for (int i = 0; i < vaders.length; i++) {
            vaders[i].setCoords(width, height);
        }
    }
=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
}
