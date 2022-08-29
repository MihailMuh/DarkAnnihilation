package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.MINION_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.MINION_HEALTH;
import static com.warfare.darkannihilation.constants.Names.BUCKSHOT;
import static com.warfare.darkannihilation.constants.Names.MINION;
import static com.warfare.darkannihilation.hub.Resources.getImages;

import com.warfare.darkannihilation.abstraction.sprite.MovingSprite;

public class Minion extends TripleFighter {
    public Minion() {
        super(getImages().minionImg, MINION_HEALTH, MINION_DAMAGE, 2, random(0.8f, 1.5f));

        visible = false;
        name = MINION;
    }

    @Override
    public boolean killedBy(MovingSprite sprite) {
        boolean killed = false;

        if (intersect(sprite)) {
            killed = sprite.damage >= health;

            if (sprite.name == BUCKSHOT) {
                kill();
            } else {
                sprite.damage(damage);
                damage(sprite.damage);
            }
        }
        return killed;
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
