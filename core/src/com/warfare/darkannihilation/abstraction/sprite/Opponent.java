package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.ENEMY;
import static com.warfare.darkannihilation.constants.Names.PLAYER;

import com.warfare.darkannihilation.utils.Image;

public abstract class Opponent extends MovementSprite {
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

    @Override
    public boolean killedBy(MovementSprite sprite) {
        boolean killed = false;
        if (intersect(sprite)) {
            sprite.damage(this);

            if (sprite.name == PLAYER) {
                killFromPlayer();
                return true;
            }
            health -= sprite.damage;

            if (health <= 0) {
                kill();
                killed = true;
            }
        }
        return killed;
    }

    public void killFromPlayer() {
        kill();
    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
