package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.BaseSprite;

public class Player extends BaseSprite {
    private float endX, endY;
    private final int speed;
    public float boostX, boostY;

    public Player(AtlasRegion texture) {
        super(texture, HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT);

        endX = (x -= halfWidth);
        endY = (y -= halfHeight);

        speed = 20;
    }

    public void setCoordinates(float X, float Y) {
        endX = X - halfWidth;
        endY = Y - halfHeight;
    }

    @Override
    public void update() {
        x += boostX;
        y += boostY;

        boostX = (endX - x) * speed * delta;
        boostY = (endY - y) * speed * delta;
    }
}
