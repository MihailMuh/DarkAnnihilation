package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.warfare.darkannihilation.abstraction.Scene;

import java.util.HashMap;

public class Intent {
    private final HashMap<String, Object> map = new HashMap<>(3);
    private Scene scene;
    public MainGameManager gameManager;

    public Intent(Class<?> cls) {
        try {
            scene = (Scene) cls.getConstructor().newInstance();
        } catch (Exception e) {
            print("Error in intent", e);
        }
    }

    public Scene boot(MainGameManager mainGameManager) {
        gameManager = mainGameManager;
        scene.bootAssets(this);
        return scene;
    }

    public Intent put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Object get(String key) {
        return map.get(key);
    }
}
