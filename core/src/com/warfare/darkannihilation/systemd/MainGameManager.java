package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.scenes.loading.Loading;
import com.warfare.darkannihilation.utils.Intent;
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

    public void startScene(Class<?> classOfSceneToStart, Object... params) {
        startScene(false, classOfSceneToStart, params);
    }

    public void startScene(boolean loadingScreen, Class<?> classOfSceneToStart, Object... params) {
        if (loadingScreen) {
            scenesArray.peek().updateOnPause = true;
            startScene(Loading.class, this, scenesArray, new Intent(classOfSceneToStart, params));
        } else {
            Intent intent = new Intent(classOfSceneToStart, params);

            Scene scene = intent.getScene();
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
