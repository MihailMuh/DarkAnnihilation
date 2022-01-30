package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.service.Service.printErr;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.systemd.service.Processor;

public class AssetManagerSuper extends AssetManager {
    public FileHandleResolver resolver = getFileHandleResolver();

    public AssetManagerSuper() {
        Texture.setAssetManager(this);
    }

    public void loadAtlas(String path) {
        load(path, TextureAtlas.class);
    }

    public void loadSound(String path) {
        load(path, Sound.class);
    }

    public void loadMusic(String path) {
        load(path, Music.class);
    }

    public TextureAtlas.AtlasRegion[] getAtlasRegions(String path, String name) {
        return getAtlasRegions(get(path, TextureAtlas.class), name);
    }

    public TextureAtlas.AtlasRegion[] getAtlasRegions(TextureAtlas textureAtlas, String name) {
        Array<TextureAtlas.AtlasRegion> atlasRegions = new Array<>(10);
        int i = 0;
        while (true) {
            TextureAtlas.AtlasRegion region = textureAtlas.findRegion(name, i);
            if (region == null) {
                break;
            }
            atlasRegions.add(region);
            i++;
        }

        return atlasRegions.toArray(TextureAtlas.AtlasRegion.class);
    }

    @Override
    public synchronized void unload(String fileName) {
        try {
            super.unload(fileName);
        } catch (Exception e) {
            printErr("Error in assetManagerWrap", e);
            Processor.postToUI(() -> super.unload(fileName));
        }
    }
}
