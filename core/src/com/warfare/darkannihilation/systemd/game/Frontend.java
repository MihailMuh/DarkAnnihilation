package com.warfare.darkannihilation.systemd.game;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.abstraction.BaseScreen;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.utils.ArrayG;

class Frontend {
    private final Player player;
    private final BaseScreen screen;

    private final ArrayG<Explosion> explosions;
    private final ArrayG<Bullet> bullets;
    private final ArrayG<Warrior> empire;
    private final ArrayG<BaseBullet> bulletsEnemy;

    Frontend(Player player, BaseScreen screen, ArrayG<Explosion> explosions, ArrayG<Bullet> bullets, ArrayG<Warrior> empire, ArrayG<BaseBullet> bulletsEnemy) {
        this.player = player;
        this.screen = screen;
        this.explosions = explosions;
        this.bullets = bullets;
        this.empire = empire;
        this.bulletsEnemy = bulletsEnemy;
    }

    public void render() {
        screen.render();
        player.render();

        for (Warrior enemy : empire) {
            enemy.render();
        }
        for (BaseBullet baseBullet : bulletsEnemy) {
            baseBullet.render();
        }
        for (Bullet bullet : bullets) {
            bullet.render();
        }
        for (Explosion explosion : explosions) {
            explosion.render();
        }
    }
}
