package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Pool;

public abstract class PoolWrap<T> extends Pool<T> {
    private final ArrayG<T> items;

    public PoolWrap(ArrayG<T> items) {
        super(items.items.length);
        this.items = items;
        fill(items.capacity);
    }

    @Override
    public T obtain() {
        T item = super.obtain();
        items.add(item);
        return item;
    }
}
