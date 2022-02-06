package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.systemd.loading.DarkScene;

public class MainGameManager {
    private final MainGame mainGame;

    public final ImageHub imageHub;
    public final FontHub fontHub;
    public final SoundHub soundHub;

    public MainGameManager(ImageHub imageHub, FontHub fontHub, SoundHub soundHub, MainGame mainGame) {
        this.imageHub = imageHub;
        this.fontHub = fontHub;
        this.soundHub = soundHub;

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
