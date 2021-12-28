package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Watch.delta;

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

    private void startScene(Scene newScene, boolean loadingScreen) {
        Scene oldScene = mainGame.scenes.first();
        Runnable runnable = () -> {
            newScene.create();
            oldScene.pause();
            mainGame.scenes.set(0, newScene);
            newScene.resume();
        };

        if (loadingScreen) mainGame.scenes.set(0, mainGame.loadingScreen.resume(runnable));
        else {
            finishLoading = false;
            while (!finishLoading) {
                Service.sleep((int) (delta * 1000));
                Gdx.app.postRunnable(() -> finishLoading = imageHub.update());
            }
            runnable.run();
        }
        oldScene.dispose();
    }

    private void startTopScene(Scene newScene) {
        newScene.create();

        Gdx.app.postRunnable(() -> {
            for (Scene scene : mainGame.scenes) {
                scene.pause();
            }
            mainGame.scenes.add(newScene);
            newScene.resume();
        });
    }

    private void startScene(Intent intent, boolean onTop, boolean loadingScreen) {
        Processor.post(() -> {
            Scene scene = intent.boot(this);
            if (onTop) {
                startTopScene(scene);
            } else {
                startScene(scene, loadingScreen);
            }
        });
    }

    public void startScene(Intent intent, boolean loadingScreen) {
        startScene(intent, false, loadingScreen);
    }

    public void startTopScene(Intent intent) {
        startScene(intent, true, false);
    }

    public void stopScene(Scene sceneToStop) {
        Gdx.app.postRunnable(() -> {
            sceneToStop.pause();
            mainGame.scenes.removeValue(sceneToStop, true);
            for (Scene scene : mainGame.scenes) {
                scene.resume();
            }
            Processor.post(sceneToStop::dispose);
        });
    }
}
