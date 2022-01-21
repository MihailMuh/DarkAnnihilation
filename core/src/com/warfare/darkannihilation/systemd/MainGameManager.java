package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.ResourcesManager;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.systemd.loading.DarkScene;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class MainGameManager {
    private final MainGame mainGame;
    private volatile boolean finishLoading;

    public final ResourcesManager resourcesManager;
    public final ImageHub imageHub;
    public final FontHub fontHub;
    public final SoundHub soundHub;

    public MainGameManager(ImageHub imageHub, FontHub fontHub, SoundHub soundHub, ResourcesManager resourcesManager, MainGame mainGame) {
        this.imageHub = imageHub;
        this.fontHub = fontHub;
        this.resourcesManager = resourcesManager;
        this.soundHub = soundHub;

        this.mainGame = mainGame;
    }

    public void startScene(Scene scene, boolean loadingScreen) {
        if (loadingScreen) {
            startTopScene(new DarkScene(this, () -> finishAllScenes(scene), 1.5f));
        } else {
            finishLoading = false;
            while (!finishLoading) {
                Service.sleep((int) (delta * 1000));
                Processor.postToUI(() -> finishLoading = resourcesManager.update());
            }
            finishAllScenes(scene);
        }
    }

    public void startTopScene(Scene scene) {
        scene.create();

        Processor.postToUI(() -> {
            int len = mainGame.scenesList.size();
            for (int i = 0; i < len; i++) {
                mainGame.scenesList.get(i).pause();
            }
            mainGame.scenesList.add(scene);
            scene.resume();
        });
    }

    public void finishScene() {
        Scene lastScene = mainGame.scenesList.lastScene;

        Processor.postToUI(() -> {
            mainGame.scenesList.pop();

            Processor.post(() -> {
                lastScene.pause();
                lastScene.dispose();
            });

            int len = mainGame.scenesList.size();
            for (int i = 0; i < len; i++) {
                mainGame.scenesList.get(i).resume();
            }
        });
    }

    public void finishAllScenes(Scene newScene) {
        newScene.create();

        Processor.postToUI(() -> {
            int len = mainGame.scenesList.size();
            for (int i = 0; i < len; i++) {
                Scene scene = mainGame.scenesList.get(i);
                scene.pause();
                Processor.post(scene::dispose);
            }

            mainGame.scenesList.clear();
            mainGame.scenesList.add(newScene);

            newScene.resume();
        });
    }
}
