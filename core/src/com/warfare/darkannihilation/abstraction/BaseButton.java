package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;

public abstract class BaseButton extends BaseSprite {
    public BaseButton(AtlasRegion region) {
        super(region);
    }

    public abstract void onClick(float x, float y);

    public boolean checkClick(float x, float y) {
        return getX() <= x && x <= right() && getY() <= y && y <= top();
    }

    @Override
    public void update() {

    }
}
