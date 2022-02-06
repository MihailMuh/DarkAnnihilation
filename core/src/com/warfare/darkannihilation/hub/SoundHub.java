package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.ATTENTION_SOUND;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_MUSIC;
import static com.warfare.darkannihilation.constants.Assets.LASER_SOUND;
import static com.warfare.darkannihilation.constants.Assets.MENU_MUSIC;
import static com.warfare.darkannihilation.constants.Assets.METAL_SOUND;
import static com.warfare.darkannihilation.constants.Assets.SHOTGUN_SOUND;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.utils.audio.Audio;
import com.warfare.darkannihilation.utils.audio.MusicWrap;
import com.warfare.darkannihilation.utils.audio.SoundWrap;

public class SoundHub extends BaseHub {
    private final Array<Audio> allMusic = new Array<>(3);
    private boolean cache = false;

    public SoundWrap metalSound;
    public SoundWrap bigLaserSound;
    public SoundWrap laserSound;
    public SoundWrap attentionSound;

    public MusicWrap menuMusic;
    public MusicWrap firstLevelMusic;

    public SoundHub(AssetManagerSuper assetManager) {
        super(assetManager);
    }

    public void loadMenuSounds() {
        assetManager.loadMusic(MENU_MUSIC);
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
        assetManager.loadSound(ATTENTION_SOUND);

        if (!cache) {
            assetManager.loadSound(LASER_SOUND);
            assetManager.loadSound(METAL_SOUND);
            assetManager.loadSound(SHOTGUN_SOUND);
        }
    }

    public void getGameSounds() {
        firstLevelMusic = assetManager.getMusic(FIRST_LEVEL_MUSIC, 0.85f);
        attentionSound = assetManager.getSound(ATTENTION_SOUND, 0.85f);
        allMusic.add(firstLevelMusic, attentionSound);

        if (!cache) {
            laserSound = assetManager.getSound(LASER_SOUND, 0.17f);
            metalSound = assetManager.getSound(METAL_SOUND, 0.45f);
            bigLaserSound = assetManager.getSound(LASER_SOUND, 0.6f);

            cache = true;

            allMusic.add(laserSound, metalSound, bigLaserSound);
        }
    }

    public void disposeGameSounds() {
        assetManager.unload(FIRST_LEVEL_MUSIC);
        allMusic.removeValue(firstLevelMusic, true);
        allMusic.removeValue(attentionSound, true);

        firstLevelMusic = null;
        attentionSound = null;
    }

    public void setVolume(float newVolume) {
        if (newVolume < 0) newVolume = 0;

        for (Audio audio : allMusic) audio.setVolume(newVolume);
    }
}
