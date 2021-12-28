package com.warfare.darkannihilation;

import com.badlogic.gdx.assets.AssetManager;
import com.warfare.darkannihilation.screens.BackgroundScreen;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.utils.AnimationG;

public class LoadingScreen extends SceneWrap {
    private final AssetManager assetManager;
    private Runnable runnable;
    private boolean finishLoading;

    public LoadingScreen(AnimationG animation, AssetManager assetManager) {
        this.assetManager = assetManager;
        screen = new BackgroundScreen(animation);
    }

    public LoadingScreen resume(Runnable runnable) {
        finishLoading = true;
        this.runnable = runnable;
        Processor.post(() -> {
            Service.sleep(400);
            finishLoading = false;
        });
        return this;
    }

    @Override
    public void render() {
        screen.render();

        if (!finishLoading && assetManager.update()) {
            finishLoading = true;
            Processor.post(runnable);
        }
    }
}
