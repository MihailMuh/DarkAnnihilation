package com.warfare.darkannihilation.scenes.versus;

import static com.warfare.darkannihilation.constants.Constants.BOSS_VERSUS_SCREEN_IN_SECS;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.ClickListener;

public class VersusScene extends Scene {
    private float lastVersus;

    public VersusScene(MainGameManager mainGameManager, byte playerName, byte bossName) {
        super(mainGameManager, new ClickListener());

        getImages().loadVersusImage(playerName, bossName);
        getImages().loadInCycle();
    }

    @Override
    public void create() {
        getImages().getVersusImage();

        screen = new VersusScreen(getImages().versusScreen);
        lastVersus = time;
    }

    @Override
    public void render() {
        screen.render();
    }

    @Override
    public void update() {
        if (time - lastVersus > BOSS_VERSUS_SCREEN_IN_SECS) {
            lastVersus = Float.MAX_VALUE;

            Processor.postTask(() -> {
                mainGameManager.finishScene();
                getImages().disposeVersusImage();
            });
        }
    }
}
