package com.warfare.darkannihilation.player;

import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;
import static com.warfare.darkannihilation.hub.Resources.getImages;

class ArmorHeart extends Heart {
    public ArmorHeart() {
        super(0, 0);
    }

    public void start(int X, int Y) {
        visible = true;

        setPosition(X, Y);
        setRegion(getImages().fullHeartBlue);
    }

    @Override
    public void setType(byte heart) {
        switch (heart) {
            case FULL_HEART:
                setRegion(getImages().fullHeartBlue);
                return;
            case HALF_HEART:
                setRegion(getImages().halfHeartBlue);
                return;
        }
        visible = false;
    }
}
