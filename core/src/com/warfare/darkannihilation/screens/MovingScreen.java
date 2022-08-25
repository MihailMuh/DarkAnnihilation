package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.utils.AnimationSuper;

public class MovingScreen extends Screen {
    public MovingScreen(AnimationSuper textures) {
        super(textures, (int) (SCREEN_WIDTH * 1.35), SCREEN_HEIGHT);
        setX(SCREEN_WIDTH * -0.175f);
    }
}
