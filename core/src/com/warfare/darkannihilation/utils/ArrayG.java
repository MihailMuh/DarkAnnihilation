package com.warfare.darkannihilation.utils;

import java.util.Iterator;

public class ArrayG<T> implements Iterable<T>, Iterator<T> {
    private int size, index;

    public T[] items;
    public int capacity;

    public ArrayG(int capacity) {
        this.capacity = capacity;
        if (capacity <= 10_000) capacity *= 2;
        items = (T[]) new Object[capacity];
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