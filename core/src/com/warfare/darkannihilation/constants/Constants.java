package com.warfare.darkannihilation.constants;

import static com.warfare.darkannihilation.Settings.DEATH_STAR_HEALTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.Settings;

public final class Constants {
    // characters
    public static final byte MILLENNIUM_FALCON_HEALTH = 50;
    public static final float MILLENNIUM_FALCON_SHOOT_TIME = 0.065f;
    public static final float MILLENNIUM_FALCON_SHOTGUN_TIME = 0.55f;
    public static final byte NUMBER_MILLENNIUM_FALCON_BULLETS = 30;
    public static final short NUMBER_MILLENNIUM_FALCON_BUCKSHOT = 30;

    public static final short EMERALD_SHOOT_TIME = 1_300;
    public static final short EMERALD_SHOTGUN_TIME = 1_200;
    public static final byte EMERALD_HEALTH = 100;

    public static final byte SATURN_SHOOT_TIME = 105;
    public static final byte SATURN_SHOTGUN_TIME = 115;
    public static final byte SATURN_HEALTH = 20;
    public static final byte NUMBER_SATURN_BULLETS = 60;
    public static final short NUMBER_SATURN_BUCKSHOT = NUMBER_SATURN_BULLETS + 400;
    public static final short NUMBER_SATURN_ENEMY_ORBIT = NUMBER_SATURN_BUCKSHOT + 50;

    public static final byte BOT_SHOOT_TIME = 70;

    // second level
    public static final byte ATOMIC_BOMB_FRAME_TIME = 30;
    public static final byte NUMBER_ATOMIC_BOMB_IMAGES = 4;

    public static final short BOSS_VADERS_HEALTH = 350;
    public static final short BOSS_VADERS_SHOOT_TIME = 1_000;

    public static final byte BUFFER_HEALTH = 35;
    public static final byte BUFFER_DAMAGE = 20;

    public static final short XWING_SHOOT_TIME = 175;
    public static final byte XWING_HEALTH = 5;
    public static final byte XWING_DAMAGE = 10;

    public static final short SUNRISE_SHOOT_TIME = 775;
    public static final byte SUNRISE_HEALTH = 45;
    public static final byte SUNRISE_DAMAGE = 20;

    public static final short SPIDER_HEALTH = 200;
    public static final byte SPIDER_SHOOT_TIME = 110;
    public static final byte SPIDER_DAMAGE = 20;
    public static final byte SPIDER_SPEED = 5;
    public static final short SPIDER_HEALTH_BAR_LEN = 150;

    // first level
    public static final float DEATH_STAR_SHOOT_TIME_FOR_FIRST_PHASE_IN_SECS = 0.8f;
    public static final float DEATH_STAR_SECOND_SHOOT_TIME_FOR_FIRST_PHASE_IN_SECS = 3.5f;
    public static final float DEATH_STAR_SHOOT_TIME_FOR_SECOND_PHASE_IN_SECS = 0.8f;
    public static final float DEATH_STAR_SHOOT_TIME_FOR_THIRD_PHASE_IN_SECS = 3;
    public static final int DEATH_STAR_THIRD_PHASE_HEALTH = DEATH_STAR_HEALTH / 3;
    public static final int DEATH_STAR_SECOND_PHASE_HEALTH = DEATH_STAR_THIRD_PHASE_HEALTH * 2;
    public static final byte DEATH_STAR_SPEED_FOR_THIRD_PHASE = 4;
    public static final short DEATH_STAR_HEALTH_BAR_LEN = 260;
    public static final short DEATH_STAR_VERSUS_SCREEN_IN_MILLIS = 4000;

    public static final short STAR_SHIELD_HEALTH = 800;

    public static final float STAR_LASER_SHOOT_TIME = 0.5f;
    public static final byte STAR_LASER_DAMAGE = 4;

    public static final byte DEMOMAN_DAMAGE = 40;
    public static final byte DEMOMAN_HEALTH = 30;

    public static final float FACTORY_SPAWN_TIME = 1.65f;
    public static final short FACTORY_HEALTH = 800;
    public static final byte FACTORY_SPEED = -3;
    public static final short FACTORY_HEALTH_BAR_LEN = 800;

    public static final byte MINION_HEALTH = 2;
    public static final byte MINION_DAMAGE = 5;

    public static final byte ROCKET_SPEED = -45;
    public static final short ROCKET_DAMAGE = 175;

    public static final byte VADER_HEALTH = 2;
    public static final byte VADER_DAMAGE = 5;
    public static final byte NUMBER_VADER = 8;
    public static final byte MIN_NUMBER_VADER = (byte) (NUMBER_VADER / 2f);

    public static final byte TRIPLE_FIGHTER_HEALTH = 6;
    public static final byte TRIPLE_FIGHTER_DAMAGE = 10;

    // explosions
    public static final byte NUMBER_EXPLOSION = 40;

    // screens
    public static final int ANIMATION_SCREEN_WIDTH = (int) (SCREEN_WIDTH * 1.35);

    // sprites on levels
    public static final byte PORTAL_FRAME = 70;
    public static final byte NUMBER_PORTAL_IMAGES = 20;
    public static final short PORTAL_LIFE_TIME = 7_000;

    public static final byte SHOTGUN_KIT_SPEED = 2;

    public static final byte HEALTH_KIT_SPEED = 2;

    // bullets
    public static final byte LIGHTNING_SHOOT_TIME = 35;
    public static final byte LIGHTNING_DAMAGE = 1;
    public static final byte NUMBER_LIGHTNING_IMAGES = 13;

    public static final byte BULLET_DYNAMITE_DAMAGE = 1;

    public static final byte BULLET_SATURN_DAMAGE = 1;

    public static final byte BUCKSHOT_SATURN_DAMAGE = 1;

    public static final byte BULLET_ENEMY_ORBIT_DAMAGE = 5;

    public static final byte BULLET_ENEMY_DAMAGE = 5;
    public static final byte BULLET_ENEMY_SPEED = 15;
    public static final byte NUMBER_BULLETS_ENEMY = 30;

    public static final byte BULLET_BOSS_VADERS_DAMAGE = 25;

    public static final byte BULLET_BOSS_DAMAGE = 5;
    public static final byte BULLET_BOSS_SPEED = 6;

    public static final byte BULLET_DAMAGE = 1;
    public static final byte BULLET_SPEED = 34;

    public static final byte BUCKSHOT_DAMAGE = 3;
    public static final byte BUCKSHOT_SPEED = 20;

    public static final byte BOMB_DAMAGE = 5;
    public static final byte BOMB_SPEED = -20;
    public static final byte NUMBER_BOMBS = 15;

    public static final byte SUNRISE_BOMB_SPEED = 35;
    public static final byte SUNRISE_BOMB_DAMAGE = 20;
    public static final float SUNRISE_BOMB_BOOM_TIME_IN_SECS = 2.12f;
    public static final float SUNRISE_BOMB_RED_TIME_IN_SECS = 0.08f;
    public static final byte NUMBER_SUNRISE_BOMBS = 3;

    // other
    public static final byte BUTTON_CLICK_TIME = 100;

    public static final float CHANGER_GUNS_CLICK_TIME = 0.7f;

    public static final float PAUSE_BUTTON_CLICK_TIME = 1;

    public static final short ULTIMATE_DAMAGE = 10_000;
}
