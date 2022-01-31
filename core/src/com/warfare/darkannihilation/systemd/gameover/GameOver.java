package com.warfare.darkannihilation.systemd.gameover;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.PoolWrap;

public class GameOver extends Scene {
    private final Array<Opponent> empire;
    private final Array<BaseBullet> bulletsEnemy;
    private final Array<Bullet> bullets;
    private final PoolWrap<Explosion> explosionPool;

    private final Runnable update;

    private float lastEmpire, lastBullet, lastEnemyBullet;

    public GameOver(MainGameManager mainGameManager, Runnable update, Array<Opponent> empire,
                    Array<Bullet> bullets, Array<BaseBullet> bulletsEnemy, PoolWrap<Explosion> explosionPool) {
        super(mainGameManager);

        this.empire = empire;
        this.bullets = bullets;
        this.bulletsEnemy = bulletsEnemy;
        this.explosionPool = explosionPool;
        this.update = update;
    }

    @Override
    public void update() {
        update.run();

        if (time - lastEmpire >= 0.2) {
            lastEmpire = time;

            randBoom();

            if (empire.size > 0) {
                Opponent opponent = empire.pop();

                if (opponent.visible) boom(opponent.centerX(), opponent.centerY());
            }
        }

        if (time - lastBullet >= 0.05) {
            lastBullet = time;

            randBoom();

            if (bullets.size > 0) {
                Bullet bullet = bullets.pop();
                boom(bullet.centerX(), bullet.centerY());
            }
        }

        if (time - lastEnemyBullet >= 0.1) {
            lastEnemyBullet = time;

            randBoom();

            if (bulletsEnemy.size > 0) {
                BaseBullet bullet = bulletsEnemy.pop();
                boom(bullet.centerX(), bullet.centerY());
            }
        }
    }

    private void boom(float x, float y) {
        boom(x, y, (byte) -random(-MEDIUM_EXPLOSION_TRIPLE, -SMALL_EXPLOSION_DEFAULT));
    }

    private void boom(float x, float y, byte type) {
        explosionPool.obtain().start(x, y, type);
    }

    private void randBoom() {
        for (int i = 0; i < 3; i++) {
            if (MathUtils.randomBoolean())
                boom(random(SCREEN_WIDTH), random(SCREEN_HEIGHT));
        }

        if (random(1, 35) == 1) boom(random(SCREEN_WIDTH), random(SCREEN_HEIGHT), HUGE_EXPLOSION);
    }

    @Override
    public void render() {

    }
}
