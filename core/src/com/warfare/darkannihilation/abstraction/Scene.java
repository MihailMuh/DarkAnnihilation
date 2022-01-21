package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.ApplicationAdapter;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.ClickListener;

public abstract class Scene extends ApplicationAdapter {
    protected final MainGameManager mainGameManager;
    protected ClickListener clickListener;
    protected BaseScreen screen;

    public boolean isShadow = false;

    public Scene(MainGameManager mainGameManager) {
        this(mainGameManager, null, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener) {
        this(mainGameManager, clickListener, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener, BaseScreen screen) {
        this.mainGameManager = mainGameManager;
        this.clickListener = clickListener;
        this.screen = screen;
    }

    @Override
    public void create() {
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    public void update() {
    }

    @Override
    public void dispose() {
        Processor.multiProcessor.removeProcessor(clickListener);
    }
}
