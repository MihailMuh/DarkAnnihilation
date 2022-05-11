package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_HEALTH;
import static com.warfare.darkannihilation.systemd.service.Service.print;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.warfare.darkannihilation.systemd.firstlevel.FirstLevel;

public class DifficultyAnalyzer {
    public static final float EASY = 1.5f;
    private static final float NOT_BAD = EASY * 2.4f;
    private static final float NORMAL = EASY * 3.4f;
    private static final float HARD = EASY * 5.4f;
    private static final float HURT = EASY * 7f;
    private static final float INSANE = EASY * 10;

    private int PLAYER_SKILL = 10;

    private final FirstLevel firstLevel;

    private volatile boolean bossTime;
    private float lastDamage;
    private int health;

    public DifficultyAnalyzer(FirstLevel firstLevel) {
        this.firstLevel = firstLevel;
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
            if (randomBoolean() || health < MILLENNIUM_FALCON_HEALTH - 20) firstLevel.killVader();
            PLAYER_SKILL--;
            print("normal", PLAYER_SKILL);
            return;
        }
        if (difference <= HARD) {
            if (randomBoolean() || health > MILLENNIUM_FALCON_HEALTH) firstLevel.newVader(1);
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
        if (reduceEffectIfFactory(0.2f)) {
            if (randomBoolean(0.75f)) {
                firstLevel.killTriple();
            }
            firstLevel.killVader();
            firstLevel.killVader();
            PLAYER_SKILL -= 3;
            print("easy", PLAYER_SKILL);
        }
    }

    private void notBad() {
        if (reduceEffectIfFactory(0.35f)) {
            if (PLAYER_SKILL - 10 < 20) {
                firstLevel.killVader();
                if (randomBoolean() || health < MILLENNIUM_FALCON_HEALTH - 30)
                    firstLevel.killVader();
            }
            PLAYER_SKILL -= 2;
            print("not bad", PLAYER_SKILL);
        }
    }

    private void awesome() {
        if (PLAYER_SKILL > 2) {
            chillSpawn();
            firstLevel.newVader(1);
            firstLevel.newTriple();
        } else {
            normalSpawn();
        }
        PLAYER_SKILL += 5;
        print("WOOOOW", PLAYER_SKILL);
    }

    private boolean reduceEffectIfFactory(float chance) {
        return firstLevel.factory.visible && randomBoolean(chance);
    }

    private void normalSpawn() {
        if (randomBoolean() || health > MILLENNIUM_FALCON_HEALTH + 40) firstLevel.newTriple();
        firstLevel.newVader(2);
    }

    private void chillSpawn() {
        firstLevel.newVader(1);
        if (randomBoolean() || health > MILLENNIUM_FALCON_HEALTH + 20) firstLevel.newVader(1);
    }

    private void checkCritical() {
        if (health < MILLENNIUM_FALCON_HEALTH - 30) {
            firstLevel.killTriple();
            firstLevel.killVader();
            firstLevel.killVader();
        }
    }
}
