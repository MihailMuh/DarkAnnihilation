package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Scene;

public class MainGameManager {
    private final MainGame mainGame;

    public MainGameManager(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public void finishScene() {
        Scene lastScene = mainGame.scenesStack.pop();

        mainGame.resume();

        lastScene.pause();
        lastScene.dispose();
    }

    public void finishAllScenes() {
        mainGame.scenesStack.clear();
    }

    public void startScene(Scene scene, boolean loadingScreen) {
        if (loadingScreen) {
            mainGame.loading.setSceneToRun(scene, mainGame.scenesStack.lastScene);
            startScene(mainGame.loading, false);
        } else {
            scene.create();

            mainGame.pause();
            mainGame.scenesStack.push(scene);

            scene.resume();
        }
    }

    public void loadAllResources() {
        mainGame.assetManager.finishLoading();
    }
}
