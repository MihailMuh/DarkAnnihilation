package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.utils.AnimationSuper;

public class DynamicScreen extends BaseScreen {
    public DynamicScreen(AnimationSuper textures) {
        super(textures, SCREEN_WIDTH * 1.35f, SCREEN_WIDTH * -0.175f);
    }
}
