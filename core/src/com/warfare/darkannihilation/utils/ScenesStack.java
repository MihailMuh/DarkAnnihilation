package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;

import java.util.Iterator;

public class ScenesStack implements Iterable<Scene> {
    private final Scene blankScene = new Scene(null) {
        @Override
        public void render() {
        }
    };
    private final Array<Scene> scenes = new Array<>(true, 4, Scene.class);

    public Scene lastScene;
    public int size;

    public void push(Scene scene) {
        lastScene = scene;
        scenes.add(scene);
        size = scenes.size;
    }

    public Scene pop() {
        if (size < 2) lastScene = blankScene;
        else lastScene = scenes.items[size - 2];

        Scene removed = scenes.pop();
        size = scenes.size;

        return removed;
    }

    @Override
    public Iterator<Scene> iterator() {
        return scenes.iterator();
    }
}
