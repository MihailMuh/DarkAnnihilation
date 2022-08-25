package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.service.Processor;

public class ScenesArray extends Array<Scene> {
    public ScenesArray() {
        super(true, 10, Scene.class);
    }

    public void updateScenes() {
        for (int i = 0; i < size; i++) {
            Scene scene = get(i);
            if (scene != null && scene.update) scene.update();
        }
    }

    public void renderScenes() {
        for (Scene scene : iterator()) {
            scene.render();
        }
    }

    public void removeExceptLastScene() {
        for (int i = 0; i < size - 1; i++) {
            Scene scene = get(i);
            if (scene != null && scene.update) {
                Processor.postTask(scene::dispose);
                removeIndex(i--);
            }
        }
    }

    public Scene getSceneUnder(Scene scene) {
        int index = indexOf(scene, true);

        if (index - 1 < 0) return null;
        return get(index - 1);
    }

    public void pauseLastScene() {
        if (size >= 1) peek().pause();
    }

    public void resumeLastScene() {
        if (size >= 1) peek().resume();
    }
}
