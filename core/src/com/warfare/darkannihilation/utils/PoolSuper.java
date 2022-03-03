package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public abstract class PoolSuper<T> implements Disposable {
    protected final Array<T> freeObjects;

    public PoolSuper(int size) {
        freeObjects = new Array<>(false, size);
        for (int i = 0; i < freeObjects.size; i++) {
            freeObjects.add(newObject());
        }
    }

    abstract protected T newObject();

    protected synchronized T obtain() {
        if (getFree() != 0) return freeObjects.pop();
        return newObject();
    }

    public synchronized void free(T object) {
        freeObjects.add(object);
    }

    public int getFree() {
        return freeObjects.size;
    }

    @Override
    public void dispose() {
        freeObjects.clear();
    }
}
