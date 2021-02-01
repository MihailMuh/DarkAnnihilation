package ru.startandroid.surfacedemo;

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

    private float fps;
    private static final int MILLIS_IN_SECOND = 1000000000;
    long timeFrame;
    public Player player;
    public Vader[] vaders = new Vader[40];


    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
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
            canvas.drawText("FPS: " + fps, 50, 50, textPaint);

            for (int i = 0; i < vaders.length; i++) {
                vaders[i].update(canvas);
            }
            player.update(canvas);

            fps = MILLIS_IN_SECOND / (System.nanoTime() - timeFrame);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        player.endX = event.getX();
        player.endY = event.getY();

        player.get_info();
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
