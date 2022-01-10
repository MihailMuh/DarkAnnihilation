package com.warfare.darkannihilation.systemd.loading;

import com.warfare.darkannihilation.utils.ClickListener;

public class LoadingClickListener extends ClickListener {
    @Override
    public boolean touchDown(float x, float y, int pointer) {
        return true;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        return true;
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        return true;
    }
}
