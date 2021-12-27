package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.LoadingScreen;
import com.warfare.darkannihilation.SceneWrap;
import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;

public class MainGame extends BaseApp {
    Array<Scene> scenes = new Array<>(1);
    LoadingScreen loadingScreen;
    private ImageHub imageHub;
    private Frontend frontend;

    @Override
    public void create() {
        super.create();
        imageHub = new ImageHub();
        frontend = new Frontend(this);
        scenes.add(new SceneWrap(null));

        MainGameManager mainGameManager = new MainGameManager(imageHub, this);
        mainGameManager.startScene(new Menu(mainGameManager), false);

        Processor.post(() -> {
            Service.sleep(500);
            imageHub.lazyLoading();
            loadingScreen = new LoadingScreen(imageHub.loadingScreenGIF, imageHub);
        });
    }

    @Override
    public void render() {
        Watch.update();

        scenes.peek().update();

        frontend.render();
    }

    @Override
    public void resize(int width, int height) {
        print(width, height, Windows.HARDCORE_WIDTH, Windows.HARDCORE_HEIGHT);
    }

    @Override
    public void resume() {
        super.resume();
        Texture.setAssetManager(imageHub);
        for (Scene scene : scenes) {
            scene.resume();
        }
    }

    @Override
    public void pause() {
        for (Scene scene : scenes) {
            scene.pause();
        }
    }

    @Override
    public void dispose() {
        for (Scene scene : scenes) {
            scene.dispose();
        }
        imageHub.dispose();
        Processor.dispose();
    }
}
