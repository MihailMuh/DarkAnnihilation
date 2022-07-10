package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.ENEMY;

import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.utils.Image;

public abstract class Opponent extends MovingSprite {
    public boolean shouldKill = false;

    public Opponent(Image image, int maxHealth, int damage, int killScore) {
        this(image, image.width, image.height, maxHealth, damage, killScore);
    }

    public Opponent(Image image, int width, int height, int maxHealth, int damage, int killScore) {
        super(image, width, height, maxHealth, damage, killScore);
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
        internalKill();
    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
