package ru.startandroid.aplication_1;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.content.Context;

import java.util.Random;

public class Widget1 extends View {
    float density;
    int x;
    int y;
    int block;
    int cell_x;
    int cell_y;
    int ship_x;
    int ship_y;
    boolean destroy;
    boolean milk;
    Random random;
    public Widget1(Context context, float density, int width, int height) {
        super(context);
        this.random = new Random();

        this.density = density;
        this.x = 0;
        this.y = 0;
<<<<<<< HEAD
        this.block = 70;
        this.destroy = false;
        this.milk = false;
        this.cell_x = width / this.block - 1;
        this.cell_y = height / this.block - 1;

        this.ship_x = this.random.nextInt(this.cell_x);
        this.ship_y = this.random.nextInt(this.cell_y);
        if (ship_y > cell_y) {
            this.ship_x = this.random.nextInt(this.cell_x);
            this.ship_y = this.random.nextInt(this.cell_y);
        }

=======
        this.block = 50;
        this.destroy = false;
        this.milk = false;
        this.cell_x = width / this.block;
        this.cell_y = height / this.block;
        this.ship_x = this.random.nextInt(this.cell_x);
        this.ship_y = this.random.nextInt(this.cell_y);
>>>>>>> 009ee9127181ddbe1fda66aaf4010810de42218b
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
<<<<<<< HEAD
=======
        Log.i("boom", "x: " + this.ship_x + ", y: " + this.ship_y);
>>>>>>> 009ee9127181ddbe1fda66aaf4010810de42218b

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
<<<<<<< HEAD
        for (int i = 0; i <= getWidth(); i += this.block) {
            for (int j = 0; j <= getHeight(); j += this.block) {
                canvas.drawLine(0, j, (getWidth()  / this.block) * this.block, j, paint);
                canvas.drawLine(i, 0, i, (getHeight() / this.block) * this.block, paint);
=======
        for (int i = 0; i < getWidth(); i += this.block) {
            for (int j = 0; j < getHeight(); j += this.block) {
                canvas.drawLine(0, j, getWidth(), j, paint);
                canvas.drawLine(i, 0, i, getHeight(), paint);
>>>>>>> 009ee9127181ddbe1fda66aaf4010810de42218b
            }
        }

        shoot(canvas);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.x = (int) event.getX() / this.block;
            this.y = (int) event.getY() / this.block;
            Log.i("onTouch", "x: " + this.x + ", y: " + this.y);
            if (this.x == this.ship_x & this.y == this.ship_y) {
                this.destroy = true;
                this.ship_x = this.random.nextInt(this.cell_x);
                this.ship_y = this.random.nextInt(this.cell_y);
                if (ship_y > cell_y) {
                    this.ship_x = this.random.nextInt(this.cell_x);
                    this.ship_y = this.random.nextInt(this.cell_y);
                }
                Log.i("ship", "x: " + this.ship_x + ", y: " + this.ship_y);
                Log.i("ship", "sgsghfhx: " + this.cell_x + ", y: " + this.cell_y);
                invalidate();
            } else {
                this.milk = true;
                this.destroy = false;
                invalidate();
                this.ship_x = this.random.nextInt(this.cell_x);
                this.ship_y = this.random.nextInt(this.cell_y);
            } else {
                this.milk = true;
                this.destroy = false;
                invalidate();
            }
//            else {
//                if (this.x - 2 <= this.ship_x & this.ship_x <= this.x + 2 & this.y - 2 <= this.ship_y & this.ship_y <= this.y + 2) {
//                    this.milk = true;
//                    this.destroy = false;
//                    invalidate();
//                }
//            }
        }
        return false;
    }

    public void shoot(Canvas canvas) {
        if (this.milk) {
            Paint d = new Paint();
            d.setColor(Color.RED);
            d.setStrokeWidth(6);
            d.setStyle(Paint.Style.STROKE);
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(4);
            paint.setStyle(Paint.Style.STROKE);
            for (int xx = this.x - 2; xx < this.x + 3; xx++) {
                for (int yy = this.y - 2; yy < this.y + 3; yy++) {
                    if (xx == this.ship_x & yy == this.ship_y) {
                        canvas.drawCircle(xx * this.block + this.block / 2,yy * this.block + this.block / 2, this.block / 2, d);
                    } else {
                        canvas.drawCircle(xx * this.block + this.block / 2,yy * this.block + this.block / 2, this.block / 4, paint);
                    }
                }
            }
        }
        if (this.destroy) {
            Paint d = new Paint();
            d.setColor(Color.RED);
            d.setStrokeWidth(10);
            d.setStyle(Paint.Style.STROKE);
            canvas.drawLine(this.x * this.block, this.y * this.block, this.x * this.block + this.block, this.y * this.block + this.block, d);
            canvas.drawLine(this.x * this.block, this.y * this.block + this.block, this.x * this.block + this.block, this.y * this.block, d);

        }
    }
}
