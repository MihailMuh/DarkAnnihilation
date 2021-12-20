package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.utils.AnimationG;

public abstract class BaseScreen {
    private final AnimationG animation;
    private final float width;
    private float time = 0;
    public float x;

    public BaseScreen(AnimationG animation, float width, float X) {
        this.animation = animation;
        this.width = width;
        x = X;
    }

    public void render() {
        spriteBatch.draw(animation.get(time), x, 0, width, SCREEN_HEIGHT);
        time += delta;
        if (animation.isFinished()) {
            time = 0;
        }
    }
}
