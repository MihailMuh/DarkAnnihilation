package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.TRIPLE_FIGHTER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.TRIPLE_FIGHTER_HEALTH;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.Shooter;

public class TripleFighter extends Shooter {
    private final float right, top;

    public TripleFighter() {
        this(getImages().tripleFighterImg, TRIPLE_FIGHTER_HEALTH, TRIPLE_FIGHTER_DAMAGE, 5, random(1f, 2f));

        name = TRIPLE;
        reset();
    }

    public TripleFighter(AtlasRegion region, byte health, byte damage, int killScore, float shootTime) {
        super(region, health, damage, killScore, shootTime);
        right = SCREEN_WIDTH - getWidth();
        top = SCREEN_HEIGHT - getHeight();
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
        visible = !shouldKill;
        health = maxHealth;

        setPosition(random(SCREEN_WIDTH), SCREEN_HEIGHT);

        speedX = random(-4f, 4f);
        speedY = -random(1.3f, 13f);
    }

    @Override
    public void update() {
        translate(speedX, speedY);
    }

    @Override
    protected void shot() {
        if (0 < getX() && getX() < right && 0 < getY() && getY() < top) {
            calculate(centerX(), centerY());
        }
    }

    protected float getMiniAngle() {
        float rads = 12 * MathUtils.degreesToRadians;

        return MathUtils.random(-rads, rads);
    }

    protected void calculate(float x, float y) {
        float rads = MathUtils.atan2(getPlayer().centerY() - y, getPlayer().centerX() - x) + getMiniAngle();
        float cos = MathUtils.cos(rads);
        float sin = MathUtils.sin(rads);

        shootTime = random(1f, 2f);
        getSounds().bigLaserSound.play();
        getPools().bulletEnemyPool.obtain(x, y, cos, sin, rads * MathUtils.radiansToDegrees);
    }

    @Override
    public void updateInThread() {
        shooting();

        if (getX() < -getWidth() || getX() > SCREEN_WIDTH || getY() < -getHeight()) reset();
    }
}
