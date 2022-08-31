package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.COMMON_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.DEATH_STAR_LASER;
import static com.warfare.darkannihilation.constants.Assets.EXPLOSIONS_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_SCREEN_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MENU_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MILLENNIUM_VS_STAR;
import static com.warfare.darkannihilation.constants.Constants.ANIMATION_SCREEN_WIDTH;
import static com.warfare.darkannihilation.constants.Names.EMERALD;
import static com.warfare.darkannihilation.constants.Names.SATURN;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.player.Player;

public class ImageHub extends BaseHub {
    private String versusScreenName;
    private TextureAtlas commonAtlas;

    public Animation<AtlasRegion> starScreenGIF, menuScreenGIF;
    public Animation<AtlasRegion> defaultExplosionAnim, tripleExplosionAnim, hugeExplosionAnim;
    public Animation<AtlasRegion> deathStarLaser;

    public AtlasRegion[] vadersImages, deathStarImages, changerGunsImages;
    public AtlasRegion buttonPress, buttonNotPress;
    public AtlasRegion fullHeartBlue, halfHeartBlue, fullHeartRed, halfHeartRed, nullHeartRed;
    public AtlasRegion demomanImg, bombImg, factoryImg, minionImg, tripleFighterImg, attentionImg, rocketImg, laserImg;
    public AtlasRegion millenniumFalcon;
    public AtlasRegion bulletImg, buckshot;
    public AtlasRegion blackColor, whiteColor, redColor;
    public AtlasRegion healthKitImg;
    public AtlasRegion sunriseBomb, sunriseBombRed;
    public AtlasRegion starShield;
    public AtlasRegion gunKit;
    public AtlasRegion pauseButton;

    public AtlasRegion gameOverScreen;
    public AtlasRegion versusScreen;

    public ImageHub(AssetManagerSuper assetManager) {
        super(assetManager);

        assetManager.loadAtlas(COMMON_ATLAS);
        assetManager.loadAtlas(EXPLOSIONS_ATLAS);
    }

    private void getColorsFromCommonAtlas() {
        whiteColor = commonAtlas.findRegion("white");
        redColor = commonAtlas.findRegion("red");
    }

    @Override
    public void boot() {
        commonAtlas = assetManager.get(COMMON_ATLAS);

        buttonPress = commonAtlas.findRegion("button_press");
        buttonNotPress = commonAtlas.findRegion("button_not_press");
        pauseButton = commonAtlas.findRegion("pause_button");
    }

    @Override
    public void lazyLoading() {
        blackColor = commonAtlas.findRegion("black");
        getColorsFromCommonAtlas();

        TextureAtlas explosionsAtlas = assetManager.get(EXPLOSIONS_ATLAS);
        defaultExplosionAnim = assetManager.getAnimation(explosionsAtlas, "default_explosion", 0.02f, 1.2f);
        hugeExplosionAnim = assetManager.getAnimation(explosionsAtlas, "skull_explosion", 0.05f, 1.2f);
        tripleExplosionAnim = assetManager.getAnimation(explosionsAtlas, "triple_explosion", 0.03f, 1.2f);
        defaultExplosionAnim.setPlayMode(Animation.PlayMode.NORMAL);
        hugeExplosionAnim.setPlayMode(Animation.PlayMode.NORMAL);
        tripleExplosionAnim.setPlayMode(Animation.PlayMode.NORMAL);

        gameOverScreen = commonAtlas.findRegion("gameover");
    }

    public void loadMenuImages() {
        assetManager.loadAtlas(MENU_ATLAS);
    }

    public void getMenuImages() {
        menuScreenGIF = assetManager.getAnimation(assetManager.get(MENU_ATLAS), "menu_screen", 0.11f, 1.4f);
    }

    public void disposeMenuImages() {
        assetManager.unload(MENU_ATLAS);
    }

    private int getIndexForGunKitByCharacterName() {
        switch (Player.CHARACTER_NAME) {
            case SATURN:
                return 1;
            case EMERALD:
                return 2;
            default:
                return 0;
        }
    }

    private int getIndexForChangerGunsByCharacterName() {
        return getIndexForGunKitByCharacterName() * 3;
    }

    public void loadFirstLevelImages() {
        assetManager.loadAtlas(FIRST_LEVEL_ATLAS);
        assetManager.loadAtlas(FIRST_LEVEL_SCREEN_ATLAS);
    }

