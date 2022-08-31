package com.warfare.darkannihilation.utils;

public class ThrowableWrap {
    private final Throwable throwable;

    public ThrowableWrap(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
