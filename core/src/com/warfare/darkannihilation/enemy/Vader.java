package com.warfare.darkannihilation.enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.warfare.darkannihilation.constants.Constants.VADER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.VADER_HEALTH;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Vader extends Warrior {
    private AtlasRegion img;

    public Vader(PoolWrap<Explosion> explosionPool) {
        super(ImageHub.vadersImages[0], VADER_HEALTH, VADER_DAMAGE, explosionPool);
    }

    @Override
    public void reset() {
        super.reset();
        img = ImageHub.vadersImages[random(0, 2)];

        x = random(SCREEN_WIDTH);

        speedX = random(-385f, 385f);
        speedY = random(230f, 770f);
    }

    @Override
    public void update() {
        x += speedX * delta;
        y -= speedY * delta;

        if (x < -width || x > SCREEN_WIDTH || y < -height) {
            reset();
        }
    }

    @Override
    public void render() {
        if (visible) {
            spriteBatch.draw(img, x, y, width, height);
        }
    }

    @Override
    protected void explode() {
        explodeDefault();
    }
}
