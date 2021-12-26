package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.utils.AnimationG;
import com.warfare.darkannihilation.utils.AssetManagerWrap;

public final class ImageHub implements Disposable {
    public final AssetManagerWrap assetManager = new AssetManagerWrap();

    private final TextureAtlas commonAtlas;
    private TextureAtlas levelAtlas, characterAtlas;

    public AnimationG loadingScreenGIF, starScreenGIF, menuScreenGIF;
    public AnimationG defaultExplosionAnim, tripleExplosionAnim, hugeExplosionAnim;

    public static AtlasRegion[] vadersImages;
    public AtlasRegion demomanImg, bombImg, factoryImg, minionImg;
    public static AtlasRegion millenniumFalcon;
    public AtlasRegion bulletImg;
    public static AtlasRegion buttonPress, buttonNotPress;

    public ImageHub() {
        Texture.setAssetManager(assetManager);
        FontHub.prepare(this);

        assetManager.loadAtlas("common.atlas");
        assetManager.finishLoading();
        commonAtlas = assetManager.get("common.atlas");

        FontHub.finish();
    }

    public void lazyLoading() {
        defaultExplosionAnim = new AnimationG(assetManager.getAtlasRegions(commonAtlas, "default_explosion", 28), 0.02f);
        hugeExplosionAnim = new AnimationG(assetManager.getAtlasRegions(commonAtlas, "skull_explosion", 13), 0.05f);
        tripleExplosionAnim = new AnimationG(assetManager.getAtlasRegions(commonAtlas, "triple_explosion", 23), 0.02f);
        loadingScreenGIF = new AnimationG(assetManager.getAtlasRegions(commonAtlas, "loading", 12), 0.05f);
    }

    public void getMenuImages() {
        buttonPress = commonAtlas.findRegion("button_press");
        buttonNotPress = commonAtlas.findRegion("button_not_press");
        menuScreenGIF = new AnimationG(assetManager.getAtlasRegions("menu/menu_screen.atlas", "menu_screen", 12), 0.11f);
    }

    public void disposeMenuImages() {
        assetManager.unload("menu/menu_screen.atlas");
        buttonNotPress = null;
        buttonPress = null;
        menuScreenGIF = null;
    }

    public void getGameImages() {
        levelAtlas = assetManager.get("first_level/first_level.atlas");
        vadersImages = new AtlasRegion[]{levelAtlas.findRegion("vader", 0),
                levelAtlas.findRegion("vader", 1), levelAtlas.findRegion("vader", 2)};

        characterAtlas = assetManager.get("players/falcon.atlas");
        millenniumFalcon = characterAtlas.findRegion("ship");
        bulletImg = characterAtlas.findRegion("bullet");

        demomanImg = levelAtlas.findRegion("demoman");
        bombImg = levelAtlas.findRegion("bomb");
        factoryImg = levelAtlas.findRegion("factory");
        minionImg = levelAtlas.findRegion("minion");
        starScreenGIF = new AnimationG(assetManager.getAtlasRegions(levelAtlas, "star_screen", 24), 0.07f);
    }

    public void disposeGameImages() {
        assetManager.unload("first_level/first_level.atlas");
        assetManager.unload("players/falcon.atlas");

        levelAtlas = null;
        vadersImages = null;
        starScreenGIF = null;
        demomanImg = null;
        bombImg = null;
        factoryImg = null;
        minionImg = null;

        characterAtlas = null;
        millenniumFalcon = null;
        bulletImg = null;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
