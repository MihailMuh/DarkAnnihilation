package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.ATTENTION_SOUND;
import static com.warfare.darkannihilation.constants.Assets.BOOM_SOUND;
import static com.warfare.darkannihilation.constants.Assets.FALLING_BOMB_SOUND;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_MUSIC;
import static com.warfare.darkannihilation.constants.Assets.HEAL_SOUND;
import static com.warfare.darkannihilation.constants.Assets.JOYSTICK_SOUND;
import static com.warfare.darkannihilation.constants.Assets.LASER_SOUND;
import static com.warfare.darkannihilation.constants.Assets.MEGA_BOOM_SOUND;
import static com.warfare.darkannihilation.constants.Assets.MENU_MUSIC;
import static com.warfare.darkannihilation.constants.Assets.METAL_SOUND;
import static com.warfare.darkannihilation.constants.Assets.READY_SOUND_0;
import static com.warfare.darkannihilation.constants.Assets.READY_SOUND_1;
import static com.warfare.darkannihilation.constants.Assets.SHOTGUN_SOUND;
import static com.warfare.darkannihilation.constants.Assets.SPACE_BAR_SOUND;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.utils.audio.Audio;
import com.warfare.darkannihilation.utils.audio.MusicWrap;
import com.warfare.darkannihilation.utils.audio.SoundWrap;

public class SoundHub extends BaseHub {
    private final Array<Audio> allAudio = new Array<>();
    private boolean cache = false;

    public SoundWrap metalSound;
    public SoundWrap bigLaserSound;
    public SoundWrap laserSound;
    public SoundWrap attentionSound;
    public SoundWrap boomSound, megaBoomSound;
    public SoundWrap healSound;
    public SoundWrap fallingBombSound;
    public SoundWrap joystickSound, spaceBarSound;
    public SoundWrap readySound0, readySound1;

    public MusicWrap menuMusic;
    public MusicWrap firstLevelMusic;

    public SoundHub(AssetManagerSuper assetManager) {
        super(assetManager);
        assetManager.loadSounds(JOYSTICK_SOUND, SPACE_BAR_SOUND);
    }

    @Override
    public void boot() {
        joystickSound = assetManager.getSound(JOYSTICK_SOUND, 1);
        spaceBarSound = assetManager.getSound(SPACE_BAR_SOUND, 1);
        allAudio.add(joystickSound, spaceBarSound);
    }

    public void loadMenuSounds() {
        assetManager.loadMusic(MENU_MUSIC);
    }

    public void getMenuSounds() {
        menuMusic = assetManager.getMusic(MENU_MUSIC, 1);
        allAudio.add(menuMusic);
    }

    public void disposeMenuSounds() {
        assetManager.unload(MENU_MUSIC);
        allAudio.removeValue(menuMusic, true);
    }

    public void loadGameSounds() {
        assetManager.loadMusic(FIRST_LEVEL_MUSIC);
        assetManager.loadSounds(ATTENTION_SOUND, FALLING_BOMB_SOUND);

        if (!cache) {
            assetManager.loadSounds(LASER_SOUND, METAL_SOUND, SHOTGUN_SOUND, BOOM_SOUND, MEGA_BOOM_SOUND,
                    HEAL_SOUND, READY_SOUND_0, READY_SOUND_1);
        }
    }

    public void getGameSounds() {
        firstLevelMusic = assetManager.getMusic(FIRST_LEVEL_MUSIC, 0.85f);
        attentionSound = assetManager.getSound(ATTENTION_SOUND, 0.85f);
        fallingBombSound = assetManager.getSound(FALLING_BOMB_SOUND, 0.13f);

        allAudio.add(firstLevelMusic, attentionSound, fallingBombSound);

        if (!cache) {
            laserSound = assetManager.getSound(LASER_SOUND, 0.17f);
            metalSound = assetManager.getSound(METAL_SOUND, 0.48f);
            bigLaserSound = assetManager.getSound(LASER_SOUND, 0.6f);
            boomSound = assetManager.getSound(BOOM_SOUND, 0.18f);
            megaBoomSound = assetManager.getSound(MEGA_BOOM_SOUND, 0.9f);
            healSound = assetManager.getSound(HEAL_SOUND, 0.8f);
            readySound0 = assetManager.getSound(READY_SOUND_0, 1);
            readySound1 = assetManager.getSound(READY_SOUND_1, 1);

            cache = true;

            allAudio.addAll(laserSound, metalSound, bigLaserSound, boomSound, megaBoomSound, healSound, readySound0, readySound1);
        }
    }

    public void disposeGameSounds() {
        assetManager.unload(FIRST_LEVEL_MUSIC);
        allAudio.removeValue(firstLevelMusic, true);
        allAudio.removeValue(attentionSound, true);
        allAudio.removeValue(fallingBombSound, true);
    }

    public void setVolume(float newVolume) {
        if (newVolume < 0) newVolume = 0;
        else if (newVolume > 1) newVolume = 1;

        for (Audio audio : allAudio) audio.setVolume(newVolume);
    }
}
