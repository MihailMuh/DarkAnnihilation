package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warfare.darkannihilation.abstraction.BaseScreen;
import com.warfare.darkannihilation.utils.AnimationG;

public class FullScreen extends BaseScreen {
    public FullScreen(AnimationG<TextureRegion> animation) {
        super(animation, SCREEN_WIDTH, 0);
    }
}
