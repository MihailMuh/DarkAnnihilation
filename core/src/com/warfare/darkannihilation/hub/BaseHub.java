package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.systemd.service.Service;

public abstract class BaseHub {
    protected final AssetManagerSuper assetManager;

    public BaseHub(AssetManagerSuper assetManager) {
        this.assetManager = assetManager;
    }

    public void loadInCycle() {
        while (!assetManager.isFinished()) {
            Service.sleep((int) (delta * 2 * 1000));
            Gdx.app.postRunnable(assetManager::update);
        }
    }

    public void boot() {

    }

    public void lazyLoading() {

    }
}
