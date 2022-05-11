package com.warfare.darkannihilation.bullet;

import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.utils.Image;

public abstract class BaseBullet extends Opponent {
    public BaseBullet(Image image, int damage, float speedY) {
        this(image, damage, 0, speedY);
    }

    public BaseBullet(Image image, int damage, int maxHealth, float speedY) {
        super(image, maxHealth, damage, 0);

        visible = false;
        this.speedY = speedY;
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;

        visible = true;
    }
}
