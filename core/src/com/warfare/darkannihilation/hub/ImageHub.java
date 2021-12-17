package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.utils.AnimationG;
import com.warfare.darkannihilation.utils.AssetManagerWrap;
import com.warfare.darkannihilation.utils.GifDecoder;

public final class ImageHub {
    public static final Storage storage = new Storage();
    public static class Storage implements Disposable {
        final AssetManagerWrap assetManager = new AssetManagerWrap();

        public AnimationG<TextureRegion> starScreen;
        public AnimationG<AtlasRegion> defaultExplosion;

        public AtlasRegion[] vadersImages;
        public AtlasRegion millenniumFalcon;
        public AtlasRegion bulletImg;
        public AtlasRegion gameOverScreen;
        public AtlasRegion buttonPress, buttonNotPress;

        public void load() {
            TextureAtlas atlas;
            AtlasRegion[] atlasRegions;

//        starScreen = GifDecoder.loadGIFAnimation("first_level/star_screen.gif");

//        assetManager.loadAtlas("first_level/first_level.atlas");
//        assetManager.loadAtlas("players/falcon.atlas");
            assetManager.loadAtlas("common.atlas");

            FontHub.prepare();

            while (!assetManager.update()) {
            }

//        atlas = assetManager.get("first_level/first_level.atlas");
//        vadersImages = new AtlasRegion[]{atlas.findRegion("vader", 0),
//                atlas.findRegion("vader", 1), atlas.findRegion("vader", 2)};
//
//        atlas = assetManager.get("players/falcon.atlas");
//        millenniumFalcon = atlas.findRegion("ship");
//        bulletImg = atlas.findRegion("bullet");
//
            atlas = assetManager.get("common.atlas");
            buttonPress = atlas.findRegion("button_press");
            buttonNotPress = atlas.findRegion("button_not_press");

//        atlasRegions = new AtlasRegion[28];
//        for (int i = 0; i < 28; i++) {
//            atlasRegions[i] = atlas.findRegion("default_explosion", i);
//        }
//        defaultExplosion = new AnimationG<>(atlasRegions, 0.02f);

            FontHub.load();
            assetManager.finishLoading();
        }

        @Override
        public void dispose() {
            assetManager.dispose();
            starScreen.dispose();
        }
    }
}