    public void getFirstLevelImages() {
        TextureAtlas firstLevelAtlas = assetManager.get(FIRST_LEVEL_ATLAS);

        vadersImages = new AtlasRegion[]{firstLevelAtlas.findRegion("vader", 0),
                firstLevelAtlas.findRegion("vader", 1), firstLevelAtlas.findRegion("vader", 2)};
        deathStarImages = new AtlasRegion[]{firstLevelAtlas.findRegion("death_star", 0),
                firstLevelAtlas.findRegion("death_star", 1), firstLevelAtlas.findRegion("death_star", 2)};

        int characterIndex = getIndexForChangerGunsByCharacterName();
        changerGunsImages = new AtlasRegion[]{
                firstLevelAtlas.findRegion("changer_guns", characterIndex),
                firstLevelAtlas.findRegion("changer_guns", characterIndex + 1),
                firstLevelAtlas.findRegion("changer_guns", characterIndex + 2)
        };

        whiteColor = firstLevelAtlas.findRegion("white");
        redColor = firstLevelAtlas.findRegion("red");

        millenniumFalcon = firstLevelAtlas.findRegion("ship");
        bulletImg = firstLevelAtlas.findRegion("bullet");
        buckshot = firstLevelAtlas.findRegion("buckshot");

        demomanImg = firstLevelAtlas.findRegion("demoman");
        bombImg = firstLevelAtlas.findRegion("bomb");
        factoryImg = firstLevelAtlas.findRegion("factory");
        minionImg = firstLevelAtlas.findRegion("minion");
        tripleFighterImg = firstLevelAtlas.findRegion("triple_fighter");
        attentionImg = firstLevelAtlas.findRegion("attention");
        rocketImg = firstLevelAtlas.findRegion("rocket");
        laserImg = firstLevelAtlas.findRegion("laser");

        fullHeartBlue = firstLevelAtlas.findRegion("full_blue_heart");
        halfHeartBlue = firstLevelAtlas.findRegion("half_blue_heart");
        fullHeartRed = firstLevelAtlas.findRegion("full_heart");
        halfHeartRed = firstLevelAtlas.findRegion("half_heart");
        nullHeartRed = firstLevelAtlas.findRegion("non_heart");

        healthKitImg = firstLevelAtlas.findRegion("health");

        sunriseBomb = firstLevelAtlas.findRegion("sunrise_bomb");
        sunriseBombRed = firstLevelAtlas.findRegion("sunrise_bomb_red");

        starShield = firstLevelAtlas.findRegion("star_shield");

        gunKit = firstLevelAtlas.findRegion("gun_kit", getIndexForGunKitByCharacterName());

        starScreenGIF = assetManager.getAnimation(assetManager.get(FIRST_LEVEL_SCREEN_ATLAS), "star_screen", 0.07f, 2);
    }

    public void disposeFirstLevelImages() {
        assetManager.unload(FIRST_LEVEL_ATLAS);
        assetManager.unload(FIRST_LEVEL_SCREEN_ATLAS);

        getColorsFromCommonAtlas();
    }

    public void loadVersusImage(byte playerName, byte bossName) {
        assetManager.load(MILLENNIUM_VS_STAR, Texture.class);
    }

    public void getVersusImage() {
        Texture versusTexture = assetManager.get(MILLENNIUM_VS_STAR, Texture.class);
        versusScreen = new AtlasRegion(versusTexture, 0, 0, versusTexture.getWidth(), versusTexture.getHeight());
        versusScreenName = MILLENNIUM_VS_STAR;
    }

    public void loadDeathStarLaserAnimation() {
        assetManager.load(DEATH_STAR_LASER, TextureAtlas.class);
    }

    public void getDeathStarLaserAnimation() {
        deathStarLaser = assetManager.getAnimation(assetManager.get(DEATH_STAR_LASER), "death_star_laser", 0.02f, 1);
        deathStarLaser.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        AtlasRegion lastFrame = deathStarLaser.getKeyFrames()[deathStarLaser.getKeyFrames().length - 1];
        float scale = ANIMATION_SCREEN_WIDTH / (float) lastFrame.originalWidth;
        lastFrame.originalWidth *= scale;
        lastFrame.originalHeight *= scale;

    }

    public void disposeVersusImage() {
        assetManager.unload(versusScreenName);
    }
}
