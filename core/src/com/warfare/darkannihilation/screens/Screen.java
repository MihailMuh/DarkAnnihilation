package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationSuper;
import com.warfare.darkannihilation.utils.Image;

public class Screen extends BaseSprite {
    private final AnimationSuper animation;
    private float time = 0;
    public boolean stop = false;

    public Screen(AnimationSuper animation, int width, int height) {
        super(animation.get(0), width, height);
        this.animation = animation;
    }

    public Screen(Image image, int width, int height) {
        this(new AnimationSuper(new Image[]{image}, 0), width, height);
    }

    @Override
    public void render() {
        animation.get(time).draw(x, 0, width, SCREEN_HEIGHT);
        spriteBatch.endSolidScreen();

        if (stop) return;

        time += delta;
        if (animation.isFinished()) {
            time = 0;
        }
    }

    @Override
    public void update() {

    }
}
