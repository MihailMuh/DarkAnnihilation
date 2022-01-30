package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.utils.AssetManagerSuper;
import com.warfare.darkannihilation.utils.FontWrap;
import com.warfare.darkannihilation.utils.ScenesStack;

public class MainGame extends BaseApp {
    private AssetManagerSuper assetManager;
    private Frontend frontend;

    final ScenesStack scenesStack = new ScenesStack();

    @Override
    public void create() {
        super.create();
        assetManager = new AssetManagerSuper();

        ImageHub imageHub = new ImageHub(assetManager);
        FontHub fontHub = new FontHub(assetManager);
        SoundHub soundHub = new SoundHub(assetManager);
        MainGameManager mainGameManager = new MainGameManager(imageHub, fontHub, soundHub, assetManager, this);

        Menu menu = new Menu(mainGameManager);
        assetManager.finishLoading();
        imageHub.boot();
        fontHub.boot();
        menu.create();

        scenesStack.push(menu);

        frontend = new Frontend(this, new FontWrap(fontHub.canisMinor, 1.1f));

        Processor.post(() -> {
            resume();

            Service.sleep(500);
            imageHub.lazyLoading();
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
        scenesStack.lastScene.resume();
    }

    @Override
    public void pause() {
        scenesStack.lastScene.pause();
    }

    @Override
    public void dispose() {
        frontend.dispose();
        assetManager.dispose();
        Processor.dispose();
    }
}
