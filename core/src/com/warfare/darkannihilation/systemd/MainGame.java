package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.Constants.NUMBER_VADER;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.Screen;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.enemy.Vader;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.PoolHub;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.systemd.service.Windows;

public class MainGame extends ApplicationAdapter {
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
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Windows.refresh();
        ImageHub.load();

        Processor.post(() -> {
            PoolHub.init(explosions, bullets);

            clickListener = new OnClickListener(player);
        });
        
        player = new Player(ImageHub.millenniumFalcon);
        screen = new Screen(ImageHub.starScreen);
        for (int i = 0; i < NUMBER_VADER; i++) {
            empire.add(new Vader());
        }

        backend = new Backend(this);
        frontend = new Frontend(this);
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
    }
}
