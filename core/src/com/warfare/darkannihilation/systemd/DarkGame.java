package com.warfare.darkannihilation.systemd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.Screen;
import com.warfare.darkannihilation.Sprite;
import com.warfare.darkannihilation.systemd.service.ImageHub;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;

public class DarkGame extends ApplicationAdapter {
    private Backend backend;
    private Frontend frontend;
    private OnClickListener clickListener;

    Sprite player;
    Texture img;
    Screen screen;

    @Override
    public void create() {
        Gdx.graphics.setVSync(true);
        Windows.refresh();
        ImageHub.load();

        img = new Texture("no.jpg");
        img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player = new Sprite(img);
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
        img.dispose();
    }
}
