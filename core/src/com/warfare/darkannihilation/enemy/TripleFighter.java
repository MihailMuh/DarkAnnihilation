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

import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.Shooter;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Image;

public class TripleFighter extends Shooter {
    private final int right, top;

    public TripleFighter() {
        this(getImages().tripleFighterImg, TRIPLE_FIGHTER_HEALTH, TRIPLE_FIGHTER_DAMAGE, 5, random(1f, 2f));

        name = TRIPLE;
        reset();
    }

    public TripleFighter(Image image, byte health, byte damage, int killScore, float shootTime) {
        super(image, health, damage, killScore, shootTime);
        right = SCREEN_WIDTH - width;
        top = SCREEN_HEIGHT - height;
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

        Processor.postToLooper(() -> {
            health = maxHealth;

            x = random(SCREEN_WIDTH);
            y = SCREEN_HEIGHT;

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
        if (0 < x && x < right && 0 < y && y < top) {
            calculate(centerX(), centerY());
        }
    }

    protected float getMiniAngle() {
        float rads = 12 * MathUtils.degreesToRadians;

        return MathUtils.random(-rads, rads);
    }

    protected void calculate(float X, float Y) {
        float rads = MathUtils.atan2(getPlayer().centerY() - Y, getPlayer().centerX() - X) + getMiniAngle();
        float cos = MathUtils.cos(rads);
        float sin = MathUtils.sin(rads);

        shootTime = random(1f, 2f);
        getSounds().bigLaserSound.play();
        getPools().bulletEnemyPool.obtain(X, Y, cos, sin, rads * MathUtils.radDeg);
    }
}
