package com.warfare.darkannihilation.player;

import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;
import static com.warfare.darkannihilation.constants.Names.NULL_HEART;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.hub.ImageHub;

public class Heart extends BaseSprite {
    protected AtlasRegion region;

    public Heart(int X, int Y) {
        super(ImageHub.fullHeartRed, 70, 60);
        x = X;
        y = Y;

        region = ImageHub.fullHeartRed;
    }

    public void setType(byte heart) {
        switch (heart) {
            case FULL_HEART:
                region = ImageHub.fullHeartRed;
                return;
            case HALF_HEART:
                region = ImageHub.halfHeartRed;
                return;
            case NULL_HEART:
                region = ImageHub.nullHeartRed;
                return;
        }
        visible = false;
    }

    @Override
    public void render() {
        spriteBatch.draw(region, x, y, width, height);
    }

    @Override
    public void reset() {
        visible = false;
    }

    @Override
    public void update() {
    }
}
