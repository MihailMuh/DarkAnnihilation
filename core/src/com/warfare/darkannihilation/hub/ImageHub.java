package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.FALCON_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MENU_ATLAS;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.BaseHub;
import com.warfare.darkannihilation.utils.AnimationG;

public class ImageHub extends BaseHub {
    private TextureAtlas commonAtlas;
    private TextureAtlas levelAtlas, characterAtlas;

    public AnimationG loadingScreenGIF, starScreenGIF, menuScreenGIF;
    public AnimationG defaultExplosionAnim, tripleExplosionAnim, hugeExplosionAnim;

    public static AtlasRegion[] vadersImages;
    public AtlasRegion demomanImg, bombImg, factoryImg, minionImg;
    public AtlasRegion millenniumFalcon;
    public AtlasRegion bulletImg;
    public static AtlasRegion buttonPress, buttonNotPress;

    public ImageHub(ResourcesManager resourcesManager) {
        super(resourcesManager);

        resourcesManager.loadAtlas("common.atlas");
    }

    @Override
    public void boot() {
        commonAtlas = resourcesManager.get("common.atlas");
    }

    public void lazyLoading() {
        defaultExplosionAnim = new AnimationG(resourcesManager.getAtlasRegions(commonAtlas, "default_explosion"), 0.02f);
        hugeExplosionAnim = new AnimationG(resourcesManager.getAtlasRegions(commonAtlas, "skull_explosion"), 0.05f);
        tripleExplosionAnim = new AnimationG(resourcesManager.getAtlasRegions(commonAtlas, "triple_explosion"), 0.02f);
        loadingScreenGIF = new AnimationG(resourcesManager.getAtlasRegions(commonAtlas, "loading"), 0.05f);
    }

    public void getMenuImages() {
        buttonPress = commonAtlas.findRegion("button_press");
        buttonNotPress = commonAtlas.findRegion("button_not_press");
        menuScreenGIF = new AnimationG(resourcesManager.getAtlasRegions(MENU_ATLAS, "menu_screen"), 0.11f);
    }

    public void disposeMenuImages() {
        resourcesManager.unload(MENU_ATLAS);
        buttonNotPress = null;
        buttonPress = null;
        menuScreenGIF = null;
    }

    public void getGameImages() {
        levelAtlas = resourcesManager.get(FIRST_LEVEL_ATLAS);
        vadersImages = new AtlasRegion[]{levelAtlas.findRegion("vader", 0),
                levelAtlas.findRegion("vader", 1), levelAtlas.findRegion("vader", 2)};

        characterAtlas = resourcesManager.get(FALCON_ATLAS);
        millenniumFalcon = characterAtlas.findRegion("ship");
        bulletImg = characterAtlas.findRegion("bullet");

        demomanImg = levelAtlas.findRegion("demoman");
        bombImg = levelAtlas.findRegion("bomb");
        factoryImg = levelAtlas.findRegion("factory");
        minionImg = levelAtlas.findRegion("minion");
        starScreenGIF = new AnimationG(resourcesManager.getAtlasRegions(levelAtlas, "star_screen"), 0.07f);
    }

    public void disposeGameImages() {
        resourcesManager.unload(FIRST_LEVEL_ATLAS);
        resourcesManager.unload(FALCON_ATLAS);

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
}
