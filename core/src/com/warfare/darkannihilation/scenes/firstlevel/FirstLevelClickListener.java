package com.warfare.darkannihilation.scenes.firstlevel;

import static com.badlogic.gdx.Input.Keys.BACK;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;

import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.ClickListener;
import com.warfare.darkannihilation.widgets.ChangerGuns;
import com.warfare.darkannihilation.widgets.PauseButton;

class FirstLevelClickListener extends ClickListener {
    private final Player player = getPlayer();
    private final ChangerGuns changerGuns;
    private final PauseButton pauseButton;
    private final MainGameManager mainGameManager;

    private boolean tapInChangerGuns, tapInPauseButton;
    public boolean allowPlayerMove;

    FirstLevelClickListener(MainGameManager mainGameManager, ChangerGuns changerGuns, PauseButton pauseButton) {
        this.mainGameManager = mainGameManager;
        this.changerGuns = changerGuns;
        this.pauseButton = pauseButton;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        if (allowPlayerMove) player.setCoordinates(x, y);
        return true;
    }

    @Override
    public boolean touchDown(float x, float y) {
        tapInChangerGuns = changerGuns.checkClick(x, y);
        tapInPauseButton = pauseButton.checkClick(x, y);

        allowPlayerMove = !tapInChangerGuns && !tapInPauseButton;
        return true;
    }

    @Override
    public boolean touchUp(float x, float y) {
        if (tapInChangerGuns) changerGuns.onClick(x, y);
        else if (tapInPauseButton) {
            pauseButton.onClick(x, y);
        }
        return true;
    }

    @Override
    public boolean keyDown(int key) {
        if (key == BACK) {
            mainGameManager.startScene(true, Menu.class, mainGameManager);
        }
        return true;
    }
}
