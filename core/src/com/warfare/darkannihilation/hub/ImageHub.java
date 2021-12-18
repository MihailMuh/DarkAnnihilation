package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.utils.AnimationG;
import com.warfare.darkannihilation.utils.AssetManagerWrap;
import com.warfare.darkannihilation.utils.GifDecoder;

public final class ImageHub implements Disposable {
    final AssetManagerWrap assetManager = new AssetManagerWrap();

    private final TextureAtlas commonAtlas;
    private TextureAtlas levelAtlas, characterAtlas;

    public AnimationG<TextureRegion> starScreenGIF;
    public AnimationG<TextureRegion> menuScreenGIF;
    public static AnimationG<AtlasRegion> defaultExplosionAnim;

    public static AtlasRegion[] vadersImages;
    public static AtlasRegion millenniumFalcon;
    public static AtlasRegion bulletImg;
    public static AtlasRegion buttonPress, buttonNotPress;

    public ImageHub() {
        FontHub.prepare(this);

        assetManager.loadAtlas("common.atlas");

        waitAssetMgr();

        commonAtlas = assetManager.get("common.atlas");
        FontHub.finish();
    }

    public void findExplosions() {
        AtlasRegion[] atlasRegions = new AtlasRegion[28];
        for (int i = 0; i < 28; i++) {
            atlasRegions[i] = commonAtlas.findRegion("default_explosion", i);
        }
        defaultExplosionAnim = new AnimationG<>(atlasRegions, 0.02f);
    }

    private void waitAssetMgr() {
        while (!assetManager.update()) {
        }
    }

    public void loadMenuImages() {
        buttonPress = commonAtlas.findRegion("button_press");
        buttonNotPress = commonAtlas.findRegion("button_not_press");
        menuScreenGIF = GifDecoder.loadGIFAnimation("menu/menu_screen.gif");
    }

    public void disposeMenuImages() {
        menuScreenGIF.dispose();
        buttonNotPress = null;
        buttonPress = null;
        menuScreenGIF = null;
    }

    public void loadGameImages() {
        starScreenGIF = GifDecoder.loadGIFAnimation("first_level/star_screen.gif");
        assetManager.loadAtlas("first_level/first_level.atlas");
        assetManager.loadAtlas("players/falcon.atlas");

        waitAssetMgr();

        levelAtlas = assetManager.get("first_level/first_level.atlas");
        vadersImages = new AtlasRegion[]{levelAtlas.findRegion("vader", 0),
                levelAtlas.findRegion("vader", 1), levelAtlas.findRegion("vader", 2)};

        characterAtlas = assetManager.get("players/falcon.atlas");
        millenniumFalcon = characterAtlas.findRegion("ship");
        bulletImg = characterAtlas.findRegion("bullet");
    }

    public void disposeGameImages() {
        assetManager.unload("first_level/first_level.atlas");
        assetManager.unload("players/falcon.atlas");

        starScreenGIF.dispose();
        starScreenGIF = null;

        levelAtlas = null;

        characterAtlas = null;

        vadersImages = null;
        millenniumFalcon = null;
        bulletImg = null;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
