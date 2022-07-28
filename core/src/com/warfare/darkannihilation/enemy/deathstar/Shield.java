package com.warfare.darkannihilation.enemy.deathstar;

import static com.warfare.darkannihilation.constants.Constants.STAR_SHIELD_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.ULTIMATE_DAMAGE;
import static com.warfare.darkannihilation.hub.Resources.getImages;

import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.player.Player;

public class Shield extends Opponent {
    public Shield() {
        super(getImages().starShield, STAR_SHIELD_HEALTH, ULTIMATE_DAMAGE, 0);
        visible = false;
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y;

        health = maxHealth;
        visible = true;
    }

    @Override
    public boolean killedByPlayer(Player player) {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void kill() {
        explodeHuge();
    }
}
