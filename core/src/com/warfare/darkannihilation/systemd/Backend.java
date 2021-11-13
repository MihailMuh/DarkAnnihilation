package com.warfare.darkannihilation.systemd;

public class Backend {
    private final MainGame game;

    private float moveAll;

    Backend(MainGame game) {
        this.game = game;
    }

    public void update() {
        moveAll = game.player.boostX / 3;

        game.screen.x -= moveAll;
        game.player.update();
    }
}
