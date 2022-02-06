package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteBatchSuper extends SpriteBatch {
    public SpriteBatchSuper(int size) {
        super(size);
    }

    public void endSolidScreen() {
        end();
        enableBlending();
        begin();
    }
}
