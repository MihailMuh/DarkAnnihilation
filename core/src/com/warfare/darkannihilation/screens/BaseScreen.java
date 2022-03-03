package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationSuper;
import com.warfare.darkannihilation.utils.Image;

public abstract class BaseScreen extends BaseSprite {
    private final AnimationSuper animation;
    private float time = 0;

    public BaseScreen(AnimationSuper animation, int width, float X) {
        super(animation.get(0), width, SCREEN_HEIGHT);
        this.animation = animation;
        x = X;
    }

    public BaseScreen(Image image) {
        super(image, SCREEN_WIDTH, SCREEN_HEIGHT);
        animation = new AnimationSuper(new Image[] {image}, 0);
    }

    @Override
    public void render() {
        animation.get(time).draw(x, 0, width, SCREEN_HEIGHT);
        spriteBatch.endSolidScreen();

        time += delta;
        if (animation.isFinished()) {
            time = 0;
        }
    }

    @Override
    public void update() {

    }
}
