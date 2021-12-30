package com.warfare.darkannihilation.systemd.game;

import static com.badlogic.gdx.Input.Keys.BACK;

import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.abstraction.BaseClickListener;
import com.warfare.darkannihilation.systemd.Intent;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.menu.Menu;

class GameClickListener extends BaseClickListener {
    private final Player player;
    private final MainGameManager manager;

    GameClickListener(Player player, MainGameManager manager) {
        super();
        this.player = player;
        this.manager = manager;
    }

    @Override
    public void touchDragged(float x, float y, int pointer) {
        player.setCoordinates(x, y);
    }

    @Override
    public void keyDown(int key) {
        if (key == BACK) {
            manager.startScene(new Intent(Menu.class), true);
        }
    }
}