package com.warfare.darkannihilation.bullet;

import com.warfare.darkannihilation.abstraction.sprite.MovementSprite;
import com.warfare.darkannihilation.utils.Image;

public abstract class BaseBullet extends MovementSprite {
    public BaseBullet(Image image, int damage) {
        super(image, 0, damage, 0);

        visible = false;
    }

    @Override
    public boolean killedBy(MovementSprite sprite) {
        if (intersect(sprite)) {
            sprite.damage(this);
            kill();
            return true;
        }
        return false;
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;

        visible = true;
    }
}
