package com.warfare.darkannihilation.systemd.game;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.abstraction.BaseScreen;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;

class Frontend {
    private final Player player;
    private final BaseScreen screen;

    private final Array<Explosion> explosions;
    private final Array<Bullet> bullets;
    private final Array<Warrior> empire;

    Frontend(Player player, BaseScreen screen, Array<Explosion> explosions, Array<Bullet> bullets, Array<Warrior> empire) {
        this.player = player;
        this.screen = screen;
        this.explosions = explosions;
        this.bullets = bullets;
        this.empire = empire;
    }

    public void render() {
        screen.render();
        player.render();

        for (Warrior enemy : empire) {
            enemy.render();
        }
        for (Bullet bullet : bullets) {
            bullet.render();
        }
        for (Explosion explosion : explosions) {
            explosion.render();
        }
    }
}
