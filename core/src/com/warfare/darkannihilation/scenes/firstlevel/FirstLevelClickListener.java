package com.warfare.darkannihilation.scenes.firstlevel;

import static com.badlogic.gdx.Input.Keys.BACK;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;

import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.ClickListener;
import com.warfare.darkannihilation.widgets.ChangerGuns;

class FirstLevelClickListener extends ClickListener {
    private final Player player = getPlayer();
    private final ChangerGuns changerGuns;
    private final MainGameManager manager;

    private boolean tapInChangerGuns;

    FirstLevelClickListener(MainGameManager manager, ChangerGuns changerGuns) {
        this.manager = manager;
        this.changerGuns = changerGuns;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        if (!tapInChangerGuns) player.setCoordinates(x, y);
        return true;
    }

    @Override
    public boolean touchDown(float x, float y) {
        tapInChangerGuns = changerGuns.checkClick(x, y);
        return true;
    }

    @Override
    public boolean touchUp(float x, float y) {
        if (tapInChangerGuns) changerGuns.onClick(x, y);
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
