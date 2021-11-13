package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Screen {
    private final float width;
    public float x;
    public Animation<TextureRegion> animation;

    public Screen(Animation<TextureRegion> textures) {
        animation = textures;
        x = SCREEN_WIDTH * -0.175f;
        width = SCREEN_WIDTH * 1.35f;
    }

    public void render() {
        spriteBatch.draw(animation.getKeyFrame(time), x, 0, width, SCREEN_HEIGHT);
    }
}
