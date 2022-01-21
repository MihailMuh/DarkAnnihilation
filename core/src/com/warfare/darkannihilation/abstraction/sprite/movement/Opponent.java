package com.warfare.darkannihilation.abstraction.sprite.movement;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class Opponent extends MovementSprite {
    public Opponent(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture, int maxHealth, int damage, int killScore) {
        super(explosionPool, texture, maxHealth, damage, killScore);
    }

    public boolean killedByPlayer(Player player) {
        if (intersect(player)) {
            killFromPlayer();
            player.damage(damage);
            return true;
        }
        return false;
    }

    public void killFromPlayer() {
        kill();
    }
}
