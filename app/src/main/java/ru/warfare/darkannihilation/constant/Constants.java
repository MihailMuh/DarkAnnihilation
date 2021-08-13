package ru.warfare.darkannihilation.constant;

public final class Constants {
//    characters
    public static final byte MILLENNIUM_FALCON_HEALTH = 50;
    public static final byte MILLENNIUM_FALCON_SHOOT_TIME = 80;
    public static final short MILLENNIUM_FALCON_SHOTGUN_TIME = 515;

    public static final short EMERALD_SHOOT_TIME = 1_300;
    public static final short EMERALD_SHOTGUN_TIME = 1_200;
    public static final byte EMERALD_HEALTH = 100;

    public static final byte SATURN_SHOOT_TIME = 105;
    public static final short SATURN_SHOTGUN_TIME = 130;
    public static final byte SATURN_HEALTH = 20;

    public static final byte BOT_SHOOT_TIME = 70;

//    second level
    public static final byte ATOMIC_BOMB_FRAME_TIME = 30;
    public static final byte NUMBER_ATOMIC_BOMB_IMAGES = 4;

    public static final short BOSS_VADERS_HEALTH = 350;
    public static final short BOSS_VADERS_SHOOT_TIME = 1_000;

    public static final byte BUFFER_HEALTH = 35;
    public static final byte BUFFER_DAMAGE = SATURN_HEALTH;

    public static final short XWING_SHOOT_TIME = 175;
    public static final byte XWING_HEALTH = 5;
    public static final byte XWING_DAMAGE = 10;

    public static final short SUNRISE_SHOOT_TIME = 775;
    public static final byte SUNRISE_HEALTH = 45;
    public static final byte SUNRISE_DAMAGE = BUFFER_DAMAGE;

    public static final short SPIDER_HEALTH = 200;
    public static final byte SPIDER_SHOOT_TIME = 110;
    public static final byte SPIDER_DAMAGE = BUFFER_DAMAGE;
    public static final byte SPIDER_SPEED = XWING_HEALTH;
    public static final short SPIDER_HEALTH_BAR_LEN = 150;

//    first level
    public static final short BOSS_SHOOT_TIME = 450;
    public static final short BOSS_HEALTH = 300;
    public static final short BOSS_HEALTH_BAR_LEN = 140;

    public static final short DEMOMAN_SHOOT_TIME = SPIDER_HEALTH_BAR_LEN;
    public static final byte DEMOMAN_DAMAGE = 40;
    public static final byte DEMOMAN_HEALTH = ATOMIC_BOMB_FRAME_TIME;

    public static final short FACTORY_SPAWN_TIME = BOSS_VADERS_SHOOT_TIME;
    public static final short FACTORY_HEALTH = SPIDER_HEALTH;
    public static final byte FACTORY_SPEED = 3;
    public static final short FACTORY_HEALTH_BAR_LEN = 500;

    public static final byte MINION_HEALTH = 2;
    public static final short MINION_SHOOT_TIME = 900;
    public static final byte MINION_DAMAGE = XWING_HEALTH;

    public static final byte ROCKET_SPEED = BUFFER_HEALTH;
    public static final short ROCKET_DAMAGE = FACTORY_SPAWN_TIME;

    public static final byte VADER_HEALTH = MINION_HEALTH;
    public static final byte VADER_DAMAGE = XWING_HEALTH;
    public static final byte NUMBER_VADER_IMAGES = FACTORY_SPEED;
    public static final byte NUMBER_VADER = XWING_DAMAGE;

    public static final short TRIPLE_FIGHTER_SHOOT_TIME = 1_500;
    public static final byte TRIPLE_FIGHTER_HEALTH = 6;
    public static final byte TRIPLE_FIGHTER_DAMAGE = XWING_DAMAGE;

//    explosions
    public static final byte NUMBER_DEFAULT_EXPLOSION_IMAGES = 28;
    public static final byte NUMBER_DEFAULT_SMALL_EXPLOSION = 13;
    public static final byte NUMBER_DEFAULT_LARGE_EXPLOSION = 23;

