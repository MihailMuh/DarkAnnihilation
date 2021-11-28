package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.MainGame.explosions;
import static com.warfare.darkannihilation.hub.PoolHub.bulletPool;
import static com.warfare.darkannihilation.hub.PoolHub.explosionPool;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;

import java.util.Iterator;

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
                if (game.player.collidesWithEnemy(enemy)) {
                    continue;
                }
                for (Iterator<Bullet> iterator = game.bullets.iterator(); iterator.hasNext(); ) {
                    Bullet bullet = iterator.next();
                    if (bullet.visible) {
//                        damage enemy and check if it dies
                        if (enemy.collidesWithBullet(bullet)) {
                            break;
                        }
                    } else {
                        iterator.remove();
                        bulletPool.free(bullet);
                    }
                }
            }
        }

        for (Bullet bullet : game.bullets) {
            bullet.x -= moveAll;
            bullet.update();
        }

        for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext(); ) {
            Explosion explosion = iterator.next();
            if (explosion.visible) {
                explosion.x -= moveAll;
            } else {
                iterator.remove();
                explosionPool.free(explosion);
            }
        }
    }
}
