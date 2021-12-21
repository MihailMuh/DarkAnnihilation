package com.warfare.darkannihilation.systemd.game;

import static com.warfare.darkannihilation.Constants.NUMBER_DEFAULT_LARGE_EXPLOSION;
import static com.warfare.darkannihilation.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;
import static com.warfare.darkannihilation.Constants.NUMBER_VADER;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.enemy.Vader;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.screens.DynamicScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.PoolWrap;

import java.util.Iterator;

public class Game extends Scene {
    private Frontend frontend;
    private Player player;

    private final Array<Explosion> explosions = new Array<>(NUMBER_DEFAULT_LARGE_EXPLOSION);
    private final Array<Bullet> bullets = new Array<>(NUMBER_MILLENNIUM_FALCON_BULLETS);
    private final Array<Warrior> empire = new Array<>(NUMBER_VADER);

    private PoolWrap<Explosion> explosionPool;
    private PoolWrap<Bullet> bulletPool;

    public Game(MainGameManager mainGameManager) {
        super(mainGameManager);
    }

    @Override
    public void run() {
        mainGameManager.imageHub.loadGameImages();

        explosionPool = new PoolWrap<Explosion>(explosions) {
            @Override
            protected Explosion newObject() {
                return new Explosion(mainGameManager.imageHub.defaultExplosionAnim);
            }
        };
        bulletPool = new PoolWrap<Bullet>(bullets) {
            @Override
            protected Bullet newObject() {
                return new Bullet(explosionPool);
            }
        };

        player = new Player(ImageHub.millenniumFalcon, bulletPool, explosionPool);
        screen = new DynamicScreen(mainGameManager.imageHub.starScreenGIF);
        clickListener = new GameClickListener(player, mainGameManager);

        for (int i = 0; i < NUMBER_VADER; i++) {
            empire.add(new Vader(explosionPool));
        }

        frontend = new Frontend(player, screen, explosions, bullets, empire);
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
                if (player.collidedWithEnemy(enemy)) {
                    continue;
                }
                for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
                    Bullet bullet = iterator.next();
                    if (bullet.visible) {
                        if (enemy.collidedWithBullet(bullet)) {
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
    public void render() {
        frontend.render();
    }

    @Override
    public void dispose() {
        mainGameManager.imageHub.disposeGameImages();
    }
}
