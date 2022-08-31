package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;

public class ScenesArray extends Array<Scene> {
    public ScenesArray() {
        super(true, 10, Scene.class);
    }

    public void map(MapAction action) {
        for (int i = 0; i < size; i++) {
            Scene scene = get(i);
            if (scene != null) action.run(scene);
        }
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

    public void backgroundUpdate() {
        for (int i = 0; i < size; i++) {
            Scene scene = get(i);
            if (scene != null && scene.update) scene.updateInThread();
        }
    }

    public void removeExceptLastScene() {
        for (int i = 0; i < size - 1; i++) {
            Scene scene = get(i);
            if (scene != null) {
                scene.dispose();
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

    public interface MapAction {
        void run(Scene scene);
    }
}
