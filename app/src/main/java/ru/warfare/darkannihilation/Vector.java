package ru.warfare.darkannihilation;

public class Vector {
    public int a;
    public int b;
    public double c;
    public int len;

    public void makeVector(int startX, int startY, int endX, int endY, int len) {
        this.len = len;

        a = endX - startX;
        b = endY - startY;
        c = Math.sqrt((a * a) + (b * b));
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
}
