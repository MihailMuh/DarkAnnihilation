package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_MUSIC;
import static com.warfare.darkannihilation.constants.Assets.LASER_SOUND;
import static com.warfare.darkannihilation.constants.Assets.MENU_MUSIC;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.utils.AssetManagerSuper;
import com.warfare.darkannihilation.utils.audio.Audio;
import com.warfare.darkannihilation.utils.audio.MusicWrap;
import com.warfare.darkannihilation.utils.audio.SoundWrap;

public class SoundHub extends BaseHub {
    private final Array<Audio> allMusic = new Array<>(3);
    private boolean cache = false;

    public SoundWrap laserSound;

    public MusicWrap menuMusic;
    public MusicWrap firstLevelMusic;

    public SoundHub(AssetManagerSuper assetManager) {
        super(assetManager);
    }

    public void getMenuSounds() {
        menuMusic = assetManager.getMusic(MENU_MUSIC, 1);
        allMusic.add(menuMusic);
    }

    public void disposeMenuSounds() {
        assetManager.unload(MENU_MUSIC);
        allMusic.removeValue(menuMusic, true);
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
        firstLevelMusic = assetManager.getMusic(FIRST_LEVEL_MUSIC, 0.75f);
        allMusic.add(firstLevelMusic);

        laserSound = assetManager.getSound(LASER_SOUND, 0.17f);
        allMusic.add(laserSound);
    }

    public void disposeGameSounds() {
        assetManager.unload(FIRST_LEVEL_MUSIC);
        allMusic.removeValue(firstLevelMusic, true);
        firstLevelMusic = null;
    }

    public void setVolume(float newVolume) {
        if (newVolume < 0) newVolume = 0;

        for (Audio audio : allMusic) audio.setVolume(newVolume);
    }
}
