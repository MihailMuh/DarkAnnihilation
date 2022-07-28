package com.warfare.darkannihilation.scenes.versus;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.ClickListener;

class VersusClickListener extends ClickListener {
    private final MainGameManager manager;
    private boolean start = false;

    VersusClickListener(MainGameManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        if (Gdx.input.isTouched(1) && !start) {
            start = true;
            manager.finishScene();
        }
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        return false;
    }
}
