package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Watch.frameCount;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.hub.AssetManagerSuper;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.LocaleHub;
import com.warfare.darkannihilation.hub.Resources;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.scenes.error.ErrorScene;
import com.warfare.darkannihilation.scenes.loading.Loading;
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;
import com.warfare.darkannihilation.utils.ScenesStack;

public class MainGame extends BaseApp {
    private final MainGameManager mainGameManager = new MainGameManager(this);
    private Frontend frontend;
    private ErrorScene errorScene;

    final ScenesStack scenesStack = new ScenesStack();

    AssetManagerSuper assetManager;
    Loading loading;

    @Override
    public void create() {
        super.create();
        assetManager = new AssetManagerSuper();
        Resources.setProviders(new ImageHub(assetManager), new SoundHub(assetManager), new FontHub(assetManager), new LocaleHub(assetManager));

        Menu menu = new Menu(mainGameManager);
        assetManager.finishLoading();
        getImages().boot();
        getFonts().boot();
        getSounds().boot();
        getLocales().boot();
        menu.create();

        scenesStack.push(menu);

        frontend = new Frontend(this);

        Processor.postTask(() -> {
            Service.sleep(500);
            getImages().lazyLoading();
            getSounds().lazyLoading();
            getFonts().lazyLoading();
            getLocales().lazyLoading();
            loading = new Loading(mainGameManager, scenesStack);

            resume();

            errorScene = new ErrorScene(this, mainGameManager, scenesStack);
            Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> error(throwable));
        });
    }

    @Override
    public void render() {
        try {
            Watch.update();

            scenesStack.lastScene.update();

            frontend.render();
        } catch (Exception exception) {
            exception.printStackTrace();
            error(exception);
        }
    }

    private void error(Throwable throwable) {
        if (errorScene.running) return;

        errorScene.init(throwable);
        try {
            mainGameManager.startScene(errorScene, false);
        } catch (Exception exception2) {
            errorScene.hardRun();
        }
    }

    @Override
    public void resume() {
        Texture.setAssetManager(assetManager);
        if (scenesStack.lastScene != null) {
            scenesStack.lastScene.resume();
        }
    }

    @Override
    public void pause() {
        if (scenesStack.lastScene != null) {
            scenesStack.lastScene.pause();
        }
        frameCount = 0;
    }

    @Override
    public void resize(int width, int height) {
        Windows.refresh();
        frontend.resize(width, height);
    }

    @Override
    public void dispose() {
        frontend.dispose();
        assetManager.dispose();
        Processor.dispose();
    }
}
