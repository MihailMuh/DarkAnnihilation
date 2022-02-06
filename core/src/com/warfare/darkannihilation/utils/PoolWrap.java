package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public abstract class PoolWrap<T> extends Pool<T> {
    private final Array<T> items;

    public PoolWrap(Array<T> items, int capacity) {
        super(capacity);
        this.items = items;
        fill(items.size);
    }

    public PoolWrap(Array<T> items) {
        this(items, items.items.length);
    }

    @Override
    public T obtain() {
        T item = super.obtain();
        items.add(item);
        return item;
    }
}
