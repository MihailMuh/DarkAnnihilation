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
            Scene oldScene = mainGame.scenes.first();
            Runnable runnable = () -> {
                newScene.create();
                oldScene.pause();
                mainGame.scenes.set(0, newScene);
                newScene.resume();
            };

            newScene.readyAssets();
            if (loadingScreen) mainGame.scenes.set(0, mainGame.loadingScreen.resume(runnable));
            else {
                finishLoading = false;
                while (!finishLoading) {
                    Service.sleep(20);
                    Gdx.app.postRunnable(() -> finishLoading = imageHub.update());
                }
                runnable.run();
            }
            oldScene.dispose();
        });
    }

    public void startTopScene(Scene newScene) {
        Gdx.app.postRunnable(() -> {
            for (Scene scene : mainGame.scenes) {
                scene.pause();
            }
            newScene.create();
            mainGame.scenes.add(newScene);
            newScene.resume();
        });
    }

    public void stopScene(Scene sceneToStop) {
        Gdx.app.postRunnable(() -> {
            sceneToStop.pause();
            mainGame.scenes.removeValue(sceneToStop, true);
            sceneToStop.dispose();
            for (Scene scene : mainGame.scenes) {
                scene.resume();
            }
        });
    }
}
