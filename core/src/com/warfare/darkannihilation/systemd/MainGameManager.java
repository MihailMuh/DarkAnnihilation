package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.service.Processor;

public class MainGameManager {
    public ImageHub imageHub;
    public MainGame mainGame;

    public MainGameManager(ImageHub imageHub, MainGame mainGame) {
        this.imageHub = imageHub;
        this.mainGame = mainGame;
    }

    public void startScene(Scene newScene) {
        Gdx.app.postRunnable(() -> {
            Scene oldScene = mainGame.scene;
            newScene.run();
            mainGame.scene = newScene;
            Processor.post(oldScene::dispose);
        });
    }
}
