package com.warfare.darkannihilation.abstraction;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.Image;

public abstract class BaseButton extends BaseSprite {
    public BaseButton(Image image) {
        super(image);
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
