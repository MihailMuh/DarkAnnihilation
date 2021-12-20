package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetManagerWrap extends AssetManager {
    public void loadAtlas(String path) {
        load(path, TextureAtlas.class);
    }

    public TextureAtlas.AtlasRegion[] getAtlasRegions(String path, String name, int len) {
        return getAtlasRegions(get(path, TextureAtlas.class), name, len);
    }

    public TextureAtlas.AtlasRegion[] getAtlasRegions(TextureAtlas textureAtlas, String name, int len) {
        TextureAtlas.AtlasRegion[] atlasRegions = new TextureAtlas.AtlasRegion[len];
        for (int i = 0; i < len; i++) {
            atlasRegions[i] = textureAtlas.findRegion(name, i);
        }

        return atlasRegions;
    }
}
