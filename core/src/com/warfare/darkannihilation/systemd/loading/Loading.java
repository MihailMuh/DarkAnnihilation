package com.warfare.darkannihilation.systemd.loading;

import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.screens.StaticScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.ScenesStack;

public class Loading extends Scene {
    private final ScenesStack scenesStack;
    private Scene sceneToRun, sceneToUpdate;

    private float alpha;
    private byte status;

    public Loading(MainGameManager mainGameManager, ScenesStack scenesStack) {
        super(mainGameManager, new LoadingClickListener(), new StaticScreen(getImages().loadingScreen));
        this.scenesStack = scenesStack;
    }

    public void setSceneToRun(Scene sceneToRun, Scene sceneToUpdate) {
        this.sceneToRun = sceneToRun;
        this.sceneToUpdate = sceneToUpdate;

        alpha = 0;
        status = -2;
    }

    @Override
    public void render() {
        switch (status) {
            case -2:
                alpha += 0.011;
                getSounds().setVolume(1 - alpha);

                if (alpha > 1) {
                    Gdx.app.postRunnable(() -> {
                        mainGameManager.finishAllScenes();
                        scenesStack.push(this);
                        status = -1;
                        alpha = 1;
                    });
                }
                break;
            case -1:
                if (mainGameManager.loadResources()) {
                    getSounds().setVolume(1);
                    mainGameManager.finishLastScene();
                    mainGameManager.startScene(sceneToRun, false);
                    sceneToUpdate = scenesStack.lastScene;
                    mainGameManager.startScene(this, false);
                    status = 0;
                }
                break;
            case 0:
                alpha -= 0.011;

                if (alpha < 0) {
                    alpha = 0;
                    mainGameManager.finishLastScene();
                }
        }

        spriteBatch.setColor(0, 0, 0, alpha);
        getImages().blackColor.draw(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setColor(1, 1, 1, 1);
    }

    @Override
    public void update() {
        if (status != -1) sceneToUpdate.update();
    }
}
