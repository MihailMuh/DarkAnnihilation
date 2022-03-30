package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.service.Processor;

import java.util.Iterator;

public class ScenesStack implements Iterable<Scene> {
    private final Array<Scene> scenes = new Array<>(true, 4, Scene.class);

    public Scene lastScene;
    public int size;

    public void push(Scene scene) {
        lastScene = scene;
        scenes.add(scene);
        size = scenes.size;
    }

    public Scene pop() {
        final Array<Scene> scenes = this.scenes;

        if (size < 2) lastScene = null;
        else lastScene = scenes.items[size - 2];

        Scene removed = scenes.pop();
        size = scenes.size;

        return removed;
    }

    public void clear() {
        lastScene.pause();

        for (Iterator<Scene> iterator = iterator(); iterator.hasNext();) {
            Scene scene = iterator.next();
            Processor.post(scene::dispose);
            iterator.remove();
        }

        lastScene = null;
        size = 0;
    }

    @Override
    public Iterator<Scene> iterator() {
        return scenes.iterator();
    }
}
