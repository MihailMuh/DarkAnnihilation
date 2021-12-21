package com.warfare.darkannihilation.abstraction;

import static com.badlogic.gdx.Input.Keys.BACK;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.systemd.service.Windows;

public abstract class BaseApp extends ApplicationAdapter {
    @Override
    public void create() {
        Gdx.graphics.setVSync(true);
        Gdx.input.setCatchKey(BACK, true);
        Windows.refresh();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Windows.refresh();
    }
}
