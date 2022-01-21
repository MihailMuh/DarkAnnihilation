package com.warfare.darkannihilation.player;

import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;

import com.warfare.darkannihilation.hub.ImageHub;

public class ArmorHeart extends Heart {
    public ArmorHeart() {
        super(0, 0);
    }

    public void start(int X, int Y) {
        visible = true;

        x = X;
        y = Y;

        region = ImageHub.fullHeartBlue;
    }

    @Override
    public void setType(byte heart) {
        switch (heart) {
            case FULL_HEART:
                region = ImageHub.fullHeartBlue;
                return;
            case HALF_HEART:
                region = ImageHub.halfHeartBlue;
                return;
        }
        visible = false;
    }
}
