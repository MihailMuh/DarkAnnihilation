package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
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

    private float lastDamage;

    public DifficultyAnalyzer(FirstLevel firstLevel) {
        this.firstLevel = firstLevel;
    }

    public void checkTime() {
        if (time - lastDamage > INSANE) addStatistics();
    }

    public void startCollecting() {
        lastDamage = time;
    }

    public void addStatistics() {
        float difference = time - lastDamage;
        lastDamage = time;

        if (difference <= EASY) {
            if (PLAYER_SKILL - 10 < 10) {
                if (randomBoolean(0.2f)) firstLevel.killTriple();
                firstLevel.killVader();
                firstLevel.killVader();
                PLAYER_SKILL -= 3;
            } else {
                PLAYER_SKILL -= 2;
            }
            return;
        }
        if (difference <= NOT_BAD) {
            if (PLAYER_SKILL - 10 < 20) {
                firstLevel.killVader();
                if (randomBoolean()) firstLevel.killVader();
                PLAYER_SKILL -= 2;
            } else {
                PLAYER_SKILL--;
            }
            return;
        }
        if (difference <= NORMAL) {
            if (randomBoolean()) firstLevel.killVader();
            PLAYER_SKILL--;
            return;
        }
        if (difference <= HARD) {
            if (randomBoolean()) firstLevel.newVader(1);
            PLAYER_SKILL++;
            return;
        }
        if (difference <= HURT) {
            chillSpawn();
            return;
        }
        if (difference <= INSANE) {
            if (PLAYER_SKILL >= 5) {
                normalSpawn();
            } else {
                chillSpawn();
            }
            return;
        }

        if (PLAYER_SKILL > 2) {
            chillSpawn();
            firstLevel.newVader(1);
            firstLevel.newTriple();
            PLAYER_SKILL += 4;
        } else {
            normalSpawn();
        }
    }

    private void normalSpawn() {
        if (randomBoolean()) firstLevel.newTriple();
        firstLevel.newVader(2);
        PLAYER_SKILL += 3;
    }

    private void chillSpawn() {
        firstLevel.newVader(1);
        if (randomBoolean()) firstLevel.newVader(1);
        PLAYER_SKILL += 2;
    }
}
