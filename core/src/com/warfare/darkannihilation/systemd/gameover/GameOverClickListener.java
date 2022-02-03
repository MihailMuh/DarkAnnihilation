package com.warfare.darkannihilation.systemd.gameover;

import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.game.Game;
import com.warfare.darkannihilation.utils.ClickListener;

public class GameOverClickListener extends ClickListener {
    private final MainGameManager manager;

    GameOverClickListener(MainGameManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        if (pointer >= 3) manager.startScene(new Game(manager), true);
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        return false;
    }
}
