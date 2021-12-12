package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.Frontend.camera;
import static com.warfare.darkannihilation.systemd.service.Windows.HARDCORE_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HARDCORE_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseClickListener {
    private final Vector3 touchPos = new Vector3();
    private final ExecutorService pool = Executors.newSingleThreadExecutor();

    public BaseClickListener() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                pool.execute(() -> {
                    onTouch();
                    BaseClickListener.this.touchDragged(touchPos.x, touchPos.y, pointer);
                });
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                pool.execute(() -> {
                    onTouch();
                    BaseClickListener.this.touchDown(touchPos.x, touchPos.y, pointer);
                });
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                pool.execute(() -> {
                    onTouch();
                    BaseClickListener.this.touchUp(touchPos.x, touchPos.y, pointer);
                });
                return true;
            }
        });
    }

    private void onTouch() {
        touchPos.x = Gdx.input.getX(0);
        touchPos.y = Gdx.input.getY(0);
        camera.unproject(touchPos, 0, 0, HARDCORE_WIDTH, HARDCORE_HEIGHT);
    }

    public void touchDown(float x, float y, int pointer) {

    }

    public void touchUp(float x, float y, int pointer) {

    }

    public void touchDragged(float x, float y, int pointer) {

    }
}
