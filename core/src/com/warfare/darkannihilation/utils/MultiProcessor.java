package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.hub.Resources;

public class MultiProcessor extends InputAdapter {
    private final TouchManager touchManager = new TouchManager();
    public final Array<ClickListener> listeners = new Array<>(true, 5, ClickListener.class);

    public MultiProcessor() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchManager.updateTouchPos(0);
        float x = touchManager.x;
        float y = touchManager.y;

        for (ClickListener listener : listeners) {
            if (listener.touchDragged(x, y)) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchManager.updateTouchPos(0);
        float x = touchManager.x;
        float y = touchManager.y;

        for (ClickListener listener : listeners) {
            if (listener.touchDown(x, y)) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchManager.updateTouchPos(0);
        float x = touchManager.x;
        float y = touchManager.y;

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

    public static class TouchManager {
        private final Vector3 touchPos = new Vector3();
        public float x, y;

        public void updateTouchPos(int pointer) {
            touchPos.x = Gdx.input.getX(pointer);
            touchPos.y = Gdx.input.getY(pointer);

            Resources.getViewport().getCamera().unproject(touchPos);

            x = touchPos.x;
            y = touchPos.y;
        }
    }
}
