package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warfare.darkannihilation.abstraction.BaseScreen;
import com.warfare.darkannihilation.utils.AnimationG;

public class Screen extends BaseScreen {
    public Screen(AnimationG<TextureRegion> textures) {
        super(textures, SCREEN_WIDTH * 1.35f, SCREEN_WIDTH * -0.175f);
    }
}
