package ru.warfare.darkannihilation.math;

public class Vector {
    public float len;
    public float rads;

    public int[] vector(int startX, int startY, int endX, int endY, float len) {
        rads = (float) java.lang.Math.atan2(endY - startY, endX - startX);
//        speedX speedY angle
        return new int[]{(int) (java.lang.Math.cos(rads) * len), (int) (java.lang.Math.sin(rads) * len),
                (int) java.lang.Math.toDegrees(rads + Math.HALF_PI)};
    }

    public int[] easyVector(int startX, int startY, int endX, int endY, int len) {
        rads = (float) java.lang.Math.atan2(endY - startY, endX - startX);
//        speedX speedY
        return new int[]{(int) (java.lang.Math.cos(rads) * len), (int) (java.lang.Math.sin(rads) * len)};
    }

    public void basisVector(int startX, int startY, int endX, int endY, float len) {
        this.len = len;

        rads = (float) java.lang.Math.atan2(endY - startY, endX - startX);
    }

    public float[] rotateVector() {
        return new float[]{(float) (java.lang.Math.cos(rads) * len), (float) (java.lang.Math.sin(rads) * len)};
    }

    public float getAngle() {
        return (float) java.lang.Math.toDegrees(rads + Math.HALF_PI);
    }
}

