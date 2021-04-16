package ru.warfare.darkannihilation;

import java.util.Random;

public class BulletBase extends Sprite {
    public Random random = new Random();

    public BulletBase(Game g, int w, int h) {
        super(g, w, h);
        isBullet = true;
    }

}