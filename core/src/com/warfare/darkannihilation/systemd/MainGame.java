package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.Input.Keys.BACK;
import static com.warfare.darkannihilation.Constants.NUMBER_VADER;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.screens.Screen;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.menu.Scene;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;

public class MainGame extends ApplicationAdapter {
    Scene scene;
    private Backend backend;
    private Frontend frontend;
    private OnClickListener clickListener;

    Player player;
    Screen screen;

    public static final Array<Explosion> explosions = new Array<>(10);
    public Array<Bullet> bullets = new Array<>(15);
    public Array<Warrior> empire = new Array<>(NUMBER_VADER);

    @Override
    public void create() {
        Gdx.graphics.setVSync(true);
        Gdx.input.setCatchKey(BACK, true);
        Windows.refresh();
        ImageHub.storage.load();

        Processor.post(() -> {
//            PoolHub.init(explosions, bullets);

//            clickListener = new OnClickListener(player);
        });

        scene = new Scene();
        
//        player = new Player(ImageHub.millenniumFalcon);
//        screen = new Screen(ImageHub.starScreen);
//        for (int i = 0; i < NUMBER_VADER; i++) {
//            empire.add(new Vader());
//        }
//
//        backend = new Backend(this);
        frontend = new Frontend(this);
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
        ImageHub.storage.dispose();
        FontHub.dispose();
        Processor.dispose();
        scene.dispose();
    }
}
