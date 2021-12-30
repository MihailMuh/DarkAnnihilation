package com.warfare.darkannihilation.scenes;

import com.warfare.darkannihilation.hub.ResourcesManager;
import com.warfare.darkannihilation.screens.BackgroundScreen;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.utils.AnimationG;

public class LoadingScene extends SceneWrap {
    private final ResourcesManager resourcesManager;
    private Runnable runnable;
    private boolean finishLoading;

    public LoadingScene(AnimationG animation, ResourcesManager resourcesManager) {
        this.resourcesManager = resourcesManager;
        screen = new BackgroundScreen(animation);
    }

    public LoadingScene resume(Runnable runnable) {
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

        if (!finishLoading && resourcesManager.update()) {
            finishLoading = true;
            Processor.post(runnable);
        }
    }
}
