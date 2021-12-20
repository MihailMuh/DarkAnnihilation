package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.utils.AnimationG;
import com.warfare.darkannihilation.utils.AssetManagerWrap;

public final class ImageHub implements Disposable {
    final AssetManagerWrap assetManager = new AssetManagerWrap();

    private final TextureAtlas commonAtlas;
    private TextureAtlas levelAtlas, characterAtlas;

    public AnimationG starScreenGIF;
    public AnimationG menuScreenGIF;
    public static AnimationG defaultExplosionAnim;

    public static AtlasRegion[] vadersImages;
    public static AtlasRegion millenniumFalcon;
    public static AtlasRegion bulletImg;
    public static AtlasRegion buttonPress, buttonNotPress;

    public ImageHub() {
        FontHub.prepare(this);

        assetManager.loadAtlas("common.atlas");

        waitAssetMgr(0);

        commonAtlas = assetManager.get("common.atlas");
        FontHub.finish();
    }

    public void findExplosions() {
        defaultExplosionAnim = new AnimationG(assetManager.getAtlasRegions(commonAtlas,
                "default_explosion", 28), 0.02f);
    }

    private void waitAssetMgr(int millis) {
        if (millis == 0) {
            assetManager.finishLoading();
            return;
        }
        while (!assetManager.update(millis)) {
            print(assetManager.getProgress());
        }
    }

    public void loadMenuImages() {
        assetManager.loadAtlas("menu/menu_screen.atlas");

        waitAssetMgr(20);

        buttonPress = commonAtlas.findRegion("button_press");
        buttonNotPress = commonAtlas.findRegion("button_not_press");
        menuScreenGIF = new AnimationG(assetManager.getAtlasRegions("menu/menu_screen.atlas",
                "menu_screen", 12), 0.11f);
    }

    public void disposeMenuImages() {
        assetManager.unload("menu/menu_screen.atlas");
        buttonNotPress = null;
        buttonPress = null;
        menuScreenGIF = null;
    }

    public void loadGameImages() {
        assetManager.loadAtlas("first_level/first_level.atlas");
        assetManager.loadAtlas("players/falcon.atlas");

        waitAssetMgr(0);

        levelAtlas = assetManager.get("first_level/first_level.atlas");
        vadersImages = new AtlasRegion[]{levelAtlas.findRegion("vader", 0),
                levelAtlas.findRegion("vader", 1), levelAtlas.findRegion("vader", 2)};

        characterAtlas = assetManager.get("players/falcon.atlas");
        millenniumFalcon = characterAtlas.findRegion("ship");
        bulletImg = characterAtlas.findRegion("bullet");
        starScreenGIF = new AnimationG(assetManager.getAtlasRegions(levelAtlas,
                "star_screen", 24), 0.07f);
    }

    public void disposeGameImages() {
        assetManager.unload("first_level/first_level.atlas");
        assetManager.unload("players/falcon.atlas");

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
