package com.warfare.darkannihilation.scenes.loading;

import static com.warfare.darkannihilation.hub.Resources.getAssetManager;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.ScenesStack;

public class Loading extends Scene {
    private final ScenesStack scenesStack;
    private Scene sceneToRun, sceneToUpdate;

    private float alpha;
    private byte status;

    public Loading(MainGameManager mainGameManager, ScenesStack scenesStack) {
        super(mainGameManager, new LoadingClickListener());
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
        spriteBatch.setColor(0, 0, 0, alpha);
        getImages().blackColor.draw(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setColor(1, 1, 1, 1);
    }

    @Override
    public void update() {
        switch (status) {
            case -2: // потускнение
                alpha += 0.011;
                sceneToUpdate.update();
                getAssetManager().update();
                getSounds().setVolume(1 - alpha); // в status = -1 не делаем эту строчку, т.к. вылетает ошибка в Native, в soundWrap

                if (alpha > 1) {
                    mainGameManager.finishAllScenes();
                    scenesStack.push(this);
                    status = -1;
                    alpha = 1;
                }
                break;
            case -1: // загрузка новой сцены
                getAssetManager().finishLoading();
                mainGameManager.finishScene();
                mainGameManager.startScene(sceneToRun, false);
                sceneToUpdate = scenesStack.lastScene;
                mainGameManager.startScene(this, false);

                status = 0;
                break;
            case 0: // рассвет
                alpha -= 0.011;
                getSounds().setVolume(1 - alpha);

                if (alpha < 0) {
                    alpha = 0;
                    mainGameManager.finishScene();
                }
                sceneToUpdate.update();
        }
    }
}
