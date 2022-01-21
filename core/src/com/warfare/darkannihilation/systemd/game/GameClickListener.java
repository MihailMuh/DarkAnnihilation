package com.warfare.darkannihilation.systemd.game;

import static com.badlogic.gdx.Input.Keys.BACK;

import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.menu.Menu;
import com.warfare.darkannihilation.utils.ClickListener;

class GameClickListener extends ClickListener {
    private final Player player;
    private final MainGameManager manager;

    GameClickListener(Player player, MainGameManager manager) {
        this.player = player;
        this.manager = manager;
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        player.setCoordinates(x, y);
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        if (key == BACK) {
            manager.startScene(new Menu(manager), true);
        }
        return true;
    }
}
