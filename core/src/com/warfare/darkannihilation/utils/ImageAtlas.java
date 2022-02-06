package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class ImageAtlas {
    private final TextureAtlas atlas;

    public ImageAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    private Image get(AtlasRegion region) {
        if (region != null) return new Image(region);
        return null;
    }

    public Image getImage(String name) {
        return get(atlas.findRegion(name));
    }

    public Image getImage(String name, int index) {
        return get(atlas.findRegion(name, index));
    }
}
