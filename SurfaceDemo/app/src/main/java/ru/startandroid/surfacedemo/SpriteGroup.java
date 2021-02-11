package ru.startandroid.surfacedemo;

import android.graphics.Canvas;

public class SpriteGroup {
    public static Canvas canvas;
    public static Sprite[] sprites = new Sprite[0];
    public SpriteGroup(Canvas c) {
        canvas = c;
    }
    public static void append(Sprite sprite) {
        Sprite[] spritesNew = new Sprite[sprites.length + 1];
        for (int i = 0; i < sprites.length; i++) {
            spritesNew[i] = sprites[i];
        }
        spritesNew[spritesNew.length - 1] = sprite;
        sprites = spritesNew.clone();
    }
    public static void update() {
        for (int i = 0; i < sprites.length; i++) {
            try {
                sprites[i].update(canvas);
            } finally {

            }
        }
    }
}
