package com.example.shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Scene extends View {
    Point[] points = new Point[3];
    float density;
    int gridSize = 30;
    int gridWidth;
    int gridHeight;
    int countPoints;
    String mode = "draw";
    String typeShape = "rect";
    String color = "#000000";

    JSONArray jsonArray = new JSONArray();
    String path = "/DATA.txt";



    Figures[] figures = new Figures[3000];
    int countFigures = 0;

    public void SaveData() {
        try (FileWriter writer = new FileWriter(path, false)) {
            String text = jsonArray.toString();
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            Log.e("Инфо", ex.getMessage());
        }
    }

    public Scene(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;
        gridSize *= density;
    }

    private void createRect(String color, Point corner, int width, int height) {
        figures[countFigures] = new Rect(color, corner, width, height);
        got_info();
        countFigures++;
    }

    private void createCircle(String color, Point center, float radius) {
        figures[countFigures] = new Circle(color, center, radius);
        got_info();
        countFigures++;

    }

    private void createTriangle(String color, Point a, Point b, Point c) {
        figures[countFigures] = new Triangle(color, a, b, c);
        got_info();
        countFigures++;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gridWidth = getWidth() / gridSize;
        gridHeight = getHeight() / gridSize;

        drawGrid(canvas);
        drawPoints(canvas);

        drawFigures(canvas);
    }

    private void drawFigures(Canvas canvas) {
        for (int i = 0; i < countFigures; i++) {
            figures[i].draw(canvas);
        }
    }

    private void drawGrid(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        DashPathEffect effects = new DashPathEffect(new float[] { 3, 9}, 0);
        paint.setPathEffect(effects);

        for (int i = 0; i < gridWidth ; i++) {
            int x = i * gridSize;
            canvas.drawLine(x, 0, x, getHeight(), paint);
        }

        for (int i = 0; i < gridHeight ; i++) {
            int y = i * gridSize;
            int endX = getWidth();
            canvas.drawLine(0, y, endX, y, paint);
        }

    }

    private void drawPoints(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        for (int i = 0; i < countPoints ; i++) {
            Point point = points[i];
            canvas.drawCircle(point.x, point.y, 10, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onDownTouch((int)event.getX(), (int)event.getY());
        }
        return true;
    }

    private void onDownTouch(int x, int y) {
        if (this.mode.equals("draw")) {
            points[countPoints] = new Point(x, y);
            countPoints += 1;

            switch (this.typeShape) {
                case "rect": checkRectForCreating(); break;
                case "circle": checkCircleForCreating(); break;
                case "triangle": checkTriangleForCreating(); break;
            }

            invalidate();
        }
    }

    private void checkRectForCreating() {
        if (countPoints >= 2) {
            int width = points[1].x - points[0].x;
            int height = points[1].y - points[0].y;
            createRect(this.color, new Point(points[0]), width, height);
            countPoints = 0;
        }
    }

    private void checkCircleForCreating() {
        if (countPoints >= 2) {
            int a = points[1].x - points[0].x;
            int b = points[1].y - points[0].y;
            float radius = (float)Math.sqrt( Math.pow(a, 2) + Math.pow(b, 2) );
            createCircle(this.color, new Point(points[0]), radius);
            countPoints = 0;
        }
    }

    private void checkTriangleForCreating() {
        if (countPoints >= 3) {
            createTriangle(this.color, new Point(points[0]), new Point(points[1]), new Point(points[2]));
            countPoints = 0;
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setTypeShape(String type) {
        this.typeShape = type;
        countPoints = 0;
        invalidate();
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void undo() {
        countFigures--;
        countFigures = Math.max(0, countFigures);
        invalidate();
    }
    void got_info() {
        try{
            figures[countFigures].save_inf(jsonArray);
            Log.e("Инфо", jsonArray.toString());
        } catch (Exception e) {
            Log.e("Инфо", "");
        }
    }
}