    public static final byte NUMBER_SKULL_EXPLOSION_IMAGES = NUMBER_DEFAULT_SMALL_EXPLOSION;
    public static final byte SKULL_EXPLOSION_FRAME_TIME = 33;
    public static final byte NUMBER_SKULL_EXPLOSIONS = FACTORY_SPEED;

    public static final byte NUMBER_TRIPLE_EXPLOSION_IMAGES = NUMBER_DEFAULT_LARGE_EXPLOSION;
    public static final byte TRIPLE_EXPLOSION_FRAME_TIME = ATOMIC_BOMB_FRAME_TIME;
    public static final byte NUMBER_TRIPLE_SMALL_EXPLOSION = NUMBER_SKULL_EXPLOSION_IMAGES;
    public static final byte NUMBER_TRIPLE_LARGE_EXPLOSION = NUMBER_TRIPLE_EXPLOSION_IMAGES;

    public static final int NUMBER_ALL_EXPLOSION = NUMBER_DEFAULT_SMALL_EXPLOSION +
            NUMBER_DEFAULT_LARGE_EXPLOSION + NUMBER_SKULL_EXPLOSIONS +
            NUMBER_TRIPLE_SMALL_EXPLOSION + NUMBER_TRIPLE_LARGE_EXPLOSION;

//    screens
    public static final byte LOADING_SCREEN_FRAME_TIME = SUNRISE_HEALTH;
    public static final byte NUMBER_LOADING_SCREEN_IMAGES = 12;

    public static final byte NUMBER_STAR_SCREEN_IMAGES = 34;

    public static final short THUNDER_SCREEN_FRAME_TIME = 135;
    public static final byte NUMBER_THUNDER_SCREEN_IMAGES = BUFFER_DAMAGE;

//    sprites on levels
    public static final byte PORTAL_FRAME = BOT_SHOOT_TIME;
    public static final byte NUMBER_PORTAL_IMAGES = BUFFER_DAMAGE;
    public static final short PORTAL_LIFE_TIME = 7_000;

    public static final byte SHOTGUN_KIT_SPEED = MINION_HEALTH;

    public static final byte HEALTH_KIT_SPEED = MINION_HEALTH;

//    bullets
    public static final byte LIGHTNING_SHOOT_TIME = BUFFER_HEALTH;
    public static final byte LIGHTNING_DAMAGE = 1;
    public static final byte NUMBER_LIGHTNING_IMAGES = NUMBER_SKULL_EXPLOSION_IMAGES;

    public static final byte BULLET_DYNAMITE_DAMAGE = LIGHTNING_DAMAGE;

    public static final byte BULLET_SATURN_DAMAGE = LIGHTNING_DAMAGE;

    public static final byte BUCKSHOT_SATURN_DAMAGE = LIGHTNING_DAMAGE;

    public static final byte BULLET_ENEMY_ORBIT_DAMAGE = XWING_HEALTH;

    public static final byte BULLET_ENEMY_DAMAGE = XWING_HEALTH;

    public static final byte BULLET_BOSS_VADERS_DAMAGE = 25;

    public static final byte BULLET_BOSS_DAMAGE = XWING_HEALTH;
    public static final byte BULLET_BOSS_SPEED = TRIPLE_FIGHTER_HEALTH;

    public static final byte BULLET_DAMAGE = LIGHTNING_DAMAGE;
    public static final byte BULLET_SPEED = XWING_DAMAGE;

    public static final byte BUCKSHOT_DAMAGE = FACTORY_SPEED;
    public static final byte BUCKSHOT_SPEED = 8;

    public static final byte BOMB_DAMAGE = XWING_HEALTH;
    public static final byte BOMB_SPEED = 15;

//    other
    public static final short BUTTON_CLICK_TIME = FACTORY_HEALTH_BAR_LEN;

    public static final short CHANGER_GUNS_CLICK_TIME = 700;

    public static final boolean DRAW_FPS = false;
    public static final int NANOS_IN_SECOND = 1_000_000_000;

//    constants
    public static final String SERVER_IP = "http://78.29.33.173:49150/";
    public static final String TAG = "D'Ark";
}
