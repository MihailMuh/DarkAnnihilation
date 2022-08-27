package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.ENEMY;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.player.Player;

public abstract class Opponent extends MovingSprite {
    public boolean shouldKill = false;

    public Opponent(AtlasRegion region, int maxHealth, int damage, int killScore) {
        super(region, maxHealth, damage, killScore);
        name = ENEMY;
    }

    public void start() {
        visible = true;
        shouldKill = false;
    }

    public boolean killedByPlayer(Player player) {
        if (intersect(player)) {
            player.damage(this);
            killFromPlayer();
            return true;
        }
        return false;
    }

    public void killFromPlayer() {
        kill();
    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
