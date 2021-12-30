package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.utils.AssetManagerWrap;

public class ResourcesManager extends AssetManagerWrap {
    public ResourcesManager() {
        Texture.setAssetManager(this);
    }
}
