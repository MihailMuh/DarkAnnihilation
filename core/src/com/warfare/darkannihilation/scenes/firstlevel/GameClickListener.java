package com.warfare.darkannihilation.scenes.firstlevel;

import static com.badlogic.gdx.Input.Keys.BACK;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;

import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.utils.ClickListener;

class GameClickListener extends ClickListener {
    private final Player player = getPlayer();
    private final MainGameManager manager;

    GameClickListener(MainGameManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean touchDragged(float x, float y) {
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
