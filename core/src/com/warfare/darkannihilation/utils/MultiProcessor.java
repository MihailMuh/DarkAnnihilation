package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.Frontend.camera;
import static com.warfare.darkannihilation.systemd.service.Processor.postOnTouch;
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
        postOnTouch(() -> {
            onTouch();
            for (int i = 0; i < listeners.size; i++)
                if (listeners.get(i).touchDragged(touchPos.x, touchPos.y, pointer)) break;
        });
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        postOnTouch(() -> {
            onTouch();
            for (int i = 0; i < listeners.size; i++)
                if (listeners.get(i).touchDown(touchPos.x, touchPos.y, pointer)) break;
        });
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        postOnTouch(() -> {
            onTouch();
            for (int i = 0; i < listeners.size; i++)
                if (listeners.get(i).touchUp(touchPos.x, touchPos.y, pointer)) break;
        });
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        postOnTouch(() -> {
            onTouch();
            for (int i = 0; i < listeners.size; i++)
                if (listeners.get(i).keyDown(keycode)) break;
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
