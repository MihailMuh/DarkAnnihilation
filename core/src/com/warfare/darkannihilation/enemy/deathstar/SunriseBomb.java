package com.warfare.darkannihilation.enemy.deathstar;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static com.warfare.darkannihilation.constants.Constants.SUNRISE_BOMB_BOOM_TIME_IN_SECS;
import static com.warfare.darkannihilation.constants.Constants.SUNRISE_BOMB_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.SUNRISE_BOMB_RED_TIME_IN_SECS;
import static com.warfare.darkannihilation.constants.Constants.SUNRISE_BOMB_SPEED;
import static com.warfare.darkannihilation.constants.Names.SUNRISE_BOMB;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.warfare.darkannihilation.bullet.BaseBullet;

public class SunriseBomb extends BaseBullet {
    private float lastBoom, lastRedColor;
    private float lastChangeRedColor, lastRedColorTimer;
    private boolean red;

    public SunriseBomb() {
        super(getImages().sunriseBomb, SUNRISE_BOMB_DAMAGE, SUNRISE_BOMB_SPEED);
        name = SUNRISE_BOMB;
    }

    public void start(float X, float Y, float cos, float sin) {
        speedX = SUNRISE_BOMB_SPEED * cos;
        speedY = SUNRISE_BOMB_SPEED * sin;

        lastBoom = time;
        lastRedColor = time;

        lastChangeRedColor = time;
        lastRedColorTimer = 0.7f;
        red = false;

        start(X, Y);
    }

    @Override
    public void update() {
        translate(speedX, speedY);

        speedX /= 1.055;
        speedY /= 1.055;

        if (time - lastBoom >= SUNRISE_BOMB_BOOM_TIME_IN_SECS) {
            lastBoom = Integer.MAX_VALUE;
            internalKill();
            aLotOfShots();
            return;
        }
        if (time - lastChangeRedColor >= lastRedColorTimer) {
            lastChangeRedColor = time + SUNRISE_BOMB_RED_TIME_IN_SECS;
            lastRedColorTimer /= 2f;

            red = true;
            lastRedColor = time;
        }

        if (red) {
            if (time - lastRedColor >= SUNRISE_BOMB_RED_TIME_IN_SECS) {
                red = false;
            }
        }
    }

    private void aLotOfShots() {
        float X = centerX();
        float Y = centerY();

        for (float angle = 0; angle < 360; angle += 10) {
            getPools().bulletEnemyPool.obtain(X, Y, cosDeg(angle), sinDeg(angle), angle);
        }
    }

    @Override
    public void kill() {
        explodeHuge();
    }

    @Override
    public void render() {
        if (red) {
            setRegion(getImages().sunriseBombRed);
        } else {
            setRegion(getImages().sunriseBomb);
        }
        super.render();
    }
}
