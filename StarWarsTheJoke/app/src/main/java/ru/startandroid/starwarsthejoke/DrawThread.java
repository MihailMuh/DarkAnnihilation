package ru.startandroid.starwarsthejoke;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    private final SurfaceHolder surfaceHolder;

    private volatile boolean running = true;
    private final Paint backgroundPaint = new Paint();
    private Bitmap bitmap;
    private int towardPointX;
    private int towardPointY;
    {
        backgroundPaint.setColor(Color.BLUE);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.smile);
        this.surfaceHolder = surfaceHolder;
    }

    public void setTowardPoint(int x, int y) {
        towardPointX = x;
        towardPointY = y;
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        int smileX = 0;
        int smileY = 0;
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    canvas.drawBitmap(bitmap, smileX, smileY, backgroundPaint);

                    if (smileX + bitmap.getWidth() / 2 < towardPointX) smileX += 100;
                    if (smileX + bitmap.getWidth() / 2 > towardPointX) smileX -= 100;
                    if (smileY + bitmap.getHeight() / 2 < towardPointY) smileY += 100;
                    if (smileY + bitmap.getHeight() / 2 > towardPointY) smileY -= 100;
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
