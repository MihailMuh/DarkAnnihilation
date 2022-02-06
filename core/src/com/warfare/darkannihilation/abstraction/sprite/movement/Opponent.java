package com.warfare.darkannihilation.abstraction.sprite.movement;

import static com.warfare.darkannihilation.constants.Names.ENEMY;
import static com.warfare.darkannihilation.constants.Names.PLAYER;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.Image;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class Opponent extends MovementSprite {
    public boolean shouldKill = false;

    public Opponent(PoolWrap<Explosion> explosionPool, Image image, int maxHealth, int damage, int killScore) {
        super(explosionPool, image, maxHealth, damage, killScore);
        name = ENEMY;
    }

    public void start() {
        visible = true;
        shouldKill = false;
    }

    @Override
    public void damage(MovementSprite sprite) {
        if (sprite.name == PLAYER) {
            killFromPlayer();
            return;
        }

        health -= sprite.damage;
        if (health <= 0) kill();
    }

    public void killFromPlayer() {
        kill();
    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
