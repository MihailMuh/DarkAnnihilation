package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.systemd.Frontend;

public class MultiProcessor extends InputAdapter {
    private final Vector3 touchPos = new Vector3();
    public final Array<ClickListener> listeners = new Array<>(5);

    public MultiProcessor() {
        Gdx.input.setInputProcessor(this);
    }

    private void onTouch() {
        touchPos.x = Gdx.input.getX(0);
        touchPos.y = Gdx.input.getY(0);
        Frontend.unproject(touchPos);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        onTouch();
        float x = touchPos.x;
        float y = touchPos.y;

        for (ClickListener listener : listeners) {
            if (listener.touchDragged(x, y)) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        onTouch();
        float x = touchPos.x;
        float y = touchPos.y;

        for (ClickListener listener : listeners) {
            if (listener.touchDown(x, y)) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        onTouch();
        float x = touchPos.x;
        float y = touchPos.y;

        for (ClickListener listener : listeners) {
            if (listener.touchUp(x, y)) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        for (ClickListener listener : listeners) {
            if (listener.keyDown(keycode)) {
                return true;
            }
        }
        return true;
    }

    public synchronized void insertProcessor(ClickListener clickListener) {
        if (clickListener != null) {
            listeners.insert(0, clickListener);
        }
    }

    public synchronized void removeProcessor(ClickListener clickListener) {
        if (clickListener != null) {
            listeners.removeValue(clickListener, true);
        }
    }
}
