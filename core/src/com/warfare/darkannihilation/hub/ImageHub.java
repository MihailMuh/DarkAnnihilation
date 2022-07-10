package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.COMMON_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.EXPLOSIONS_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_SCREEN_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MENU_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MILLENNIUM_VS_STAR;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.utils.AnimationSuper;
import com.warfare.darkannihilation.utils.Image;
import com.warfare.darkannihilation.utils.ImageAtlas;

public class ImageHub extends BaseHub {
    private String versusScreenName;
    private ImageAtlas commonAtlas;

    public AnimationSuper starScreenGIF, menuScreenGIF, loadingScreen;
    public AnimationSuper defaultExplosionAnim, tripleExplosionAnim, hugeExplosionAnim;

    public Image[] vadersImages;
    public Image buttonPress, buttonNotPress;
    public Image fullHeartBlue, halfHeartBlue, fullHeartRed, halfHeartRed, nullHeartRed;
    public Image demomanImg, bombImg, factoryImg, minionImg, tripleFighterImg, attentionImg, rocketImg, laserImg, deathStarImg;
    public Image millenniumFalcon;
    public Image bulletImg;
    public Image blackColor, whiteColor, redColor;
    public Image healthKitImg;
    public Image gameOverScreen;
    public Image versusScreen;

    public ImageHub(AssetManagerSuper assetManager) {
        super(assetManager);

        assetManager.loadAtlas(COMMON_ATLAS);
        assetManager.loadAtlas(EXPLOSIONS_ATLAS);
    }

    @Override
    public void boot() {
        commonAtlas = assetManager.getAtlas(COMMON_ATLAS);

        buttonPress = commonAtlas.getImage("button_press", 1.47f);
        buttonNotPress = commonAtlas.getImage("button_not_press", 1.47f);
    }

    @Override
    public void lazyLoading() {
        loadingScreen = assetManager.getAnimation(commonAtlas, "loading", 0.05f);
        blackColor = commonAtlas.getImage("dark_null");

        ImageAtlas explosionsAtlas = assetManager.getAtlas(EXPLOSIONS_ATLAS);
        defaultExplosionAnim = assetManager.getAnimation(explosionsAtlas, "default_explosion", 0.02f, 1.2f);
        hugeExplosionAnim = assetManager.getAnimation(explosionsAtlas, "skull_explosion", 0.05f, 1.2f);
        tripleExplosionAnim = assetManager.getAnimation(explosionsAtlas, "triple_explosion", 0.03f, 1.2f);
        gameOverScreen = commonAtlas.getImage("gameover");
    }

    public void loadInCycle() {
        while (!assetManager.isFinished()) {
            Service.sleep(17);
            Gdx.app.postRunnable(assetManager::update);
        }
    }

    public void loadMenuImages() {
        assetManager.loadAtlas(MENU_ATLAS);
    }

    public void getMenuImages() {
        menuScreenGIF = assetManager.getAnimation(assetManager.getAtlas(MENU_ATLAS), "menu_screen", 0.11f, 1.4f);
    }

    public void disposeMenuImages() {
        assetManager.unload(MENU_ATLAS);
    }

    public void loadFirstLevelImages() {
        assetManager.loadAtlas(FIRST_LEVEL_ATLAS);
        assetManager.loadAtlas(FIRST_LEVEL_SCREEN_ATLAS);
    }

    public void getFirstLevelImages() {
        ImageAtlas firstLevelAtlas = assetManager.getAtlas(FIRST_LEVEL_ATLAS);

        vadersImages = new Image[]{firstLevelAtlas.getImage("vader", 0),
                firstLevelAtlas.getImage("vader", 1), firstLevelAtlas.getImage("vader", 2)};

        millenniumFalcon = firstLevelAtlas.getImage("ship");
        bulletImg = firstLevelAtlas.getImage("bullet");

        demomanImg = firstLevelAtlas.getImage("demoman");
        bombImg = firstLevelAtlas.getImage("bomb");
        factoryImg = firstLevelAtlas.getImage("factory");
        minionImg = firstLevelAtlas.getImage("minion");
        tripleFighterImg = firstLevelAtlas.getImage("triple_fighter");
        attentionImg = firstLevelAtlas.getImage("attention");
        rocketImg = firstLevelAtlas.getImage("rocket");
        laserImg = firstLevelAtlas.getImage("laser");
        deathStarImg = firstLevelAtlas.getImage("boss");

        fullHeartBlue = firstLevelAtlas.getImage("full_blue_heart");
        halfHeartBlue = firstLevelAtlas.getImage("half_blue_heart");
        fullHeartRed = firstLevelAtlas.getImage("full_heart");
        halfHeartRed = firstLevelAtlas.getImage("half_heart");
        nullHeartRed = firstLevelAtlas.getImage("non_heart");

        healthKitImg = firstLevelAtlas.getImage("health");

        whiteColor = firstLevelAtlas.getImage("white");
        redColor = firstLevelAtlas.getImage("red");

        starScreenGIF = assetManager.getAnimation(assetManager.getAtlas(FIRST_LEVEL_SCREEN_ATLAS), "star_screen", 0.07f, 2);
    }

    public void disposeFirstLevelImages() {
        assetManager.unload(FIRST_LEVEL_ATLAS);
        assetManager.unload(FIRST_LEVEL_SCREEN_ATLAS);
    }

    public void loadVersusImage(byte playerName, byte bossName) {
        assetManager.load(MILLENNIUM_VS_STAR, Texture.class);
    }

    public void getVersusImage() {
        Texture versusTexture = assetManager.get(MILLENNIUM_VS_STAR, Texture.class);
        versusScreen = new Image(new TextureAtlas.AtlasRegion(versusTexture, 0, 0, versusTexture.getWidth(), versusTexture.getHeight()));
        versusScreenName = MILLENNIUM_VS_STAR;
    }

    public void disposeVersusImage() {
        assetManager.unload(versusScreenName);
    }
}
