package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.BaseScreen;
import com.warfare.darkannihilation.utils.AnimationG;

public class BackgroundScreen extends BaseScreen {
    public BackgroundScreen(AnimationG animation) {
        super(animation, SCREEN_WIDTH, 0);
    }
}
