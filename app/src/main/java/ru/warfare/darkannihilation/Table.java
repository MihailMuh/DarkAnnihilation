package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.systemd.service.Service.resources;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class Table {
    private final Paint topPaint = new Paint();
    private final Paint topPaintRed = new Paint();

    private final ArrayList<ArrayList<String>> table = new ArrayList<>(0);
    private final ArrayList<Float> maxes = new ArrayList<>(0);

    private float textHeight;
    private static final int[] index = new int[2];
    private float curHeight = 0;
    private int tableHeight;
    private int columns = 0;

    public boolean offline = false;
    private boolean finish = false;

    private int errX;
    private int errY;
    private String string_error;

    public Table(int tableHeight) {
        table.add(new ArrayList<>(0));
        this.tableHeight = tableHeight;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setAntiAlias(true);

        topPaint.set(paint);
        topPaint.setTextSize(30);

        topPaintRed.set(topPaint);
        topPaintRed.setColor(Color.RED);

        textHeight = topPaint.getTextSize() * 1.45f;
    }

    public Table(String lang, boolean err) {
        topPaintRed.setColor(Color.RED);
        topPaintRed.setFilterBitmap(true);
        topPaintRed.setDither(true);
        topPaintRed.setAntiAlias(true);
        topPaintRed.setTextSize(50);

        offline = true;

        int i = 23;
        if (err) {
            i = 24;
        }
        switch (lang) {
            case "ru":
                string_error = resources.getStringArray(R.array.ru)[i];
                break;
            case "en":
                string_error = resources.getStringArray(R.array.en)[i];
                break;
            case "fr":
                string_error = resources.getStringArray(R.array.fr)[i];
                break;
            case "sp":
                string_error = resources.getStringArray(R.array.sp)[i];
                break;
            case "ge":
                string_error = resources.getStringArray(R.array.ge)[i];
                break;
        }

        errX = (int) ((SCREEN_WIDTH - topPaintRed.measureText(string_error)) / 2);
        errY = (int) ((SCREEN_HEIGHT + topPaintRed.getTextSize()) / 2);
    }

    public void addText(String text) {
        if (curHeight <= tableHeight) {
            table.get(columns).add(text);
            curHeight += textHeight;
        } else {
            columns++;
            curHeight = 0;
            table.add(new ArrayList<>(0));
            addText(text);
        }
    }

    public void addMarkedText(String text) {
        addText(text);
        index[0] = columns;
        index[1] = table.get(columns).indexOf(text);
    }

    public void makeTable() {
        for (int i = 0; i < table.size(); i++) {
            maxes.add(0f);
            for (int j = 0; j < table.get(i).size(); j++) {
                float currentLen = topPaint.measureText(table.get(i).get(j)) / 2f;
                if (currentLen > maxes.get(i)) {
                    maxes.set(i, currentLen);
                }
            }
            float m = maxes.get(i) + 60;
            maxes.set(i, m);
        }

        finish = true;
    }

    public void drawTable() {
        if (!offline) {
            if (finish) {
                for (int i = 0; i < table.size(); i++) {
                    int c;
                    if (i == 0) {
                        c = 1;
                    } else {
                        c = 3 * i;
                    }
                    for (int j = 0; j < table.get(i).size(); j++) {
                        String string = table.get(i).get(j);
                        float x = (maxes.get(i) * c) - (topPaint.measureText(string) / 2f);
                        float y = (j + 1) * textHeight;

                        if (i == index[0] & j == index[1]) {
                            Game.canvas.drawText(string, x, y, topPaintRed);
                        } else {
                            Game.canvas.drawText(string, x, y, topPaint);
                        }
                    }
                }
            }
        } else {
            Game.canvas.drawText(string_error, errX, errY, topPaintRed);
        }
    }
}
