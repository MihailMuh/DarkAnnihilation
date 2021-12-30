package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.ApplicationListener;
import com.warfare.darkannihilation.systemd.Intent;
import com.warfare.darkannihilation.systemd.MainGameManager;

public abstract class Scene implements ApplicationListener {
    protected MainGameManager mainGameManager;
    protected BaseClickListener clickListener;
    protected BaseScreen screen;

    public void bootAssets(Intent intent) {
        this.mainGameManager = intent.gameManager;
    }

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
