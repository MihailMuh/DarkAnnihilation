package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Service.printErr;

import com.warfare.darkannihilation.abstraction.Scene;

import java.util.HashMap;

public class Intent {
    private HashMap<String, Object> map;
    private Scene scene;
    public MainGameManager gameManager;

    public Intent(MainGameManager mainGameManager, Class<?> cls) {
        this(mainGameManager, cls, new Parameters());
    }

    public Intent(MainGameManager mainGameManager, Class<?> cls, Parameters parameters) {
        try {
            gameManager = mainGameManager;
            map = parameters.map;

            scene = (Scene) cls.getConstructor().newInstance();
            scene.bootAssets(this);
        } catch (Exception e) {
            printErr("Error in intent", e);
        }
    }

    Scene getScene() {
        scene.create();
        return scene;
    }

    public Object get(String key) {
        return map.get(key);
    }
}
