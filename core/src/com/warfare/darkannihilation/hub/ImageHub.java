package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.FALCON_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MENU_ATLAS;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.screens.StaticScreen;
import com.warfare.darkannihilation.utils.AnimationSuper;
import com.warfare.darkannihilation.utils.AssetManagerSuper;

public class ImageHub extends BaseHub {
    private TextureAtlas commonAtlas;
    private TextureAtlas levelAtlas, characterAtlas;

    public static StaticScreen loadingScreen;
    public AnimationSuper starScreenGIF, menuScreenGIF;
    public AnimationSuper defaultExplosionAnim, tripleExplosionAnim, hugeExplosionAnim;

    public AtlasRegion[] vadersImages;
    public AtlasRegion buttonPress, buttonNotPress;
    public AtlasRegion fullHeartBlue, halfHeartBlue, fullHeartRed, halfHeartRed, nullHeartRed;
    public AtlasRegion demomanImg, bombImg, factoryImg, minionImg, tripleFighterImg, attentionImg, rocketImg;
    public AtlasRegion millenniumFalcon;
    public AtlasRegion bulletImg;
    public AtlasRegion blackColor;
    public AtlasRegion healthKitImg;
    public AtlasRegion gameOverScreen;
    public AtlasRegion bulletEnemyImg;

    public ImageHub(AssetManagerSuper assetManagerSuper) {
        super(assetManagerSuper);

        assetManagerSuper.loadAtlas("common.atlas");
    }

    @Override
    public void boot() {
        commonAtlas = assetManager.get("common.atlas");

        buttonPress = commonAtlas.findRegion("button_press");
        buttonNotPress = commonAtlas.findRegion("button_not_press");
    }

    @Override
    public void lazyLoading() {
        defaultExplosionAnim = new AnimationSuper(assetManager.getAtlasRegions(commonAtlas, "default_explosion"), 0.02f);
        hugeExplosionAnim = new AnimationSuper(assetManager.getAtlasRegions(commonAtlas, "skull_explosion"), 0.05f);
        tripleExplosionAnim = new AnimationSuper(assetManager.getAtlasRegions(commonAtlas, "triple_explosion"), 0.03f);
        loadingScreen = new StaticScreen(new AnimationSuper(assetManager.getAtlasRegions(commonAtlas, "loading"), 0.05f));

        blackColor = commonAtlas.findRegion("dark_null");
        gameOverScreen = commonAtlas.findRegion("gameover");

        fullHeartBlue = commonAtlas.findRegion("full_blue_heart");
        halfHeartBlue = commonAtlas.findRegion("half_blue_heart");
        fullHeartRed = commonAtlas.findRegion("full_heart");
        halfHeartRed = commonAtlas.findRegion("half_heart");
        nullHeartRed = commonAtlas.findRegion("non_heart");

        healthKitImg = commonAtlas.findRegion("health");
        bulletEnemyImg = commonAtlas.findRegion("bullet_enemy");
    }

    public void loadMenuImages() {
        assetManager.loadAtlas(MENU_ATLAS);
    }

    public void getMenuImages() {
        menuScreenGIF = new AnimationSuper(assetManager.getAtlasRegions(MENU_ATLAS, "menu_screen"), 0.11f);
    }

    public void disposeMenuImages() {
        assetManager.unload(MENU_ATLAS);
        menuScreenGIF = null;
    }

    public void loadFirstLevelImages() {
        assetManager.loadAtlas(FIRST_LEVEL_ATLAS);
        assetManager.loadAtlas(FALCON_ATLAS);
    }

    public void getFirstLevelImages() {
        levelAtlas = assetManager.get(FIRST_LEVEL_ATLAS);
        vadersImages = new AtlasRegion[]{levelAtlas.findRegion("vader", 0),
                levelAtlas.findRegion("vader", 1), levelAtlas.findRegion("vader", 2)};

        characterAtlas = assetManager.get(FALCON_ATLAS);
        millenniumFalcon = characterAtlas.findRegion("ship");
        bulletImg = characterAtlas.findRegion("bullet");

        demomanImg = levelAtlas.findRegion("demoman");
        bombImg = levelAtlas.findRegion("bomb");
        factoryImg = levelAtlas.findRegion("factory");
        minionImg = levelAtlas.findRegion("minion");
        tripleFighterImg = levelAtlas.findRegion("triple_fighter");
        attentionImg = levelAtlas.findRegion("attention");
        rocketImg = levelAtlas.findRegion("rocket");

        starScreenGIF = new AnimationSuper(assetManager.getAtlasRegions(levelAtlas, "star_screen"), 0.07f);
    }

    public void disposeFirstLevelImages() {
        assetManager.unload(FIRST_LEVEL_ATLAS);
        assetManager.unload(FALCON_ATLAS);

        levelAtlas = null;
        vadersImages = null;
        starScreenGIF = null;
        demomanImg = null;
        bombImg = null;
        factoryImg = null;
        minionImg = null;
        tripleFighterImg = null;
        attentionImg = null;
        rocketImg = null;

        characterAtlas = null;
        millenniumFalcon = null;
        bulletImg = null;
    }
}
