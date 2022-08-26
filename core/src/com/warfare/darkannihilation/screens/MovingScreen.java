package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.constants.Constants.ANIMATION_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class MovingScreen extends AnimatedScreen {
    public MovingScreen(Animation<AtlasRegion> textures) {
        super(textures, ANIMATION_SCREEN_WIDTH, SCREEN_HEIGHT);
        setX(SCREEN_WIDTH * -0.175f);
    }
}
