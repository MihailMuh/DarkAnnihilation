package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.BaseApp;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.systemd.service.Watch;

public class MainGame extends BaseApp {
    Scene scene;
    private ImageHub imageHub;
    private Frontend frontend;

    @Override
    public void create() {
        super.create();
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
        super.resize(width, height);

        frontend.onResize();
    }

    @Override
    public void dispose() {
        scene.dispose();
        imageHub.dispose();
        Processor.dispose();
    }
}
