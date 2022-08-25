package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationSuper;

public class Screen extends BaseSprite {
    private final AnimationSuper animation;
    private float time = 0;
    public boolean stop = false;

    public Screen(AnimationSuper animation, int width, int height) {
        super(animation.get(0), width, height);
        this.animation = animation;
    }

    public Screen(AtlasRegion region, int width, int height) {
        this(new AnimationSuper(new AtlasRegion[]{region}, 0), width, height);
    }

    @Override
    public void render() {
        setRegion(animation.get(time));
        super.render();
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
