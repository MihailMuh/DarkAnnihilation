package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_HEALTH;
import static com.warfare.darkannihilation.systemd.service.Service.print;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.warfare.darkannihilation.abstraction.sprite.Opponent;

public class DifficultyAnalyzer {
    public static final float EASY = 1.5f;
    private static final float NOT_BAD = EASY * 2.4f;
    private static final float NORMAL = EASY * 3.4f;
    private static final float HARD = EASY * 5.4f;
    private static final float HURT = EASY * 7f;
    private static final float INSANE = EASY * 10;

    private int PLAYER_SKILL = 10;

    private final EnemyController enemyController;
    private final Opponent powerfulEnemy;

    private volatile boolean bossTime;
    private float lastDamage;
    private int health;

    public DifficultyAnalyzer(EnemyController enemyController, Opponent powerfulEnemy) {
        this.enemyController = enemyController;
        this.powerfulEnemy = powerfulEnemy;
    }

    public void isBossTime(boolean bossTime) {
        this.bossTime = bossTime;
    }

    public void checkTime() {
        if (!bossTime && time - lastDamage > INSANE) {
            lastDamage = time;
            awesome();
        }
    }

    public void startCollecting() {
        lastDamage = time;
    }

    public void addStatistics(int health) {
        if (bossTime) return;

        float difference = time - lastDamage;
        lastDamage = time;
        this.health = health;

        checkCritical();
        if (difference <= EASY) {
            if (PLAYER_SKILL - 10 < 15 && health <= MILLENNIUM_FALCON_HEALTH * 1.5) {
                easy();
                return;
            }
        }
        if (difference <= NOT_BAD) {
            notBad();
            return;
        }
        if (difference <= NORMAL) {
            if (randomBoolean() || health < MILLENNIUM_FALCON_HEALTH - 20) enemyController.killVader();
            PLAYER_SKILL--;
            print("normal", PLAYER_SKILL);
            return;
        }
        if (difference <= HARD) {
            if (randomBoolean() || health > MILLENNIUM_FALCON_HEALTH) enemyController.newVader(1);
            PLAYER_SKILL++;
            print("hard", PLAYER_SKILL);
            return;
        }
        if (difference <= HURT) {
            chillSpawn();
            PLAYER_SKILL += 2;
            print("hurt", PLAYER_SKILL);
            return;
        }
        if (difference <= INSANE) {
            if (PLAYER_SKILL >= 5) {
                normalSpawn();
            } else {
                chillSpawn();
            }
            PLAYER_SKILL += 4;
            print("insane", PLAYER_SKILL);
            return;
        }

        awesome();
    }

    private void easy() {
        if (reduceEffectIfPowerfulEnemy(0.2f)) {
            if (randomBoolean(0.75f)) {
                enemyController.killTriple();
            }
            enemyController.killVader();
            enemyController.killVader();
            PLAYER_SKILL -= 3;
            print("easy", PLAYER_SKILL);
        }
    }

    private void notBad() {
        if (reduceEffectIfPowerfulEnemy(0.35f)) {
            if (PLAYER_SKILL - 10 < 20) {
                enemyController.killVader();
                if (randomBoolean() || health < MILLENNIUM_FALCON_HEALTH - 30)
                    enemyController.killVader();
            }
            PLAYER_SKILL -= 2;
            print("not bad", PLAYER_SKILL);
        }
    }

    private void awesome() {
        if (PLAYER_SKILL > 2) {
            chillSpawn();
            enemyController.newVader(1);
            enemyController.newTriple();
        } else {
            normalSpawn();
        }
        PLAYER_SKILL += 5;
        print("WOOOOW", PLAYER_SKILL);
    }

    private boolean reduceEffectIfPowerfulEnemy(float chance) {
        return powerfulEnemy.visible && randomBoolean(chance);
    }

    private void normalSpawn() {
        if (randomBoolean() || health > MILLENNIUM_FALCON_HEALTH + 40) enemyController.newTriple();
        enemyController.newVader(2);
    }

    private void chillSpawn() {
        enemyController.newVader(1);
        if (randomBoolean() || health > MILLENNIUM_FALCON_HEALTH + 20) enemyController.newVader(1);
    }

    private void checkCritical() {
        if (health < MILLENNIUM_FALCON_HEALTH - 30) {
            enemyController.killTriple();
            enemyController.killVader();
            enemyController.killVader();
        }
    }
}
