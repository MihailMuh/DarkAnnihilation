package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;

import java.util.Iterator;

public class ScenesList implements Iterable<Scene> {
    private final Array<Scene> scenes = new Array<>(4);
    public Scene lastScene;

    public int size() {
        return scenes.size;
    }

    public Scene get(int i) {
        return scenes.get(i);
    }

    public void add(Scene scene) {
        scenes.add(scene);

        if (!scene.isShadow) {
            lastScene = scene;
        }
    }

    public void pop() {
        int index = scenes.indexOf(lastScene, true);

        while (index >= 0) {
            scenes.removeIndex(index--);

            Scene scene = scenes.get(index);
            if (!scene.isShadow) {
                lastScene = scene;
                break;
            }
        }
    }

    public void clear() {
        scenes.removeRange(0, scenes.size - 1);
    }

    @Override
    public Iterator<Scene> iterator() {
        return scenes.iterator();
    }
}
