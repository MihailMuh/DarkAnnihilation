package com.warfare.darkannihilation.systemd.service;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warfare.darkannihilation.GifDecoder;

public final class ImageHub {
    private static final AssetManager assetManager = new AssetManager();

    public static Animation<TextureRegion> starScreen;

    public static TextureAtlas.AtlasRegion[] vadersImages;
    public static TextureAtlas.AtlasRegion millenniumFalcon;

    public static void load() {
        assetManager.load("first_level/first_level.atlas", TextureAtlas.class);

        while (!assetManager.update()) {
            print(assetManager.getProgress());
        }

        assetManager.finishLoading();
        starScreen = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, "first_level/star_screen.gif");

        TextureAtlas firstLvlAtlas = assetManager.get("first_level/first_level.atlas");

        millenniumFalcon = firstLvlAtlas.findRegion("boss");

        vadersImages = new TextureAtlas.AtlasRegion[]{firstLvlAtlas.findRegion("vader", 0),
                firstLvlAtlas.findRegion("vader", 1), firstLvlAtlas.findRegion("vader", 2)};
    }

    public static void dispose() {
        assetManager.dispose();
        dispose(starScreen);
    }

    private static void dispose(Animation<TextureRegion> animation) {
        if (animation != null) {
            animation.getKeyFrame(0).getTexture().dispose();
        }
    }
}
