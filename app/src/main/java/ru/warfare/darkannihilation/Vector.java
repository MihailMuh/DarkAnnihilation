package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Sprite.getDistance;

public class Vector {
    private int a;
    private int b;
    private double c;
    private double ac;
    private double bc;
    public double len;

    public void makeVector(int startX, int startY, int endX, int endY, int len) {
        this.len = len;

        a = endX - startX;
        b = endY - startY;
        c = Math.sqrt((a * a) + (b * b));
        ac = a / c;
        bc = b / c;
    }

    public void makeVector(float startX, float startY, float endX, float endY, double len) {
        this.len = len;

        a = (int) (endX - startX);
        b = (int) (endY - startY);
        c = Math.sqrt((a * a) + (b * b));
        ac = a / c;
        bc = b / c;
    }

    public int getAngle() {
        return (int) Math.toDegrees(Math.atan2(b, a) + (Math.PI / 2));
    }

    public int getSpeedX() {
        return (int) ((a * len) / c);
    }

    public int getSpeedY() {
        return (int) ((b * len) / c);
    }

    public double[] rotateVector(double angle) {
        double x = ac * len;
        double y = bc * len;
        double cos = Math.cos(-angle);
        double sin =  Math.sin(-angle);
        return new double[] {((x * cos) - (y * sin)), ((x * sin) + (y * cos))};
    }

    public static float getAngle(float x1, float y1, float x2, float y2) {
        double yy = y2 - y1;
        double xx = x2 - x1;
        double R = getDistance(yy, xx);
        double b = getDistance(xx + R, yy);
        double a = 2 * R * R;
        double cos = (a - (b * b)) / a;
        if (cos <= -1f) {
            return 180;
        }
        if (y1 > y2) {
            b = getDistance(xx - R, yy);
            return (float) Math.toDegrees(Math.acos((a - (b * b)) / a)) + 180;
        }
        return (float) Math.toDegrees(Math.acos(cos));
    }
}
