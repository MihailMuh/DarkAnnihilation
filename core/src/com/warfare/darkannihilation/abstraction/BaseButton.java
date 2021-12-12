package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class BaseButton extends BaseSprite {
    public BaseButton(TextureAtlas.AtlasRegion texture) {
        super(texture);
    }

    public abstract void onClick(float X, float Y);

    protected boolean checkClick(float X, float Y) {
        return x <= X && X <= right() && y <= Y && Y <= top();
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }
}
