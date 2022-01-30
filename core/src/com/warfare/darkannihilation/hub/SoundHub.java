package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_MUSIC;
import static com.warfare.darkannihilation.constants.Assets.LASER_SOUND;
import static com.warfare.darkannihilation.constants.Assets.MENU_MUSIC;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.warfare.darkannihilation.utils.AssetManagerSuper;

public class SoundHub extends BaseHub {
    private boolean cache = false;

    public Sound laserSound;

    public Music menuMusic;
    public Music firstLevelMusic;

    public SoundHub(AssetManagerSuper assetManager) {
        super(assetManager);
    }

    public void getMenuSounds() {
        menuMusic = assetManager.get(MENU_MUSIC);
    }

    public void disposeMenuSounds() {
        assetManager.unload(MENU_MUSIC);
        menuMusic = null;
    }

    public void loadGameSounds() {
        assetManager.loadMusic(FIRST_LEVEL_MUSIC);

        if (!cache) {
            assetManager.loadSound(LASER_SOUND);
            cache = true;
        }
    }

    public void getGameSounds() {
        firstLevelMusic = assetManager.get(FIRST_LEVEL_MUSIC);
        laserSound = assetManager.get(LASER_SOUND);
    }

    public void disposeGameSounds() {
        assetManager.unload(FIRST_LEVEL_MUSIC);
        firstLevelMusic = null;
    }
}
