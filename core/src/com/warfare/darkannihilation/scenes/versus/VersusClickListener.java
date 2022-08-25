package com.warfare.darkannihilation.scenes.versus;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.utils.ClickListener;

class VersusClickListener extends ClickListener {
    private final Runnable actionOnTouch;
    private boolean start = false;

    VersusClickListener(Runnable actionOnTouch) {
        this.actionOnTouch = actionOnTouch;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        if (Gdx.input.isTouched(1) && !start) {
            start = true;
            actionOnTouch.run();
        }
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        return false;
    }
}
