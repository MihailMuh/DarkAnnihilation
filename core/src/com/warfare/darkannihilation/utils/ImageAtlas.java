package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class ImageAtlas {
    private final TextureAtlas atlas;

    public ImageAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    private Image get(AtlasRegion region, float scale) {
        if (region != null) return new Image(region, scale);
        return null;
    }

    private Image get(AtlasRegion region) {
        return get(region, 1);
    }

    public Image getImage(String name) {
        return getImage(name, 1f);
    }

    public Image getImage(String name, float scale) {
        return get(atlas.findRegion(name), scale);
    }

    public Image getImage(String name, int index) {
        return get(atlas.findRegion(name, index));
    }

    public Image getImage(String name, int index, float scale) {
        return get(atlas.findRegion(name, index), scale);
    }
}
