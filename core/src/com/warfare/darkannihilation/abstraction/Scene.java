package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.ApplicationListener;
import com.warfare.darkannihilation.systemd.BaseClickListener;
import com.warfare.darkannihilation.systemd.MainGameManager;

public abstract class Scene implements ApplicationListener {
    protected final MainGameManager mainGameManager;
    protected BaseClickListener clickListener;
    protected BaseScreen screen;

    protected Scene(MainGameManager mainGameManager) {
        this.mainGameManager = mainGameManager;
    }

    public abstract void readyAssets();

    public void update() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
