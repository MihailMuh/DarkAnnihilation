package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.Frontend.camera;
import static com.warfare.darkannihilation.systemd.service.Processor.postToTouchLooper;
import static com.warfare.darkannihilation.systemd.service.Windows.PHONE_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.PHONE_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MultiProcessor extends InputAdapter {
    private final Vector3 touchPos = new Vector3();
    public final Array<ClickListener> listeners = new Array<>(5);

    public MultiProcessor() {
        Gdx.input.setInputProcessor(this);
    }

    private void onTouch() {
        touchPos.x = Gdx.input.getX(0);
        touchPos.y = Gdx.input.getY(0);
        camera.unproject(touchPos, 0, 0, PHONE_WIDTH, PHONE_HEIGHT);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        postToTouchLooper(() -> {
            onTouch();
            for (ClickListener listener : listeners)
                if (listener.touchDragged(touchPos.x, touchPos.y)) break;
        });
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        postToTouchLooper(() -> {
            onTouch();
            for (ClickListener listener : listeners)
                if (listener.touchDown(touchPos.x, touchPos.y)) break;
        });
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        postToTouchLooper(() -> {
            onTouch();
            for (ClickListener listener : listeners)
                if (listener.touchUp(touchPos.x, touchPos.y)) break;
        });
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        postToTouchLooper(() -> {
            onTouch();
            for (ClickListener listener : listeners)
                if (listener.keyDown(keycode)) break;
        });
        return true;
    }

    public synchronized void insertProcessor(ClickListener clickListener) {
        if (clickListener != null) listeners.insert(0, clickListener);
    }

    public synchronized void removeProcessor(ClickListener clickListener) {
        if (clickListener != null) listeners.removeValue(clickListener, true);
    }
}
