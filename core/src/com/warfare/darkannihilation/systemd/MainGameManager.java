package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class MainGameManager {
    private volatile boolean finishLoading;
    public ImageHub imageHub;
    public MainGame mainGame;

    public MainGameManager(ImageHub imageHub, MainGame mainGame) {
        this.imageHub = imageHub;
        this.mainGame = mainGame;
    }

    public void startScene(Scene newScene, boolean loadingScreen) {
        Processor.post(() -> {
            Scene oldScene = mainGame.scene;
            Runnable runnable = () -> {
                newScene.run();
                mainGame.scene = newScene;
            };

            newScene.readyAssets();
            if (loadingScreen) mainGame.scene = mainGame.loadingScreen.run(runnable);
            else {
                finishLoading = false;
                while (!finishLoading) {
                    Service.sleep(20);
                    Gdx.app.postRunnable(() -> finishLoading = imageHub.assetManager.update());
                }
                runnable.run();
            }
            oldScene.dispose();
        });
    }
}
