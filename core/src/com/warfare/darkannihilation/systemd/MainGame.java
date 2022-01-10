package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.ResourcesManager;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.utils.FontWrap;
import com.warfare.darkannihilation.utils.ScenesList;

public class MainGame extends BaseApp {
    private ResourcesManager resourcesManager;
    private Frontend frontend;

    final ScenesList scenesList = new ScenesList();

    @Override
    public void create() {
        super.create();
        Processor.UIThread = Thread.currentThread();

        resourcesManager = new ResourcesManager();
        ImageHub imageHub = new ImageHub(resourcesManager);
        FontHub fontHub = new FontHub(resourcesManager);
        SoundHub soundHub = new SoundHub(resourcesManager);
        MainGameManager mainGameManager = new MainGameManager(imageHub, fontHub, soundHub, resourcesManager, this);

        Intent intent = new Intent(mainGameManager, Menu.class);
        resourcesManager.finishLoading();
        imageHub.boot();
        fontHub.boot();

        scenesList.add(intent.getScene());

        frontend = new Frontend(this, new FontWrap(fontHub.canisMinor, 1.1f));

        Processor.post(() -> {
            Service.sleep(500);
            imageHub.lazyLoading();
        });

        resume();
    }

    @Override
    public void render() {
        Watch.update();

        scenesList.lastScene.update();

        frontend.render();
    }

    @Override
    public void resume() {
        Texture.setAssetManager(resourcesManager);
        for (Scene scene : scenesList) {
            scene.resume();
        }
    }

    @Override
    public void pause() {
        for (Scene scene : scenesList) {
            scene.pause();
        }
    }

    @Override
    public void dispose() {
        resourcesManager.dispose();
        Processor.dispose();
    }
}
