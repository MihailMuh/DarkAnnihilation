package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.MINION_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.MINION_HEALTH;
import static com.warfare.darkannihilation.constants.Names.MINION;
import static com.warfare.darkannihilation.hub.Resources.getImages;

public class Minion extends TripleFighter {
    public Minion() {
        super(getImages().minionImg, MINION_HEALTH, MINION_DAMAGE, 2, random(0.8f, 1.5f));

        visible = false;
        name = MINION;
    }

    public void start(float x, float y) {
        setPosition(x, y);
        health = maxHealth;
        visible = true;

        shootTime = random(0.8f, 1.5f);
        speedX = random(-8, 8);
        speedY = -random(2, 5);
    }

    @Override
    public void reset() {
        visible = false;
    }

    @Override
    protected float getMiniAngle() {
        return 0;
    }
}
