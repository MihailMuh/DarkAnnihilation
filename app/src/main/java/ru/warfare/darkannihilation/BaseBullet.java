package ru.warfare.darkannihilation;

import java.util.Random;

public class BaseBullet extends Sprite {
    public Random random = new Random();

    public BaseBullet(Game g, int w, int h) {
        super(g, w, h);
        isBullet = true;
    }

}