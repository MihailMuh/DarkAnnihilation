package com.warfare.darkannihilation.player;

import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;
import static com.warfare.darkannihilation.constants.Names.NULL_HEART;
import static com.warfare.darkannihilation.hub.Resources.getImages;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;

public class Heart extends BaseSprite {
    public Heart(int X, int Y) {
        super(getImages().fullHeartRed, 70, 60);
        x = X;
        y = Y;

        image = getImages().fullHeartRed;
    }

    public void setType(byte heart) {
        switch (heart) {
            case FULL_HEART:
                image = getImages().fullHeartRed;
                return;
            case HALF_HEART:
                image = getImages().halfHeartRed;
                return;
            case NULL_HEART:
                image = getImages().nullHeartRed;
                return;
        }
        visible = false;
    }

    @Override
    public void update() {
    }
}
