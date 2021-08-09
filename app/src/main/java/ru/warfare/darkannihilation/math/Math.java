package ru.warfare.darkannihilation.math;

public final class Math {
    public static final float HALF_PI = (float) java.lang.Math.PI / 2f;

    public static int getDistance(int a, int b) {
        return (int) java.lang.Math.sqrt((a * a) + (b * b));
    }

    public static int boolToInt(boolean x) {
        if (x) {
            return 1;
        }
        return 0;
    }
}