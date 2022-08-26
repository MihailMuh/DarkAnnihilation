package com.warfare.darkannihilation.scenes.versus;

import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_VERSUS_SCREEN_IN_SECS;
import static com.warfare.darkannihilation.hub.Resources.getAssetManager;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Watch.timeOnPause;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Watch;

public class VersusScene extends Scene {
    private float lastVersus;
    private boolean resourcesLoaded = false;

    public VersusScene(MainGameManager mainGameManager, byte playerName, byte bossName) {
        super(mainGameManager);

        getImages().loadVersusImage(playerName, bossName);
        getSounds().loadDeathStarMusic();
        getImages().loadInCycle();

        clickListener = new VersusClickListener(() -> {
            if (resourcesLoaded) mainGameManager.finishScene(this);
        });
    }

    @Override
    public void create() {
        super.create();
        getImages().getVersusImage();
        getSounds().getDeathStarMusic();
        getSounds().firstLevelMusic.stop();
        getSounds().deathStarMusic.play();

        screen = new VersusScreen(getImages().versusScreen);

        lastVersus = timeOnPause;
        Watch.stopTime();
    }

    @Override
    public void render() {
        screen.render();
    }

    @Override
    public void update() {
        if (!resourcesLoaded) {
            getImages().loadDeathStarLaserAnimation();
            getAssetManager().finishLoading();
            getImages().getDeathStarLaserAnimation();
            getPools().iniStarLaserPool();

            resourcesLoaded = true;
        }
        if (timeOnPause - lastVersus > DEATH_STAR_VERSUS_SCREEN_IN_SECS) {
            lastVersus = Integer.MAX_VALUE;
            mainGameManager.finishScene(this);
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        getImages().disposeVersusImage();
        Watch.resetTime();
    }
}
