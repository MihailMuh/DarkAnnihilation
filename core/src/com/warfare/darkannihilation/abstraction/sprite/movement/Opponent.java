package com.warfare.darkannihilation.abstraction.sprite.movement;

import static com.warfare.darkannihilation.constants.Names.ENEMY;
import static com.warfare.darkannihilation.constants.Names.PLAYER;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.hub.SoundHub;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class Opponent extends MovementSprite {
    public boolean shouldKill = false;

    public Opponent(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture, int maxHealth, int damage, int killScore) {
        super(explosionPool, texture, maxHealth, damage, killScore);
        name = ENEMY;
    }

    public void start() {
        visible = true;
        shouldKill = false;
    }

    @Override
    public void damage(MovementSprite sprite) {
        if (sprite.name == PLAYER) {
            SoundHub.metalSound.play();
            killFromPlayer();
            return;
        }

        health -= sprite.damage;
        if (health <= 0) kill();
    }

    public void killFromPlayer() {
        kill();
    }
}
