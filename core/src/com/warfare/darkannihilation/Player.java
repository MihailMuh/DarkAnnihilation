package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_HEALTH;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.Warrior;

public class Player extends Warrior {
    private float endX, endY;
    private final int speed;

    public Player(AtlasRegion texture) {
        super(texture, MILLENNIUM_FALCON_HEALTH, 10000, HALF_SCREEN_HEIGHT);

        speed = 20;
    }

    @Override
    public void reset() {
        super.reset();
        x = HALF_SCREEN_WIDTH;

        endX = (x -= halfWidth);
        endY = (y -= halfHeight);
    }

    public void collidesWithEnemy(Warrior enemy) {
        if (intersect(enemy)) {
            enemy.boom();
        }
    }

    public void setCoordinates(float X, float Y) {
        endX = X - halfWidth;
        endY = Y - halfHeight;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        speedX = (endX - x) * speed * delta;
        speedY = (endY - y) * speed * delta;
    }

    @Override
    public void render() {
        draw();
    }
}
