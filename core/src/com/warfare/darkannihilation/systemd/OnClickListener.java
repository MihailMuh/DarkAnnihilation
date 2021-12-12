package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.Player;

public class OnClickListener extends BaseClickListener {
    private final Player player;

    public OnClickListener(Player player) {
        super();
        this.player = player;
    }

    @Override
    public void touchDragged(float x, float y, int pointer) {
        player.setCoordinates(x, y);
    }
}
