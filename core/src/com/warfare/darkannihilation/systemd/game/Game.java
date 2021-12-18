package com.warfare.darkannihilation.systemd.game;

import static com.warfare.darkannihilation.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.hub.PoolHub.bulletPool;
import static com.warfare.darkannihilation.hub.PoolHub.explosionPool;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.enemy.Vader;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.hub.PoolHub;
import com.warfare.darkannihilation.screens.DynamicScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;

import java.util.Iterator;

public class Game extends Scene {
    private Player player;

    private final Array<Explosion> explosions = new Array<>(10);
    public Array<Bullet> bullets = new Array<>(15);
    public Array<Warrior> empire = new Array<>(NUMBER_VADER);

    public Game(MainGameManager mainGameManager) {
        super(mainGameManager);
    }

    @Override
    public void run() {
        mainGameManager.imageHub.loadGameImages();

        player = new Player(ImageHub.millenniumFalcon);
        screen = new DynamicScreen(mainGameManager.imageHub.starScreenGIF);
        clickListener = new GameClickListener(player, mainGameManager);
        for (int i = 0; i < NUMBER_VADER; i++) {
            empire.add(new Vader());
        }

        PoolHub.init(explosions, bullets);
    }

    @Override
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

    @Override
    public void update() {
        float moveAll = player.speedX / 3;

        screen.x -= moveAll;

        player.update();

        for (Warrior enemy : empire) {
            if (enemy.visible) {
                enemy.x -= moveAll;
                enemy.update();
                if (player.collidesWithEnemy(enemy)) {
                    continue;
                }
                for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
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

        for (Bullet bullet : bullets) {
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

    @Override
    public void dispose() {
        mainGameManager.imageHub.disposeGameImages();
    }
}
