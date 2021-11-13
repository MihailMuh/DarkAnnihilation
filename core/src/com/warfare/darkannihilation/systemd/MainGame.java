package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.Screen;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.systemd.service.ImageHub;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;

public class MainGame extends ApplicationAdapter {
    private Backend backend;
    private Frontend frontend;
    private OnClickListener clickListener;

    Player player;
    Screen screen;

    @Override
    public void create() {
        Gdx.graphics.setVSync(true);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Windows.refresh();
        ImageHub.load();

        player = new Player(ImageHub.millenniumFalcon);
        screen = new Screen(ImageHub.starScreen);

        backend = new Backend(this);
        frontend = new Frontend(this);
        clickListener = new OnClickListener(player);
    }

    @Override
    public void render() {
        Watch.update();

        backend.update();
        frontend.render();

        print(Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void resize(int width, int height) {
        Windows.refresh();

        frontend.onResize();
    }

    @Override
    public void dispose() {
        ImageHub.dispose();
        frontend.dispose();
    }
}
