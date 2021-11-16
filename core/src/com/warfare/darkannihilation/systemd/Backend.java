package com.warfare.darkannihilation.systemd;

import com.warfare.darkannihilation.abstraction.BaseEnemy;

public class Backend {
    private final MainGame game;

    private float moveAll;

    Backend(MainGame game) {
        this.game = game;
    }

    public void update() {
        moveAll = game.player.boostX / 3;

        game.screen.x -= moveAll;

        for (BaseEnemy enemy : game.empire) {
            if (enemy.visible) {
                enemy.x -= moveAll;
                enemy.update();
            }
        }

        game.player.update();
    }
}
