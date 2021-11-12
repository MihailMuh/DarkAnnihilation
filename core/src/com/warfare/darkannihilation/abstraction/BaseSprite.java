package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.Rect;

public abstract class BaseSprite extends Rect {
    private final Texture image;

    public BaseSprite(Texture texture, float x, float y) {
        super(x, y, texture.getWidth(), texture.getHeight());
        image = texture;
    }

    public BaseSprite(Texture texture) {
        super(0, 0, texture.getWidth(), texture.getHeight());
        image = texture;
    }

    public void render() {
        spriteBatch.draw(image, x, y, width, height);
    }

    public abstract void update();
}
