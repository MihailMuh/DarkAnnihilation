package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public abstract class PoolWrap<T> extends Pool<T> {
    private final Array<T> items;

    public PoolWrap(Array<T> items) {
        super(items.items.length);
        this.items = items;
        fill(items.items.length);
    }

    @Override
    public T obtain() {
        T item = super.obtain();
        items.add(item);
        return item;
    }
}
