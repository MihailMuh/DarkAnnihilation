package com.warfare.darkannihilation;

public final class Constants {
    //    characters
    public static final byte MILLENNIUM_FALCON_HEALTH = 50;
    public static final float MILLENNIUM_FALCON_SHOOT_TIME = 0.065f;
    public static final short MILLENNIUM_FALCON_SHOTGUN_TIME = 515;
    public static final byte NUMBER_MILLENNIUM_FALCON_BULLETS = 40;
    public static final short NUMBER_MILLENNIUM_FALCON_BUCKSHOT = NUMBER_MILLENNIUM_FALCON_BULLETS + 30;

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

    //    second level
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

    //    first level
    public static final short BOSS_SHOOT_TIME = 450;
    public static final short BOSS_HEALTH = 300;
    public static final short BOSS_HEALTH_BAR_LEN = 140;

    public static final short DEMOMAN_SHOOT_TIME = 150;
    public static final byte DEMOMAN_DAMAGE = 40;
    public static final byte DEMOMAN_HEALTH = 30;

    public static final short FACTORY_SPAWN_TIME = 1_000;
    public static final short FACTORY_HEALTH = 300;
    public static final byte FACTORY_SPEED = 3;
    public static final short FACTORY_HEALTH_BAR_LEN = 500;

    public static final byte MINION_HEALTH = 2;
    public static final short MINION_SHOOT_TIME = 900;
    public static final byte MINION_DAMAGE = 5;

    public static final byte ROCKET_SPEED = 35;
    public static final short ROCKET_DAMAGE = 175;

    public static final byte VADER_HEALTH = 2;
    public static final byte VADER_DAMAGE = 5;
    public static final byte NUMBER_VADER = 12;

    public static final short TRIPLE_FIGHTER_SHOOT_TIME = 1_500;
    public static final byte TRIPLE_FIGHTER_HEALTH = 6;
    public static final byte TRIPLE_FIGHTER_DAMAGE = 10;

    //    explosions
    public static final byte NUMBER_SKULL_EXPLOSION_IMAGES = 13;
    public static final byte SKULL_EXPLOSION_FRAME_TIME = 33;
    public static final byte NUMBER_SKULL_EXPLOSIONS = 3;

    public static final byte NUMBER_DEFAULT_EXPLOSION_IMAGES = 28;
    public static final byte NUMBER_DEFAULT_LARGE_EXPLOSION = 20;
    public static final byte NUMBER_DEFAULT_SMALL_EXPLOSION = NUMBER_DEFAULT_LARGE_EXPLOSION + 18;

    public static final byte NUMBER_TRIPLE_EXPLOSION_IMAGES = 23;
    public static final byte TRIPLE_EXPLOSION_FRAME_TIME = 30;
    public static final byte NUMBER_TRIPLE_LARGE_EXPLOSION = NUMBER_DEFAULT_SMALL_EXPLOSION + 23;
    public static final byte NUMBER_TRIPLE_SMALL_EXPLOSION = NUMBER_TRIPLE_LARGE_EXPLOSION + 13;

    public static final int NUMBER_ALL_EXPLOSION = 3 + 23 + 18 + 23 + 13;

    //    screens
    public static final byte LOADING_SCREEN_FRAME_TIME = 45;
    public static final byte NUMBER_LOADING_SCREEN_IMAGES = 12;

    public static final byte NUMBER_STAR_SCREEN_IMAGES = 34;

    public static final short THUNDER_SCREEN_FRAME_TIME = 135;
    public static final byte NUMBER_THUNDER_SCREEN_IMAGES = 20;

    //    sprites on levels
    public static final byte PORTAL_FRAME = 70;
    public static final byte NUMBER_PORTAL_IMAGES = 20;
    public static final short PORTAL_LIFE_TIME = 7_000;

    public static final byte SHOTGUN_KIT_SPEED = 2;

    public static final byte HEALTH_KIT_SPEED = 2;

    //    bullets
    public static final byte LIGHTNING_SHOOT_TIME = 35;
    public static final byte LIGHTNING_DAMAGE = 1;
    public static final byte NUMBER_LIGHTNING_IMAGES = 13;

    public static final byte BULLET_DYNAMITE_DAMAGE = 1;

    public static final byte BULLET_SATURN_DAMAGE = 1;

    public static final byte BUCKSHOT_SATURN_DAMAGE = 1;

    public static final byte BULLET_ENEMY_ORBIT_DAMAGE = 5;

    public static final byte BULLET_ENEMY_DAMAGE = 5;

    public static final byte BULLET_BOSS_VADERS_DAMAGE = 25;

    public static final byte BULLET_BOSS_DAMAGE = 5;
    public static final byte BULLET_BOSS_SPEED = 6;

    public static final byte BULLET_DAMAGE = 1;
    public static final short BULLET_SPEED = 770;

    public static final byte BUCKSHOT_DAMAGE = 3;
    public static final byte BUCKSHOT_SPEED = 8;

    public static final byte BOMB_DAMAGE = 5;
    public static final byte BOMB_SPEED = 15;

    public static final byte NUMBER_BULLETS_ENEMY = 50;
    public static final byte NUMBER_BOMBS = NUMBER_BULLETS_ENEMY + 20;
    public static final byte NUMBER_BOSS_SHOTS = NUMBER_BOMBS + 30;

    //    other
    public static final float BUTTON_CLICK_TIME = 0.35f;

    public static final short CHANGER_GUNS_CLICK_TIME = 700;

    //    constants
    public static final String SERVER_IP = "http://78.29.33.173:49150/";
    public static final String TAG = "D'Ark";
    public static final String ADMOB_ID = "ca-app-pub-6694626552209820/7260374588";
}
