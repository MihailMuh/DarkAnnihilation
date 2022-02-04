package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.TRIPLE_FIGHTER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.TRIPLE_FIGHTER_HEALTH;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.Shooter;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.BulletEnemy;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.PoolWrap;
import com.warfare.darkannihilation.utils.audio.SoundWrap;

public class TripleFighter extends Shooter {
    private final Player player;
    private final SoundWrap sound;
    private final PoolWrap<BaseBullet> bulletsEnemy;

    private final float borderY;
    private static final int VECTOR_LEN = 13 * 77;

    public TripleFighter(PoolWrap<Explosion> explosionPool, PoolWrap<BaseBullet> bulletsEnemy, TextureAtlas.AtlasRegion texture, SoundWrap sound, Player player) {
        super(explosionPool, texture, TRIPLE_FIGHTER_HEALTH, TRIPLE_FIGHTER_DAMAGE, 5, random(1f, 2f));
        this.player = player;
        this.bulletsEnemy = bulletsEnemy;
        this.sound = sound;

        borderY = SCREEN_HEIGHT + height;
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
        health = maxHealth;

        x = random(SCREEN_WIDTH);
        y = borderY;

        speedX = random(-231f, 231f);
        speedY = random(77f, 770f);
    }

    @Override
    public void update() {
        x += speedX * delta;
        y -= speedY * delta;

        shooting();

        if (x < -width || x > SCREEN_WIDTH || y < -height) reset();
    }

    @Override
    protected void shot() {
        Processor.post(() -> calculate(centerX(), centerY()));
    }

    private void calculate(float X, float Y) {
        float rads = MathUtils.atan2(player.centerY() - Y, player.centerX() - X);

        ((BulletEnemy) bulletsEnemy.obtain()).start(X, Y,
                VECTOR_LEN * MathUtils.cos(rads), VECTOR_LEN * MathUtils.sin(rads),
                (rads - MathUtils.HALF_PI) * MathUtils.radDeg);

        shootTime = random(1f, 2f);
        sound.play();
    }
}
