package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.Settings.APPLY_ACCUMULATOR;
import static com.warfare.darkannihilation.hub.Resources.getBatch;
import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
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
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;
import com.warfare.darkannihilation.utils.ScenesArray;

public class MainGame extends BaseApp {
    private final ScenesArray scenesArray = new ScenesArray();
    private final MainGameManager mainGameManager = new MainGameManager(scenesArray);
    private Frontend frontend;
    private ErrorScene errorScene;

    private static final double FPS_61 = 1 / 61f;
    private static final double FPS_60 = 1 / 60f;
    private static final double FPS_59 = 1 / 59f;
    private double accumulator;

    AssetManagerSuper assetManager;

    @Override
    public void create() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            print("Error in thread:", thread);
            Gdx.app.postRunnable(() -> error(throwable));
        });

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
        scenesArray.add(menu);

        getImages().lazyLoading();
        getSounds().lazyLoading();
        getFonts().lazyLoading();
        getLocales().lazyLoading();

        frontend = new Frontend(scenesArray);
        errorScene = new ErrorScene(this, mainGameManager, scenesArray);

        resume();
    }

    @Override
    public void render() {
        try {
            Watch.update();

            if (APPLY_ACCUMULATOR) {
                accumulator += delta;
                while (accumulator >= FPS_61) {
                    scenesArray.updateScenes();

                    accumulator -= FPS_60;
                    if (accumulator < FPS_59 - FPS_60) accumulator = 0;
                }
            } else {
                scenesArray.updateScenes();
            }

            frontend.render();
        } catch (IllegalStateException exception) {
            try {
                getBatch().end();
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
        scenesArray.resumeLastScene();
    }

    @Override
    public void pause() {
        scenesArray.pauseLastScene();
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
