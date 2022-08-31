package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.warfare.darkannihilation.abstraction.Scene;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Intent {
    private final Object[] params;
    private Constructor<?> constructorOfSceneToStart;

    public Intent(Class<?> classOfSceneToStart, Object... params) {
        this.params = params;

        Class<?>[] classParams = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classParams[i] = params[i].getClass();
        }

        try {
            constructorOfSceneToStart = classOfSceneToStart.getConstructor(classParams);
            constructorOfSceneToStart.setAccessible(true);
        } catch (NoSuchMethodException exception) {
            print("Exception in Intent()");
            exception.printStackTrace();
        }
    }

    public Scene getScene() {
        try {
            return (Scene) constructorOfSceneToStart.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            print("Exception in Intent::getScene()");
            exception.printStackTrace();
            return null;
        }
    }
}
