package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.Settings.APPLY_ACCUMULATOR;
import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Service.print;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.Gdx;
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
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;
import com.warfare.darkannihilation.utils.ScenesStack;

public class MainGame extends BaseApp {
    private final ScenesStack scenesStack = new ScenesStack();
    private final MainGameManager mainGameManager = new MainGameManager(this, scenesStack);
    private Frontend frontend;
    private ErrorScene errorScene;

    private static final double FPS_61 = 1 / 61f;
    private static final double FPS_60 = 1 / 60f;
    private static final double FPS_59 = 1 / 59f;
    private double accumulator;

    AssetManagerSuper assetManager;
    Loading loading;

    @Override
    public void create() {
        super.create();
        assetManager = new AssetManagerSuper();
        Resources.setProviders(new ImageHub(assetManager), new SoundHub(assetManager), new FontHub(assetManager), new LocaleHub(assetManager), assetManager);

        Menu menu = new Menu(mainGameManager);

        assetManager.finishLoading();
        getImages().boot();
        getFonts().boot();
        getSounds().boot();
        getLocales().boot();

        menu.create();
        scenesStack.push(menu);

        getImages().lazyLoading();
        getSounds().lazyLoading();
        getFonts().lazyLoading();
        getLocales().lazyLoading();

        frontend = new Frontend(scenesStack);
        loading = new Loading(mainGameManager, scenesStack);
        errorScene = new ErrorScene(this, mainGameManager, scenesStack);

        resume();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            print("Error in thread:", thread);
            Gdx.app.postRunnable(() -> error(throwable));
        });
    }

    @Override
    public void render() {
        try {
            Watch.update();

            if (APPLY_ACCUMULATOR) {
                accumulator += delta;
                while (accumulator >= FPS_61) {
                    scenesStack.lastScene.update();

                    accumulator -= FPS_60;
                    if (accumulator < FPS_59 - FPS_60) accumulator = 0;
                }
            } else {
                scenesStack.lastScene.update();
            }

            frontend.render();
        } catch (IllegalStateException exception) {
            try {
                spriteBatch.end();
            } catch (Exception e) {
                exception.printStackTrace();
                error(exception);
            }
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
        scenesStack.resumeScene();
    }

    @Override
    public void pause() {
        scenesStack.pauseScene();
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
