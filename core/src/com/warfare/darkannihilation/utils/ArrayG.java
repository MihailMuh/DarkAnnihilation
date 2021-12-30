package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.utils.reflect.ArrayReflection;

import java.util.Iterator;

public class ArrayG<T> implements Iterable<T>, Iterator<T> {
    public T[] items;
    public int size, capacity, index;

    public ArrayG(int capacity, Class<?> cls) {
        this.capacity = capacity;
        if (capacity <= 10_000) capacity *= 2;
        items = (T[]) ArrayReflection.newInstance(cls, capacity);
    }

    public void add(T value) {
        items[size++] = value;
    }

    @Override
    public Iterator<T> iterator() {
        index = 0;
        return this;
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public T next() {
        return items[index++];
    }

    @Override
    public void remove() {
        index--;
        size--;
        System.arraycopy(items, index + 1, items, index, size - index);
        items[size] = null;
    }
}
