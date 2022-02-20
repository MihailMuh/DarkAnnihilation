package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.Resources;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.systemd.loading.Loading;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.hub.AssetManagerSuper;
import com.warfare.darkannihilation.utils.ScenesStack;

public class MainGame extends BaseApp {
    private final MainGameManager mainGameManager = new MainGameManager(this);
    private Frontend frontend;

    final ScenesStack scenesStack = new ScenesStack();

    AssetManagerSuper assetManager;
    Loading loading;

    @Override
    public void create() {
        super.create();
        assetManager = new AssetManagerSuper();
        Resources.setProviders(new ImageHub(assetManager), new SoundHub(assetManager), new FontHub(assetManager));

        Menu menu = new Menu(mainGameManager);
        assetManager.finishLoading();
        Resources.getImages().boot();
        Resources.getFonts().boot();
        menu.create();

        scenesStack.push(menu);

        frontend = new Frontend(this);

        Processor.post(() -> {
            Service.sleep(500);
            Resources.getImages().lazyLoading();
            loading = new Loading(mainGameManager, scenesStack);

            resume();
        });
    }

    @Override
    public void render() {
        Watch.update();

        scenesStack.lastScene.update();

        frontend.render();
    }

    @Override
    public void resume() {
        Texture.setAssetManager(assetManager);
        if (scenesStack.lastScene != null) scenesStack.lastScene.resume();
    }

    @Override
    public void pause() {
        if (scenesStack.lastScene != null) scenesStack.lastScene.pause();
    }

    @Override
    public void dispose() {
        frontend.dispose();
        assetManager.dispose();
        Processor.dispose();
    }
}
