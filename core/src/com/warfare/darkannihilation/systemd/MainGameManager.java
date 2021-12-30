package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.ResourcesManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class MainGameManager {
    private volatile boolean finishLoading;
    public final ResourcesManager resourcesManager;
    public final ImageHub imageHub;
    public final FontHub fontHub;
    public final MainGame mainGame;

    public MainGameManager(ImageHub imageHub, FontHub fontHub, ResourcesManager resourcesManager, MainGame mainGame) {
        this.imageHub = imageHub;
        this.fontHub = fontHub;
        this.resourcesManager = resourcesManager;
        this.mainGame = mainGame;
    }

    private void startScene(Scene newScene, boolean loadingScreen) {
        Scene oldScene = mainGame.sceneStack.lastScene;
        Runnable runnable = () -> {
            newScene.create();
            oldScene.pause();
            mainGame.sceneStack.set(newScene);
            newScene.resume();
        };

        if (loadingScreen) mainGame.sceneStack.set(mainGame.loadingScene.resume(runnable));
        else {
            finishLoading = false;
            while (!finishLoading) {
                Service.sleep((int) (delta * 1000));
                Gdx.app.postRunnable(() -> finishLoading = resourcesManager.update());
            }
            runnable.run();
        }
        oldScene.dispose();
    }

    private void startTopScene(Scene newScene) {
        Gdx.app.postRunnable(() -> {
            newScene.create();
            for (Scene scene : mainGame.sceneStack) {
                scene.pause();
            }
            mainGame.sceneStack.put(newScene);
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

    public void stopScene() {
        Scene lastScene = mainGame.sceneStack.lastScene;

        Gdx.app.postRunnable(() -> {
            lastScene.pause();
            mainGame.sceneStack.pop();
            for (Scene scene : mainGame.sceneStack) {
                scene.resume();
            }
            Processor.post(lastScene::dispose);
        });
    }
}
