package com.warfare.darkannihilation.systemd.service;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warfare.darkannihilation.AssetManagerWrap;
import com.warfare.darkannihilation.GifDecoder;

public final class ImageHub {
    private static final AssetManagerWrap assetManager = new AssetManagerWrap();

    public static Animation<TextureRegion> starScreen;

    public static TextureAtlas.AtlasRegion[] vadersImages;
    public static TextureAtlas.AtlasRegion millenniumFalcon;

    public static void load() {
        TextureAtlas atlas;

        assetManager.loadAtlas("first_level/first_level.atlas");
        assetManager.loadAtlas("players/falcon.atlas");

        while (!assetManager.update()) {
            print(assetManager.getProgress());
        }

        starScreen = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, "first_level/star_screen.gif");

        atlas = assetManager.get("first_level/first_level.atlas");
        vadersImages = new TextureAtlas.AtlasRegion[]{atlas.findRegion("vader", 0),
                atlas.findRegion("vader", 1), atlas.findRegion("vader", 2)};

        atlas = assetManager.get("players/falcon.atlas");
        millenniumFalcon = atlas.findRegion("ship");

        assetManager.finishLoading();
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
