package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.loading.DarkScene;

public class MainGameManager {
    private final MainGame mainGame;

    public MainGameManager(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public void finishLastScene() {
        Scene lastScene = mainGame.scenesStack.pop();

        mainGame.resume();

        lastScene.pause();
        lastScene.dispose();
    }

    public void finishAllScenes() {
        mainGame.scenesStack.clear();
    }

    public void startScene(Scene scene, boolean loadingScreen) {
        if (loadingScreen)
            startScene(new DarkScene(this, scene, mainGame.scenesStack.lastScene, 1.5f), false);
        else {
            scene.create();

            mainGame.pause();
            mainGame.scenesStack.push(scene);

            scene.resume();
        }
    }

    public boolean loadResources() {
        return mainGame.assetManager.update();
    }
}
