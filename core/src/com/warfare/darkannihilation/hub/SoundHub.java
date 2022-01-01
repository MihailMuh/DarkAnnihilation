package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.LASER_SOUND;
import static com.warfare.darkannihilation.constants.Assets.MENU_MUSIC;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.warfare.darkannihilation.abstraction.BaseHub;

public class SoundHub extends BaseHub {
    public Sound laserSound;
    public Music menuMusic;

    public SoundHub(ResourcesManager resourcesManager) {
        super(resourcesManager);

        resourcesManager.loadSound("sounds/laser.mp3");
    }

    @Override
    public void boot() {
    }

    public void getMenuSounds() {
        menuMusic = resourcesManager.get(MENU_MUSIC);
    }

    public void disposeMenuSounds() {
        resourcesManager.unload(MENU_MUSIC);
        menuMusic = null;
    }

    public void loadGameSounds() {
        resourcesManager.loadSound(LASER_SOUND);
    }

    public void getGameSounds() {
        laserSound = resourcesManager.get(LASER_SOUND);
    }

    public void disposeGameSounds() {
        resourcesManager.unload(LASER_SOUND);
        laserSound = null;
    }
}
