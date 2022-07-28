package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.utils.ScenesStack;

public class MainGameManager {
    private final MainGame mainGame;
    private final ScenesStack scenesStack;

    public MainGameManager(MainGame mainGame, ScenesStack scenesStack) {
        this.mainGame = mainGame;
        this.scenesStack = scenesStack;
    }

    public void finishScene() {
        Scene lastScene = scenesStack.pop();

        scenesStack.resumeScene();

        lastScene.pause();
        lastScene.dispose();
    }

    public void finishAllScenes() {
        scenesStack.clear();
    }

    public void startScene(Scene scene, boolean loadingScreen) {
        if (loadingScreen) {
            mainGame.loading.setSceneToRun(scene, scenesStack.lastScene);
            startScene(mainGame.loading, false);
        } else {
            scene.create();

            scenesStack.pauseScene();
            scenesStack.push(scene);

            scene.resume();
        }
    }
}
