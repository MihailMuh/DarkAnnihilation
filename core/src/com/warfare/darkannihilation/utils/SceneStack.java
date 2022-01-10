package com.warfare.darkannihilation.utils;

import com.warfare.darkannihilation.abstraction.Scene;

import java.util.Iterator;

public class SceneStack implements Iterable<Scene>, Iterator<Scene> {
    private final Scene[] scenes = new Scene[3];
    private int index, size;

    public Scene lastScene;

    public void push(Scene newScene) {
        scenes[size++] = newScene;
        lastScene = newScene;
    }

    public void set(Scene newScene) {
        scenes[size - 1] = newScene;
        lastScene = newScene;
    }

    public void pop() {
        scenes[--size] = null;
        lastScene = scenes[size - 1];
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            scenes[i] = null;
        }
        size = 0;
        lastScene = null;
    }

    public void remove(Scene scene) {
        for (int i = 0; i < size; i++) {
            if (scenes[i] == scene) {
                size--;
                System.arraycopy(scenes, i + 1, scenes, i, size - i);
                scenes[size] = null;
                break;
            }
        }
    }

    @Override
    public Iterator<Scene> iterator() {
        index = 0;
        return this;
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public Scene next() {
        return scenes[index++];
    }
}
