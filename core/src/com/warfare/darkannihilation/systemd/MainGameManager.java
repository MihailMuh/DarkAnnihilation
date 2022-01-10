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

    public void startScene(Intent intent, boolean loadingScreen) {
        if (loadingScreen) {
            Parameters params = new Parameters();
            params.put("runnable", (Runnable) () -> finishAllScenes(intent));
            params.put("secs", 1.5f);
            startTopScene(new Intent(this, DarkScene.class, params));
        } else {
            finishLoading = false;
            while (!finishLoading) {
                Service.sleep((int) (delta * 1000));
                Processor.postToUI(() -> finishLoading = resourcesManager.update());
            }
            finishAllScenes(intent);
        }
    }

    public void startTopScene(Intent intent) {
        Scene newScene = intent.getScene();

        Processor.postToUI(() -> {
            int len = mainGame.scenesList.size();
            for (int i = 0; i < len; i++) {
                mainGame.scenesList.get(i).pause();
            }
            mainGame.scenesList.add(newScene);
            newScene.resume();
        });
    }

    public void finishScene() {
        Processor.postToUI(() -> {
            Scene lastScene = mainGame.scenesList.lastScene;
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

    public void finishAllScenes(Intent intent) {
        Processor.postToUI(() -> {
            int len = mainGame.scenesList.size();
            for (int i = 0; i < len; i++) {
                Scene scene = mainGame.scenesList.get(i);
                scene.pause();
                Processor.post(scene::dispose);
            }

            mainGame.scenesList.clear();

            Scene newScene = intent.getScene();
            mainGame.scenesList.add(newScene);

            newScene.resume();
        });
    }
}
