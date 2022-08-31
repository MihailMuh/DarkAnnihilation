package com.warfare.darkannihilation.scenes.gameover;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.scenes.firstlevel.FirstLevel;
import com.warfare.darkannihilation.utils.ClickListener;

class GameOverClickListener extends ClickListener {
    private final MainGameManager manager;
    private boolean start = false;

    GameOverClickListener(MainGameManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        if (Gdx.input.isTouched(1) && !start) {
            start = true;
            manager.startScene(true, FirstLevel.class, manager);
        }
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        return false;
    }
}
