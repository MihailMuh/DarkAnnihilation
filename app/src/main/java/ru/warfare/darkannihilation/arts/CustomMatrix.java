package ru.warfare.darkannihilation.arts;

import android.graphics.Matrix;

public class CustomMatrix extends Matrix {
    public Matrix rotate(float angle) {
        postRotate(angle);
        return this;
    }

    public Matrix mirror(int width, int height) {
        postScale(-1, 1, width / 2f, height / 2f);
        return this;
    }
}
