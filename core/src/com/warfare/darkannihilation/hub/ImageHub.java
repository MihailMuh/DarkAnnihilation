package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.FALCON_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MENU_ATLAS;

import com.warfare.darkannihilation.screens.StaticScreen;
import com.warfare.darkannihilation.utils.AnimationSuper;
import com.warfare.darkannihilation.utils.Image;
import com.warfare.darkannihilation.utils.ImageAtlas;

public class ImageHub extends BaseHub {
    private ImageAtlas commonAtlas;

    public StaticScreen loadingScreen;
    public AnimationSuper starScreenGIF, menuScreenGIF;
    public AnimationSuper defaultExplosionAnim, tripleExplosionAnim, hugeExplosionAnim;

    public Image[] vadersImages;
    public Image buttonPress, buttonNotPress;
    public Image fullHeartBlue, halfHeartBlue, fullHeartRed, halfHeartRed, nullHeartRed;
    public Image demomanImg, bombImg, factoryImg, minionImg, tripleFighterImg, attentionImg, rocketImg, laserImg, deathStarImg;
    public Image millenniumFalcon;
    public Image bulletImg;
    public Image blackColor;
    public Image healthKitImg;
    public Image gameOverScreen;

    public ImageHub(AssetManagerSuper assetManagerSuper) {
        super(assetManagerSuper);

        assetManagerSuper.loadAtlas("common.atlas");
    }

    @Override
    public void boot() {
        commonAtlas = assetManager.getAtlas("common.atlas");

        buttonPress = commonAtlas.getImage("button_press");
        buttonNotPress = commonAtlas.getImage("button_not_press");
    }

    @Override
    public void lazyLoading() {
        defaultExplosionAnim = assetManager.getAnimation(commonAtlas, "default_explosion", 0.02f);
        hugeExplosionAnim = assetManager.getAnimation(commonAtlas, "skull_explosion", 0.05f);
        tripleExplosionAnim = assetManager.getAnimation(commonAtlas, "triple_explosion", 0.03f);
        loadingScreen = new StaticScreen(assetManager.getAnimation(commonAtlas, "loading", 0.05f));

        blackColor = commonAtlas.getImage("dark_null");
        gameOverScreen = commonAtlas.getImage("gameover");

        fullHeartBlue = commonAtlas.getImage("full_blue_heart");
        halfHeartBlue = commonAtlas.getImage("half_blue_heart");
        fullHeartRed = commonAtlas.getImage("full_heart");
        halfHeartRed = commonAtlas.getImage("half_heart");
        nullHeartRed = commonAtlas.getImage("non_heart");

        healthKitImg = commonAtlas.getImage("health");
    }

    public void loadMenuImages() {
        assetManager.loadAtlas(MENU_ATLAS);
    }

    public void getMenuImages() {
        menuScreenGIF = assetManager.getAnimation(assetManager.getAtlas(MENU_ATLAS), "menu_screen", 0.11f);
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
        ImageAtlas levelAtlas = assetManager.getAtlas(FIRST_LEVEL_ATLAS);

        vadersImages = new Image[]{levelAtlas.getImage("vader", 0),
                levelAtlas.getImage("vader", 1), levelAtlas.getImage("vader", 2)};

        ImageAtlas characterAtlas = assetManager.getAtlas(FALCON_ATLAS);
        millenniumFalcon = characterAtlas.getImage("ship");
        bulletImg = characterAtlas.getImage("bullet");

        demomanImg = levelAtlas.getImage("demoman");
        bombImg = levelAtlas.getImage("bomb");
        factoryImg = levelAtlas.getImage("factory");
        minionImg = levelAtlas.getImage("minion");
        tripleFighterImg = levelAtlas.getImage("triple_fighter");
        attentionImg = levelAtlas.getImage("attention");
        rocketImg = levelAtlas.getImage("rocket");
        laserImg = levelAtlas.getImage("laser");
        deathStarImg = levelAtlas.getImage("boss");

        starScreenGIF = assetManager.getAnimation(levelAtlas, "star_screen", 0.07f);
    }

    public void disposeFirstLevelImages() {
        assetManager.unload(FIRST_LEVEL_ATLAS);
        assetManager.unload(FALCON_ATLAS);
    }
}
