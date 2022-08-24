package com.warfare.darkannihilation.hub;

import static com.badlogic.gdx.Application.ApplicationType.Desktop;
import static com.warfare.darkannihilation.Settings.MUTE_MUSIC;
import static com.warfare.darkannihilation.Settings.SHOW_ASSET_MANAGER_LOGS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.warfare.darkannihilation.utils.AnimationSuper;
import com.warfare.darkannihilation.utils.Image;
import com.warfare.darkannihilation.utils.ImageAtlas;
import com.warfare.darkannihilation.utils.audio.MusicWrap;
import com.warfare.darkannihilation.utils.audio.SoundWrap;

public class AssetManagerSuper extends AssetManager {
    public final FileHandleResolver resolver;

    public AssetManagerSuper() {
        super();

        if (SHOW_ASSET_MANAGER_LOGS) getLogger().setLevel(Logger.DEBUG);

        Texture.setAssetManager(this);
        resolver = getFileHandleResolver();
    }

    public void loadAtlas(String path) {
        load(path, TextureAtlas.class);
    }

    public void loadSounds(String... paths) {
        for (String path : paths) {
            load(path, Sound.class);
        }
    }

    public SoundWrap getSound(String path, float volume) {
        if (MUTE_MUSIC) volume = 0;
        return new SoundWrap(get(path, Sound.class), volume);
    }

    public void loadMusic(String path) {
        load(path, Music.class);
    }

    public MusicWrap getMusic(String path, float volume) {
        if (MUTE_MUSIC) volume = 0;
        return new MusicWrap(get(path, Music.class), volume);
    }

    public ImageAtlas getAtlas(String name) {
        return new ImageAtlas(get(name));
    }

    public AnimationSuper getAnimation(ImageAtlas atlas, String name, float frameDuration, float scale) {
        Array<Image> images = new Array<>(true, 10, Image.class);

        int i = 0;
        while (true) {
            Image image = atlas.getImage(name, i, scale);
            if (image == null) {
                break;
            }
            images.add(image);
            i++;
        }

        return new AnimationSuper(images.toArray(Image.class), frameDuration);
    }

    @Override
    public void finishLoading() {
        while (!update()) {
        }
    }

    @Override
    public synchronized void unload(String fileName) {
        if (Gdx.app.getType() == Desktop) {
            Gdx.app.postRunnable(() -> super.unload(fileName));
        } else {
            super.unload(fileName);
        }
    }
}
