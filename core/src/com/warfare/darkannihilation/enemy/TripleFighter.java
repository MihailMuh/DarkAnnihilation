package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.TRIPLE_FIGHTER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.TRIPLE_FIGHTER_HEALTH;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.Shooter;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.BulletEnemy;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.PoolWrap;

public class TripleFighter extends Shooter {
    private final Player player;
    private final PoolWrap<BaseBullet> bulletsEnemy;

    private final float borderY;
    private final int right, top;

    public TripleFighter(PoolWrap<Explosion> explosionPool, PoolWrap<BaseBullet> bulletsEnemy, Player player) {
        super(explosionPool, getImages().tripleFighterImg, TRIPLE_FIGHTER_HEALTH, TRIPLE_FIGHTER_DAMAGE, 5, random(1f, 2f));
        this.player = player;
        this.bulletsEnemy = bulletsEnemy;

        name = TRIPLE;
        borderY = SCREEN_HEIGHT + height;
        right = SCREEN_WIDTH - width;
        top = SCREEN_HEIGHT - height;
        reset();
    }

    @Override
    public void kill() {
        explodeDefaultTriple();
        reset();
    }

    @Override
    public void killFromPlayer() {
        explodeSmallTriple();
        reset();
    }

    @Override
    public void reset() {
        Processor.postToLooper(() -> {
            if (shouldKill) visible = false;

            health = maxHealth;

            x = random(SCREEN_WIDTH);
            y = borderY;

            speedX = random(-4f, 4f);
            speedY = random(1.3f, 13f);
        });
    }

    @Override
    public void update() {
        x += speedX;
        y -= speedY;

        shooting();

        if (x < -width || x > SCREEN_WIDTH || y < -height) reset();
    }

    @Override
    protected void shot() {
        Processor.post(() -> calculate(centerX(), centerY()));
    }

    private void calculate(float X, float Y) {
        if (0 < x && x < right && 0 < y && y < top) {
            float rads = MathUtils.atan2(player.centerY() - Y, player.centerX() - X);
            float cos = MathUtils.cos(rads);
            float sin = MathUtils.sin(rads);

            shootTime = random(1f, 2f);
            getSounds().bigLaserSound.play();

            Gdx.app.postRunnable(() -> ((BulletEnemy) bulletsEnemy.obtain()).
                    start(X, Y, cos, sin, (rads - MathUtils.HALF_PI) * MathUtils.radDeg));
        }
    }
}
