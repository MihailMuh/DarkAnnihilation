package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.ApplicationAdapter;
import com.warfare.darkannihilation.systemd.Intent;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.ClickListener;

public abstract class Scene extends ApplicationAdapter {
    protected MainGameManager mainGameManager;
    protected BaseScreen screen;
    protected ClickListener clickListener;

    public boolean isShadow = false;

    public void bootAssets(Intent intent) {
        this.mainGameManager = intent.gameManager;
    }

    public void update() {
    }

    @Override
    public void dispose() {
        Processor.multiProcessor.removeProcessor(clickListener);
    }
}
