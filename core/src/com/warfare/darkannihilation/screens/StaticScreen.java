package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.utils.AnimationSuper;

public class StaticScreen extends BaseScreen {
    public StaticScreen(AnimationSuper animation) {
        super(animation, SCREEN_WIDTH, 0);
    }
}
