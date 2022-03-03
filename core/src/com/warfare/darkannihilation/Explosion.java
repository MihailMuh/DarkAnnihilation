package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationSuper;

public class Explosion extends BaseSprite {
    private AnimationSuper animation;
    private int explosionWidth, explosionHeight;

    private float timer;

    public Explosion() {
        super(getImages().blackColor);
    }

    public void start(float X, float Y, byte type) {
        boolean small = false;

        switch (type) {
            case SMALL_EXPLOSION_TRIPLE:
                small = true;
                animation = getImages().tripleExplosionAnim;
                break;
            case SMALL_EXPLOSION_DEFAULT:
                small = true;
                animation = getImages().defaultExplosionAnim;
                break;
            case MEDIUM_EXPLOSION_TRIPLE:
                animation = getImages().tripleExplosionAnim;
                break;
            case MEDIUM_EXPLOSION_DEFAULT:
                animation = getImages().defaultExplosionAnim;
                break;
            case HUGE_EXPLOSION:
                animation = getImages().hugeExplosionAnim;
                break;
        }

        explosionWidth = animation.get(0).width;
        explosionHeight = animation.get(0).height;
        if (small) {
            explosionWidth /= 2.5;
            explosionHeight /= 2.5;
        }

        x = X - explosionWidth / 2f;
        y = Y - explosionHeight / 2f;

        timer = 0;
        visible = true;
    }

    @Override
    public void render() {
        animation.get(timer).draw(x, y, explosionWidth, explosionHeight);

        timer += delta;
        if (animation.isFinished()) {
            visible = false;
        }
    }

    @Override
    public void update() {
    }
}
