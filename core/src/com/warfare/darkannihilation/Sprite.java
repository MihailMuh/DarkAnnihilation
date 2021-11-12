package com.warfare.darkannihilation;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.abstraction.BaseSprite;

public class Sprite extends BaseSprite {
    public Sprite(Texture texture) {
        super(texture);
    }

    public void setCoordinates(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;
    }

    @Override
    public void update() {
        //y += 45 * DartGame.delta;
    }
}
