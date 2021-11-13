package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.warfare.darkannihilation.abstraction.BaseSprite;

public class Player extends BaseSprite {
    private float endX, endY;
    private final int speed;
    public float boostX, boostY;

    public Player(Texture texture) {
        super(texture, HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT, 100, 120);

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
