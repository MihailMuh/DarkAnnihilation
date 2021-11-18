package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.Warrior;

public class Backend {
    private final MainGame game;

    private float moveAll;

    Backend(MainGame game) {
        this.game = game;
    }

    public void update() {
        moveAll = game.player.speedX / 3;

        game.screen.x -= moveAll;

        game.player.update();

        for (Warrior enemy : game.empire) {
            if (enemy.visible) {
                enemy.x -= moveAll;
                enemy.update();
            }
        }
    }
}
