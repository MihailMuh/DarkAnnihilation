package com.warfare.darkannihilation.enemy.boss;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.BOSS_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.BOSS_HEALTH_BAR_LEN;
import static com.warfare.darkannihilation.constants.Constants.BOSS_SHOOT_TIME;
import static com.warfare.darkannihilation.constants.Constants.ULTIMATE_DAMAGE;
import static com.warfare.darkannihilation.constants.Names.DEATH_STAR;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.Shooter;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Image;

public class DeathStar extends Shooter {
    private final Image red = getImages().redColor;
    private final Image white = getImages().whiteColor;

    private float whiteBarY, redBarY;
    private final float yToStart;

    private int healthBar;

    public DeathStar() {
        super(getImages().deathStarImg, BOSS_HEALTH, ULTIMATE_DAMAGE, 325, BOSS_SHOOT_TIME);
        name = DEATH_STAR;

        y = SCREEN_HEIGHT;
        x = random(SCREEN_WIDTH - width);
        speedY = 2;
        speedX = random(7, 15);

        yToStart = SCREEN_HEIGHT - height - 50;
        healthBar = BOSS_HEALTH_BAR_LEN - 3;
        whiteBarY = y + 390;
        redBarY = whiteBarY + 2;

        shrinkBorders(20, 20, 20, 20);
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);

        Processor.postToLooper(() -> {
            if (healthBar > 0) {
                healthBar = (int) ((health / (float) maxHealth) * BOSS_HEALTH_BAR_LEN) - 3;
            } else {
                healthBar = 0;
            }
        });
    }

    @Override
    public void update() {
        if (y >= yToStart) {
            y -= speedY;
            whiteBarY -= speedY;
            redBarY -= speedY;
        } else {
            x += speedX;
            if (x < -width) {
                if (randomBoolean()) {
                    speedX = -speedX;
                } else {
                    x = SCREEN_WIDTH;
                }
            } else if (x > SCREEN_WIDTH) {
                if (randomBoolean()) {
                    speedX = -speedX;
                } else {
                    x = -width;
                }
            }
            shooting();
        }
    }

    @Override
    public boolean killedByPlayer(Player player) {
        return false;
    }

    @Override
    public void kill() {
        explodeHuge();
    }

    @Override
    protected void shot() {
        float X = right() - 125;
        float Y = top() - 50;

        getPools().bulletEnemyPool.obtain(X, Y, 0.7071067812f, -0.7071067812f, 45);
        getPools().bulletEnemyPool.obtain(X, Y, 0, -1, 0);
        getPools().bulletEnemyPool.obtain(X, Y, -0.7071067812f, -0.7071067812f, 135);

        getSounds().bigLaserSound.play();
    }

    @Override
    public void render() {
        if (visible) {
            super.render();

            float centerX = centerX();
            white.draw(centerX - 70, whiteBarY, BOSS_HEALTH_BAR_LEN, 18);
            red.draw(centerX - 67, redBarY, healthBar, 14);
        }
    }
}
