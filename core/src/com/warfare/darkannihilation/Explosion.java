package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.warfare.darkannihilation.abstraction.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationG;

public class Explosion extends BaseSprite {
    private final AnimationG animation;
    private final int smallWidth, smallHeight;

    private float timer;
    private boolean small;

    public Explosion(AnimationG animation) {
        super(animation.get(0));
        this.animation = animation;
        smallWidth = (int) (width / 2.5);
        smallHeight = (int) (height / 2.5);
    }

    public void start(float X, float Y, boolean small) {
        if (!small) {
            x = X - halfWidth;
            y = Y - halfHeight;
        } else {
            x = X - smallWidth / 2f;
            y = Y - smallHeight / 2f;
            this.small = true;
        }

        visible = true;
    }

    @Override
    public void reset() {
        small = false;
        timer = 0;
    }

    @Override
    public void render() {
        if (!small) {
            spriteBatch.draw(animation.get(timer), x, y, width, height);
        } else {
            spriteBatch.draw(animation.get(timer), x, y, smallWidth, smallHeight);
        }

        timer += delta;
        if (animation.isFinished()) {
            visible = false;
        }
    }

    @Override
    public void update() {
    }
}
