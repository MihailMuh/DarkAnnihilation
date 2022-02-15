package com.warfare.darkannihilation.systemd.gameover;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.pools.ExplosionPool;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.GameTask;

import java.util.Iterator;

public class GameOver extends Scene {
    private final GameTask gameTask1 = new GameTask(this::randBoom, 50);
    private final GameTask gameTask2 = new GameTask(this::randBoom, 150);
    private final GameTask gameTask3 = new GameTask(this::killSprites, 400);

    private final ExplosionPool explosionPool = getPools().explosionPool;

    private final Array<Opponent> empire;
    private final Array<BaseBullet> bulletsEnemy;
    private final Array<Bullet> bullets;
    private final Array<Explosion> explosions;

    public GameOver(MainGameManager mainGameManager, Array<Opponent> empire, Array<Bullet> bullets,
                    Array<BaseBullet> bulletsEnemy, Array<Explosion> explosions) {
        super(mainGameManager, new GameOverClickListener(mainGameManager));

        this.empire = empire;
        this.bullets = bullets;
        this.bulletsEnemy = bulletsEnemy;
        this.explosions = explosions;
    }

    @Override
    public void resume() {
        gameTask1.start();
        gameTask2.start();
        gameTask3.start();
    }

    @Override
    public void pause() {
        gameTask1.stop();
        gameTask2.stop();
        gameTask3.stop();
    }

    @Override
    public void update() {
        for (Opponent opponent : empire) {
            if (opponent.visible) {
                opponent.update();
            }
        }

        for (BaseBullet bullet : bulletsEnemy) {
            bullet.update();
        }

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext(); ) {
            Explosion explosion = iterator.next();
            if (!explosion.visible) {
                iterator.remove();
                explosionPool.free(explosion);
            }
        }
    }

    private void boom(float x, float y) {
        explosionPool.obtain(x, y, (byte) -random(-MEDIUM_EXPLOSION_TRIPLE, -SMALL_EXPLOSION_DEFAULT));
    }

    private void randBoom() {
        for (int i = 0; i < 3; i++) {
            if (MathUtils.randomBoolean())
                boom(random(SCREEN_WIDTH), random(SCREEN_HEIGHT));
        }

        if (random(1, 35) == 1) explosionPool.obtain(random(SCREEN_WIDTH), random(SCREEN_HEIGHT), HUGE_EXPLOSION);
    }

    private void killSprites() {
        randBoom();

        if (empire.size > 0) {
            Opponent opponent = empire.pop();

            if (opponent.visible)
                explosionPool.obtain(opponent.centerX(), opponent.centerY(), MEDIUM_EXPLOSION_DEFAULT);
        }
        if (bullets.size > 0) {
            Bullet bullet = bullets.pop();
            explosionPool.obtain(bullet.centerX(), bullet.centerY(), SMALL_EXPLOSION_DEFAULT);
        }
        if (bulletsEnemy.size > 0) {
            BaseBullet bullet = bulletsEnemy.pop();
            explosionPool.obtain(bullet.centerX(), bullet.centerY(), SMALL_EXPLOSION_DEFAULT);
        }
    }

    @Override
    public void render() {
    }
}
