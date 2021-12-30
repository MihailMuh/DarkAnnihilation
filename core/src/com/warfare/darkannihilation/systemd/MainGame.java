package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ResourcesManager;
import com.warfare.darkannihilation.scenes.LoadingScene;
import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.utils.FontWrap;
import com.warfare.darkannihilation.utils.SceneStack;

public class MainGame extends BaseApp {
    private ResourcesManager resourcesManager;
    private Frontend frontend;

    final SceneStack sceneStack = new SceneStack();
    LoadingScene loadingScene;

    @Override
    public void create() {
        super.create();
        resourcesManager = new ResourcesManager();
        ImageHub imageHub = new ImageHub(resourcesManager);
        FontHub fontHub = new FontHub(resourcesManager);

        Scene menu = new Intent(Menu.class).boot(new MainGameManager(imageHub, fontHub, resourcesManager, this));
        resourcesManager.finishLoading();
        imageHub.boot();
        fontHub.boot();

        menu.create();
        sceneStack.put(menu);
        menu.resume();

        frontend = new Frontend(this, new FontWrap(fontHub.canisMinor, 1.1f));

        Processor.post(() -> {
            Service.sleep(500);
            imageHub.lazyLoading();
            loadingScene = new LoadingScene(imageHub.loadingScreenGIF, resourcesManager);
        });
    }

    @Override
    public void render() {
        Watch.update();

        sceneStack.lastScene.update();

        frontend.render();
    }

    @Override
    public void resume() {
        super.resume();
        Texture.setAssetManager(resourcesManager);
        for (Scene scene : sceneStack) {
            scene.resume();
        }
    }

    @Override
    public void pause() {
        for (Scene scene : sceneStack) {
            scene.pause();
        }
    }

    @Override
    public void dispose() {
        for (Scene scene : sceneStack) {
            scene.dispose();
        }
        resourcesManager.dispose();
        Processor.dispose();
    }
}
