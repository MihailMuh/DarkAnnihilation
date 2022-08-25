package com.warfare.darkannihilation.player;

import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;
import static com.warfare.darkannihilation.constants.Names.NULL_HEART;
import static com.warfare.darkannihilation.hub.Resources.getImages;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;

public class Heart extends BaseSprite {
    public Heart(int x, int y) {
        super(getImages().fullHeartRed, 70, 60);
        setPosition(x, y);
    }

    public void setType(byte heart) {
        switch (heart) {
            case FULL_HEART:
                setRegion(getImages().fullHeartRed);
                return;
            case HALF_HEART:
                setRegion(getImages().halfHeartRed);
                return;
            case NULL_HEART:
                setRegion(getImages().nullHeartRed);
                return;
        }
        visible = false;
    }

    @Override
    public void update() {
    }
}
