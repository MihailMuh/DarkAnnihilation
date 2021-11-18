package com.warfare.darkannihilation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetManagerWrap extends AssetManager {
    public void loadAtlas(String path) {
        load(path, TextureAtlas.class);
    }
}
