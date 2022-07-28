package com.warfare.darkannihilation;

import com.badlogic.gdx.utils.Collections;

public final class Settings {
    private static final byte BOSS_PERIOD_IN_SECS = 120; // default: 120
    public static final short BOSS_HEALTH = 1300; //default: 1300

    private static final boolean MUTE_MUSIC = false;
    public static final boolean VIBRATE = true;

    public static final boolean SHOW_FPS = false;
    public static final boolean GOD_MODE = false;
    public static final boolean SOUNDS_IN_THREAD = true;

    public static final boolean SHOW_ASSET_MANAGER_LOGS = false;
    public static final boolean APPLY_ACCUMULATOR = false;

    static {
        Collections.allocateIterators = false;
    }

    public static boolean isMuteMusic() {
        return MUTE_MUSIC;
    }

    public static byte getBossPeriodInSecs() {
        return BOSS_PERIOD_IN_SECS;
    }

    public static short getBossHealth() {
        return BOSS_HEALTH;
    }
}
