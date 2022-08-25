package com.warfare.darkannihilation;

import com.badlogic.gdx.utils.Collections;

public final class Settings {
    public static final byte DEATH_STAR_PERIOD_IN_SECS = 100; // default: 100
    public static final short DEATH_STAR_HEALTH = 1300; //default: 1300

    public static final boolean MUTE_MUSIC = false;
    public static final boolean VIBRATE = true;

    public static final boolean SHOW_FPS = false;
    public static final boolean GOD_MODE = false;
    public static final boolean SOUNDS_IN_THREAD = true;

    public static final boolean SHOW_ASSET_MANAGER_LOGS = false;
    public static final boolean APPLY_ACCUMULATOR = false;

    static {
        Collections.allocateIterators = false;
    }
}
