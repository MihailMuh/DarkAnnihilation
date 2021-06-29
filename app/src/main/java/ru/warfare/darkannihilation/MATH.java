package ru.warfare.darkannihilation;

public class MATH {
    public static final float HALF_PI = (float) Math.PI / 2f;

    public static int randInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public static int getDistance(int a, int b) {
        return (int) Math.sqrt((a * a) + (b * b));
    }
    public static double getDistance(double a, double b) {
        return Math.sqrt((a * a) + (b * b));
    }
}

class Vector {
    public float len;
    public float rads;

    public int[] vector(int startX, int startY, int endX, int endY, float len) {
        rads = (float) Math.atan2(endY - startY, endX - startX);
//        speedX speedY angle
        return new int[]{(int) (Math.cos(rads) * len), (int) (Math.sin(rads) * len),
                (int) Math.toDegrees(rads + MATH.HALF_PI)};
    }

    public int[] easyVector(int startX, int startY, int endX, int endY, int len) {
        rads = (float) Math.atan2(endY - startY, endX - startX);
//        speedX speedY
        return new int[]{(int) (Math.cos(rads) * len), (int) (Math.sin(rads) * len)};
    }

    public void basisVector(int startX, int startY, int endX, int endY, float len) {
        this.len = len;

        rads = (float) Math.atan2(endY - startY, endX - startX);
    }

    public float[] rotateVector() {
        return new float[]{(float) (Math.cos(rads) * len), (float) (Math.sin(rads) * len)};
    }

    public float getAngle() {
        return (float) Math.toDegrees(rads + MATH.HALF_PI);
    }
}
