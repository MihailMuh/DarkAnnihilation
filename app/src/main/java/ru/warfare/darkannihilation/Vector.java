package ru.warfare.darkannihilation;

public class Vector {
    public int a = 0;
    public int b;
    public double c;
    public double ac;
    public double bc;
    public double len;

    public void makeVector(int startX, int startY, int endX, int endY, int len) {
        this.len = len;

        a = endX - startX;
        b = endY - startY;
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
        /**
         * It rotates the vector counterclockwise (right direction).
         */
        double x = ac * len;
        double y = bc * len;
        double cos = Math.cos(-angle);
        double sin =  Math.sin(-angle);
        return new double[] {((x * cos) - (y * sin)), ((x * sin) + (y * cos))};
    }
}
