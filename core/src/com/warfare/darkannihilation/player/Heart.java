package com.warfare.darkannihilation.player;

import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;
import static com.warfare.darkannihilation.constants.Names.NULL_HEART;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.hub.ImageHub;

public class Heart extends BaseSprite {
    protected final ImageHub imageHub;

    public Heart(ImageHub imageHub, int X, int Y) {
        super(imageHub.fullHeartRed, 70, 60);
        x = X;
        y = Y;

        this.imageHub = imageHub;

        image = imageHub.fullHeartRed;
    }

    public void setType(byte heart) {
        switch (heart) {
            case FULL_HEART:
                image = imageHub.fullHeartRed;
                return;
            case HALF_HEART:
                image = imageHub.halfHeartRed;
                return;
            case NULL_HEART:
                image = imageHub.nullHeartRed;
                return;
        }
        visible = false;
    }

    @Override
    public void update() {
    }
}
