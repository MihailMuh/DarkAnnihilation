package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.Pool;

public abstract class PoolWrap<T> extends Pool<T> {
    public PoolWrap() {
        super();
        fill(16);
    }

    public PoolWrap(int initialCapacity) {
        super(initialCapacity);
        fill(initialCapacity);
    }

    public PoolWrap(int initialCapacity, int max) {
        super(initialCapacity, max);
        fill(initialCapacity);
    }
}
