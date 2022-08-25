package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.scenes.loading.Loading;
import com.warfare.darkannihilation.utils.ScenesArray;

public class MainGameManager {
    private final ScenesArray scenesArray;

    public MainGameManager(ScenesArray scenesArray) {
        this.scenesArray = scenesArray;
    }

    public void finishScene(Scene scene) {
        scene.pause();

        Scene sceneToResume = scenesArray.getSceneUnder(scene);
        if (sceneToResume != null) sceneToResume.resume();

        scenesArray.removeValue(scene, true);
        scene.dispose();
    }

    public void startScene(Scene scene, boolean loadingScreen) {
        if (loadingScreen) {
            scenesArray.peek().updateOnPause = true;
            scene.updateOnPause = true;
            startScene(new Loading(this, scenesArray, scene), false);
        } else {
            scene.create();

            scenesArray.pauseLastScene();
            scenesArray.add(scene);

            scene.resume();
        }
    }

    public void startSceneOver(Scene callerScene, Scene sceneToStart) {
        sceneToStart.create();

        callerScene.pause();
        scenesArray.insert(scenesArray.indexOf(callerScene, true) + 1, sceneToStart);

        sceneToStart.resume();
    }
}
