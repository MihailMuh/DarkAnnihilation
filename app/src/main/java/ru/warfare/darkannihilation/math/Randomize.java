package ru.warfare.darkannihilation.math;

import java.util.Random;

public final class Randomize {
    private static final Random random = new Random();

    public static int randInt(int min, int max) {
        return (int) (java.lang.Math.random() * ((max - min) + 1)) + min;
    }

    public static float randFloat() {
        return random.nextFloat();
    }

    public static boolean randBoolean() {
        return random.nextBoolean();
    }
}
