package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;

public class Screen extends BaseSprite {
    private final Animation<AtlasRegion> animation;
    public boolean stopAnimation = false;

    public Screen(Animation<AtlasRegion> animation, int width, int height) {
        super(animation.getKeyFrame(0), width, height);
        this.animation = animation;
    }

    public Screen(AtlasRegion region, int width, int height) {
        this(new Animation<>(0, region), width, height);
    }

    @Override
    public void render() {
        if (!stopAnimation) setRegion(animation.getKeyFrame(time));
        super.render();
        spriteBatch.endSolidScreen();
    }

    @Override
    public void update() {

    }
}
