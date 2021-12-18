package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.systemd.BaseClickListener;
import com.warfare.darkannihilation.systemd.MainGameManager;

public abstract class Scene implements Disposable {
    protected final MainGameManager mainGameManager;
    protected BaseClickListener clickListener;
    protected BaseScreen screen;

    protected Scene(MainGameManager mainGameManager) {
        this.mainGameManager = mainGameManager;
    }

    public abstract void run();

    public abstract void render();

    public void update() {

    }
}
