package com.warfare.darkannihilation.screens;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class MovingScreen extends Screen {
    public MovingScreen(Animation<AtlasRegion> textures) {
        super(textures, (int) (SCREEN_WIDTH * 1.35), SCREEN_HEIGHT);
        setX(SCREEN_WIDTH * -0.175f);
    }
}
