package com.warfare.darkannihilation.enemy;

import static com.warfare.darkannihilation.constants.Constants.ROCKET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.ROCKET_SPEED;
import static com.warfare.darkannihilation.constants.Names.DEMOMAN;
import static com.warfare.darkannihilation.constants.Names.FACTORY;
import static com.warfare.darkannihilation.constants.Names.PLAYER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.abstraction.sprite.MovementSprite;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;

public class Rocket extends Opponent {
    public Rocket() {
        super(getImages().rocketImg, 0, ROCKET_DAMAGE, 0);

        visible = false;
    }

    public void start(float X) {
        health = maxHealth;
        x = X;
        y = SCREEN_HEIGHT;

        visible = true;
    }

    @Override
    public boolean killedBy(MovementSprite sprite) {
        if (intersect(sprite)) {
            sprite.damage(this);
        }
        return false;
    }

    @Override
    public void update() {
        y -= ROCKET_SPEED;

        if (y <= -height) {
            visible = false;
        }
    }

    @Override
    public void render() {
        if (visible) super.render();
    }

    @Override
    public void kill() {
        explodeHuge();
        visible = false;
    }

    @Override
    public void damage(MovementSprite sprite) {
        if (sprite.name == PLAYER || sprite.name == DEMOMAN || sprite.name == FACTORY) {
            kill();
        }
    }
}
