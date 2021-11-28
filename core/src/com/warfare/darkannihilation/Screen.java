package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warfare.darkannihilation.utils.AnimationG;

public class Screen {
    private final AnimationG<TextureRegion> animation;
    private final float width;
    private float time = 0;
    public float x;

    public Screen(AnimationG<TextureRegion> textures) {
        animation = textures;
        x = SCREEN_WIDTH * -0.175f;
        width = SCREEN_WIDTH * 1.35f;
    }

    public void render() {
        spriteBatch.draw(animation.get(time), x, 0, width, SCREEN_HEIGHT);
        time += delta;
        if (animation.isFinished()) {
            time = 0;
        }
    }
}
