package com.warfare.darkannihilation.scenes.loading;

import static com.warfare.darkannihilation.hub.Resources.getAssetManager;
import static com.warfare.darkannihilation.hub.Resources.getBatch;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.ScenesArray;

public class Loading extends Scene {
    private final ScenesArray scenesArray;
    private final Scene sceneToStart;

    private float alpha = 0;
    private byte status = -2;

    public Loading(MainGameManager mainGameManager, ScenesArray scenesArray, Scene sceneToStart) {
        super(mainGameManager, new LoadingClickListener());
        this.scenesArray = scenesArray;
        this.sceneToStart = sceneToStart;
    }

    @Override
    public void render() {
        getBatch().setColor(0, 0, 0, alpha);
        getBatch().draw(getImages().blackColor, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        getBatch().setColor(1, 1, 1, 1);
    }

    @Override
    public void update() {
        switch (status) {
            case -2: // потускнение
                alpha += 0.011;
                getAssetManager().update();
                getSounds().setVolume(1 - alpha); // в status = -1 не делаем эту строчку, т.к. вылетает ошибка в Native, в soundWrap

                if (alpha > 1) {
                    scenesArray.removeExceptLastScene();
                    status = -1;
                    alpha = 1;
                }
                break;
            case -1: // загрузка ресурсов для новой сцены
                getAssetManager().finishLoading();

                scenesArray.insert(scenesArray.indexOf(this, true), sceneToStart); // вставляем сцену под текущую
                sceneToStart.create();

                status = 0;
                break;
            case 0: // рассвет
                alpha -= 0.011;
                getSounds().setVolume(1 - alpha);

                if (alpha < 0) {
                    alpha = 0;
                    mainGameManager.finishScene(this);
                }
        }
    }
}
