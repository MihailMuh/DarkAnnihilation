package com.warfare.darkannihilation;

import com.warfare.darkannihilation.systemd.MainGameManager;

public class SceneTop extends SceneWrap {
    public SceneTop(MainGameManager mainGameManager) {
        super(mainGameManager);
    }

    public void stop() {
        mainGameManager.stopScene(this);
    }
}
