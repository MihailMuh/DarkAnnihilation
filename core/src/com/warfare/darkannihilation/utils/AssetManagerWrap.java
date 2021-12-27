package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AssetManagerWrap extends AssetManager {
    public void loadAtlas(String path) {
        load(path, TextureAtlas.class);
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
}
