package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.Input.Keys.BACK;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;

public class MainGame extends ApplicationAdapter {
    Scene scene;
    private ImageHub imageHub;
    private Frontend frontend;

    @Override
    public void create() {
        Gdx.graphics.setVSync(true);
        Gdx.input.setCatchKey(BACK, true);
        Windows.refresh();
        imageHub = new ImageHub();

        scene = new Menu(new MainGameManager(imageHub, this));
        scene.run();

        frontend = new Frontend(this);

        Processor.post(() -> {
            Service.sleep(1000);
            imageHub.findExplosions();
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
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Windows.refresh();

        frontend.onResize();
    }

    @Override
    public void dispose() {
        FontHub.dispose();
        Processor.dispose();
        scene.dispose();
        imageHub.dispose();
    }
}
