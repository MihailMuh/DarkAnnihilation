package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.ApplicationListener;
import com.warfare.darkannihilation.screens.Screen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.ClickListener;

public abstract class Scene implements ApplicationListener {
    protected final MainGameManager mainGameManager;
    protected ClickListener clickListener;
    protected Screen screen;

    public boolean updateOnPause = false;
    public boolean update = true;

    public Scene(MainGameManager mainGameManager) {
        this(mainGameManager, null, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener) {
        this(mainGameManager, clickListener, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener, Screen screen) {
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
        resumeBackground();
        update = true;
    }

    public void update() {

    }

    public void stopBackground() {
        if (screen != null) screen.stopAnimation = true;
    }

    public void resumeBackground() {
        if (screen != null) screen.stopAnimation = false;
    }

    @Override
    public void dispose() {
        Processor.multiProcessor.removeProcessor(clickListener);
    }
}
