package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.systemd.loading.DarkScene;
import com.warfare.darkannihilation.utils.AssetManagerSuper;

public class MainGameManager {
    private final MainGame mainGame;

    public final AssetManagerSuper assetManager;
    public final ImageHub imageHub;
    public final FontHub fontHub;
    public final SoundHub soundHub;

    public MainGameManager(ImageHub imageHub, FontHub fontHub, SoundHub soundHub, AssetManagerSuper assetManager, MainGame mainGame) {
        this.imageHub = imageHub;
        this.fontHub = fontHub;
        this.assetManager = assetManager;
        this.soundHub = soundHub;

        this.mainGame = mainGame;
    }

    public void finishLastScene() {
        Scene lastScene = mainGame.scenesStack.pop();

        mainGame.resume();

        lastScene.pause();
        lastScene.dispose();
    }

    public void startScene(Scene scene, boolean loadingScreen) {
        if (loadingScreen)
            startScene(new DarkScene(this, scene, mainGame.scenesStack.lastScene, 1.5f), false);
        else {
            scene.create();

            mainGame.scenesStack.push(scene);
            mainGame.pause();

            scene.resume();
        }
    }
}
