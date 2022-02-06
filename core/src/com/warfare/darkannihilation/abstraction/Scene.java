package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.ApplicationListener;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.screens.BaseScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.ClickListener;

public abstract class Scene implements ApplicationListener {
    protected final MainGameManager mainGameManager;
    protected final ImageHub imageHub;
    protected final SoundHub soundHub;
    protected final FontHub fontHub;

    protected ClickListener clickListener;
    protected BaseSprite screen;

    public Scene(MainGameManager mainGameManager) {
        this(mainGameManager, null, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener) {
        this(mainGameManager, clickListener, null);
    }

    public Scene(MainGameManager mainGameManager, ClickListener clickListener, BaseScreen screen) {
        this.mainGameManager = mainGameManager;
        imageHub = mainGameManager.imageHub;
        soundHub = mainGameManager.soundHub;
        fontHub = mainGameManager.fontHub;

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

    }

    @Override
    public void resume() {

    }

    public void update() {

    }

    @Override
    public void dispose() {
        Processor.multiProcessor.removeProcessor(clickListener);
    }
}
