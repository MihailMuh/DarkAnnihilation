package com.warfare.darkannihilation.systemd.gameover;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.firstlevel.FirstLevel;
import com.warfare.darkannihilation.utils.ClickListener;

public class GameOverClickListener extends ClickListener {
    private final MainGameManager manager;
    private boolean start = false;

    GameOverClickListener(MainGameManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        if (Gdx.input.isTouched(1) && !start) {
            start = true;
            manager.startScene(new FirstLevel(manager), true);
        }
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        return false;
    }
}
