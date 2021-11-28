package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationG;

public class Explosion extends BaseSprite {
    private final AnimationG<AtlasRegion> animation;
    private float timer;

    public Explosion(AnimationG<AtlasRegion> animation) {
        super(animation.get(0));
        this.animation = animation;
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;

        visible = true;
    }

    @Override
    public void reset() {
        visible = false;
        timer = 0;
    }

    @Override
    public void render() {
        spriteBatch.draw(animation.get(timer), x, y, width, height);
        timer += delta;
        if (animation.isFinished()) {
            visible = false;
        }
    }

    @Override
    public void update() {

    }
}
