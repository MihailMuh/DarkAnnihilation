package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationSuper;

public abstract class BaseScreen extends BaseSprite {
    private final AnimationSuper animation;
    private float time = 0;

    public BaseScreen(AnimationSuper animation, float width, float X) {
        super(animation.get(0), width, SCREEN_HEIGHT);
        this.animation = animation;
        x = X;
    }

    @Override
    public void render() {
        spriteBatch.draw(animation.get(time), x, 0, width, SCREEN_HEIGHT);
        time += delta;
        if (animation.isFinished()) {
            time = 0;
        }
    }

    @Override
    public void update() {

    }
}
