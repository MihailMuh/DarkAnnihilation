package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.LoadingScreen;
import com.warfare.darkannihilation.SceneWrap;
import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;

public class MainGame extends BaseApp {
    Scene scene;
    LoadingScreen loadingScreen;
    private ImageHub imageHub;
    private Frontend frontend;

    @Override
    public void create() {
        super.create();
        imageHub = new ImageHub();
        frontend = new Frontend(this);
        scene = new SceneWrap(null);

        MainGameManager mainGameManager = new MainGameManager(imageHub, this);
        mainGameManager.startScene(new Menu(mainGameManager), false);

        Processor.post(() -> {
            Service.sleep(500);
            imageHub.lazyLoading();
            loadingScreen = new LoadingScreen(imageHub.loadingScreenGIF, imageHub.assetManager);
        });
    }

    @Override
    public void render() {
        Watch.update();

        scene.update();

        frontend.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        frontend.onResize();
    }

    @Override
    public void resume() {
        super.resume();
        Texture.setAssetManager(imageHub.assetManager);
    }

    @Override
    public void dispose() {
        scene.dispose();
        imageHub.dispose();
        Processor.dispose();
    }
}
