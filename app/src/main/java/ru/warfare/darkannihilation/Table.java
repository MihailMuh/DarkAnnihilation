package ru.warfare.darkannihilation;

import android.graphics.Color;

import java.util.ArrayList;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.HardThread;

import static ru.warfare.darkannihilation.systemd.service.Service.resources;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class Table {
    private final CustomPaint topPaint = new CustomPaint();
    private final CustomPaint topPaintRed = new CustomPaint();

    private final ArrayList<ArrayList<String>> table = new ArrayList<>(0);
    private final ArrayList<Float> maxes = new ArrayList<>(0);
    private float[] finalMaxes;

    private float textHeight;
    private final int[] index = new int[2];
    private float curHeight = 0;
    private int tableHeight;
    private int columns = 0;

    public boolean offline = false;
    private boolean finish = false;

    private int errX;
    private int errY;
    private String string_error;

    private int startX = 0;
    private volatile float speedX = 0;
    private boolean stop = false;
    private static final byte left = 100;
    private int tableSize;

    public Table(int tableHeight) {
        table.add(new ArrayList<>(0));
        this.tableHeight = tableHeight;

        topPaint.setTextSize(30);

        topPaintRed.set(topPaint);
        topPaintRed.setColor(Color.RED);

        textHeight = topPaint.getTextSize() * 2f;
    }

    public Table(String lang, boolean err) {
        topPaintRed.setColor(Color.RED);
        topPaintRed.setTextSize(40);

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

    public void startMove(int X) {
        startX = X;
        stop = false;
    }

    public void setCoords(int X) {
        speedX = (startX - X) / 1.5f;
        startX = X;
    }

    public void stopMove() {
        stop = true;
    }

    public void addText(String text) {
        if (curHeight < tableHeight) {
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
        tableSize = table.size();
        finalMaxes = new float[tableSize];

        for (int i = 0; i < tableSize; i++) {
            float max = 0;

            for (int j = 0; j < table.get(i).size(); j++) {
                float currentLen = topPaint.measureText(table.get(i).get(j));

                if (currentLen > max) {
                    max = currentLen;
                }
            }

            maxes.add(max + left);
            finalMaxes[i] = max;
        }

        finish = true;
    }

    public void drawTable() {
        if (!offline) {
            if (finish) {
                float xForCurColumn = 0;
                for (int i = 0; i < tableSize; i++) {
                    for (int j = 0; j < table.get(i).size(); j++) {
                        String string = table.get(i).get(j);
                        float x = ((maxes.get(i) - topPaint.measureText(string)) / 2f) + xForCurColumn;
                        float y = (j + 1) * textHeight;

                        if (i == index[0] && j == index[1]) {
                            Game.canvas.drawText(string, x, y, topPaintRed);
                        } else {
                            Game.canvas.drawText(string, x, y, topPaint);
                        }
                    }

                    maxes.set(i, maxes.get(i) - speedX);

                    xForCurColumn += finalMaxes[i] + left;
                }

                while (maxes.get(0) - finalMaxes[0] >= left) {
                    for (int i = 0; i < tableSize; i++) {
                        maxes.set(i, maxes.get(i) - 10f);
                    }
                    stop = false;
                    speedX = 0;
                }

                HardThread.doInBackGround(() -> {
                    if (stop) {
                        speedX /= 1.02f;
                    }
                });
            }
        } else {
            Game.canvas.drawText(string_error, errX, errY, topPaintRed);
        }
    }
}
