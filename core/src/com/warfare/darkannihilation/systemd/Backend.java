package com.warfare.darkannihilation.systemd;

public class Backend {
    private final DarkGame game;

    Backend(DarkGame game) {
        this.game = game;
    }

    public void update() {
        game.player.update();
    }
}
