package ru.warfare.darkannihilation;

import java.util.ArrayList;

import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.Py.print;
import static ru.warfare.darkannihilation.systemd.Game.topPaint;
import static ru.warfare.darkannihilation.systemd.Game.topPaintRed;

public final class Table {
    private static ArrayList<ArrayList<String>> table;
    private static ArrayList<Float> maxes;

    private static final float textHeight = topPaint.getTextSize() * 1.45f;
    private static final int[] index = new int[2];
    private static float curHeight;
    private static int tableHeight;
    private static int columns;

    public static void newTable(int tableHeight) {
        curHeight = 0;
        columns = 0;
        table = new ArrayList<>(1);
        maxes = new ArrayList<>();
        table.add(new ArrayList<>(0));

        Table.tableHeight = tableHeight;
    }

    public static void addText(String text) {
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

    public static void addMarkedText(String text) {
        addText(text);
        index[0] = columns;
        index[1] = table.get(columns).indexOf(text);
    }

    public static void makeTable() {
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
    }

    public static void drawTable() {
        try {
            for (int i = 0; i < table.size(); i++) {
                int c;
                if (i == 0) {
                    c = 1;
                } else {
                    c = 4 * i;
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
        } catch (Exception e) {
            print("Draw Table " + e.toString());
        }
    }
}
