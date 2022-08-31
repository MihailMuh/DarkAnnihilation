package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.ApplicationListener;
import com.warfare.darkannihilation.screens.AnimatedScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.ClickListener;

public abstract class Scene implements ApplicationListener {
    protected ClickListener clickListener;
    protected AnimatedScreen screen;
    protected MainGameManager mainGameManager;

    public boolean updateOnPause = false;
    public boolean update = true;

    public Scene() {
    }

    public Scene(MainGameManager mainGameManager) {
        this(mainGameManager, null, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener) {
        this(mainGameManager, clickListener, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener, AnimatedScreen screen) {
        this.mainGameManager = mainGameManager;

        this.clickListener = clickListener;
        this.screen = screen;
    }

    @Override
    public void create() {
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (!updateOnPause) update = false;
    }

    @Override
    public void resume() {
        update = true;
    }

    public void update() {

    }

    public void updateInThread() {

    }

    @Override
    public void dispose() {
        Processor.multiProcessor.removeProcessor(clickListener);
    }
}
