package ru.warfare.darkannihilation;

import java.util.Random;

public class BulletBase extends Sprite {
    public int damage = 1;
    public Random random;

    public BulletBase(Game g, int w, int h) {
        super(g, w, h);
    }

}