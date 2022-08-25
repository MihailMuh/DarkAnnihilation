package com.warfare.darkannihilation.scenes.versus;

import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.screens.Screen;

class VersusScreen extends Screen {
    public VersusScreen(AtlasRegion region) {
        super(region, SCREEN_HEIGHT, SCREEN_HEIGHT);

        setCenterX(HALF_SCREEN_WIDTH);
    }
}
