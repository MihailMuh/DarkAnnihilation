package com.warfare.darkannihilation.enemy.deathstar;

import static com.badlogic.gdx.math.MathUtils.atan2;
import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.badlogic.gdx.math.MathUtils.sin;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static com.warfare.darkannihilation.Settings.DEATH_STAR_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_HEALTH_BAR_LEN;
import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_SECOND_PHASE_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_SECOND_SHOOT_TIME_FOR_FIRST_PHASE_IN_SECS;
import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_SHOOT_TIME_FOR_FIRST_PHASE_IN_SECS;
import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_SHOOT_TIME_FOR_SECOND_PHASE_IN_SECS;
import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_SPEED_FOR_THIRD_PHASE;
import static com.warfare.darkannihilation.constants.Constants.DEATH_STAR_THIRD_PHASE_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.ULTIMATE_DAMAGE;
import static com.warfare.darkannihilation.constants.Names.DEATH_STAR;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Processor.postTask;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.Shooter;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.systemd.EnemyController;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.utils.HealthBar;

public class DeathStar extends Shooter {
    private final Shield shield = new Shield();
    private final EnemyController enemyController;
    private final Player player = getPlayer();
    private final HealthBar healthBar;

    private final float yToShootForFirstPhase, yToStopForSecondPhase;
    private final float yToStopForThirdPhase;

    private boolean drawShield;
    private float lastSecondShot;
    private float centerX;

    private byte phase = 0;

    public DeathStar(EnemyController enemyController) {
        super(getImages().deathStarImages[0], DEATH_STAR_HEALTH, ULTIMATE_DAMAGE, 325, DEATH_STAR_SHOOT_TIME_FOR_FIRST_PHASE_IN_SECS);
        this.enemyController = enemyController;

        name = DEATH_STAR;

        x = random(SCREEN_WIDTH - width);
        y = SCREEN_HEIGHT;
        yToStopForSecondPhase = SCREEN_HEIGHT - height - 100;
        yToShootForFirstPhase = SCREEN_HEIGHT - halfHeight - 50;
        yToStopForThirdPhase = SCREEN_HEIGHT - height - 40;

        speedX = random(0.002f, 0.005f);
        speedY = random(1, 3);

        healthBar = new HealthBar(DEATH_STAR_HEALTH_BAR_LEN, maxHealth, 20, 16);

        shrinkBorders(20, 20, 20, 20);
    }

    @Override
    public void damage(int damage) {
        if (phase == 2) damage *= 2;
        super.damage(damage);

        Processor.postToLooper(() -> {
            healthBar.updateHealthBar(health);

            checkHealthForPhases();
        });
    }

    private void checkHealthForPhases() {
        if (health > DEATH_STAR_SECOND_PHASE_HEALTH) return;

        // Не объединяем два условия в одно, т.к. это условие базируется на предыдущем
        if ((health > DEATH_STAR_THIRD_PHASE_HEALTH && phase == 1) || (health <= DEATH_STAR_THIRD_PHASE_HEALTH && phase == 2)) {
            return;
        }

        explodeHuge();
        image = getImages().deathStarImages[++phase];
        newParamsForPhase();
    }

    private void newParamsForPhase() {
        if (phase == 1) {
            speedX = random(7, 15);
            shootTime = DEATH_STAR_SHOOT_TIME_FOR_SECOND_PHASE_IN_SECS;
        } else {
            speedY = DEATH_STAR_SPEED_FOR_THIRD_PHASE;

            enemyController.addEnemy(shield);
            postTask(() -> {
                Service.sleep(500);
                enemyController.newTriple(10);
            });
        }
    }

    private void newDirection(float startX) {
        if (randomBoolean()) {
            speedX = -speedX;
        } else {
            x = startX;
        }
    }

    private void updateThirdPhase() {
        if (y < yToStopForThirdPhase) {
            y += speedY;
        } else {
            if (!drawShield) {
                shield.start(centerX, y - halfHeight);
                drawShield = true;
            }
        }
    }

    private void updateSecondPhase() {
        if (y >= yToStopForSecondPhase) {
            y -= speedY;
        } else {
            x += speedX;

            if (x < -width) {
                newDirection(SCREEN_WIDTH);
            } else if (x > SCREEN_WIDTH) {
                newDirection(-width);
            }
            shooting();
        }
    }

    private void shootSecondPhase() {
        float X = right() - 130;
        float Y = top() - 100;

        for (float angle = -15; angle > -180; angle -= 15) {
            getPools().bulletEnemyPool.obtain(X, Y, cosDeg(angle), sinDeg(angle), angle); // For example: cos(-22.5), sin(-22.5), 90 + (-22.5)
        }
    }

    private void updateFirstPhase() {
        if (y <= yToShootForFirstPhase) {
            shooting();
            if (time - lastSecondShot >= DEATH_STAR_SECOND_SHOOT_TIME_FOR_FIRST_PHASE_IN_SECS) {
                lastSecondShot = time;

                Processor.postToLooper(this::secondShootOfFirstPhase);
            }
        }

        if (y >= yToStopForSecondPhase) {
            y -= speedY;
        }
        x += speedX * (player.x - centerX); // player.x faster than player.centerX(). Accuracy doesn't live matter
    }

    private void shootFirstPhase() {
        float Y = top() - 215;
        float endX = right() - 30;

        for (float X = x + 100; X < endX; X += 30) {
            getPools().bulletEnemyPool.obtain(X, Y, 0, -1, 90); // Летят прямо вниз
            Y += 7;
        }
    }

    private void secondShootOfFirstPhase() {
        float X = right() - 75;
        float Y = top() - 135;
        float deltaY = player.centerY() - Y;
        float deltaX = player.centerX() - X;
        float rads = atan2(deltaY, deltaX);
        float cos = cos(rads);
        float sin = sin(rads);
        double distance = Math.sqrt(deltaY * deltaY + deltaX * deltaX);

        if (distance > HALF_SCREEN_WIDTH) {
            cos *= 1.5;
            sin *= 1.5;
        }

        getPools().sunriseBulletPool.obtain(X, Y, cos, sin);
    }

    @Override
    public void update() {
        centerX = centerX();

        switch (phase) {
            case 0:
                updateFirstPhase();
                break;
            case 1:
                updateSecondPhase();
                break;
            case 2:
                updateThirdPhase();
        }
        healthBar.setOutlineBarCoords(centerX, top());
    }

    @Override
    public boolean killedByPlayer(Player player) {
        return false;
    }

    @Override
    public void kill() {
        explodeHuge();
        postTask(() -> {
            getSounds().firstLevelMusic.play();
            getSounds().disposeDeathStarMusic();

            healthBar.hide();
            if (shield.visible) {
                enemyController.removeEnemy(shield);
                shield.kill();
            }
        });
    }

    @Override
    protected void shot() {
        switch (phase) {
            case 0:
                shootFirstPhase();
                break;
            case 1:
                shootSecondPhase();
                break;
            case 2:

        }
        getSounds().bigLaserSound.play();
    }

    @Override
    public void render() {
        super.render();
        healthBar.render();
    }
}
