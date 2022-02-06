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
    private final AnimationSuper animationTriple;
    private final AnimationSuper animationDefault;
    private final AnimationSuper animationHuge;
    private AnimationSuper mainAnim;
    private int explosionWidth, explosionHeight;

    private float timer;

    public Explosion() {
        super(getImages().defaultExplosionAnim.get(0));
        this.animationTriple = getImages().tripleExplosionAnim;
        this.animationDefault = getImages().defaultExplosionAnim;
        this.animationHuge = getImages().hugeExplosionAnim;
    }

    public void start(float X, float Y, byte type) {
        boolean small = false;

        switch (type) {
            case SMALL_EXPLOSION_TRIPLE:
                small = true;
                mainAnim = animationTriple;
                break;
            case SMALL_EXPLOSION_DEFAULT:
                small = true;
                mainAnim = animationDefault;
                break;
            case MEDIUM_EXPLOSION_TRIPLE:
                mainAnim = animationTriple;
                break;
            case MEDIUM_EXPLOSION_DEFAULT:
                mainAnim = animationDefault;
                break;
            case HUGE_EXPLOSION:
                mainAnim = animationHuge;
                break;
        }

        explosionWidth = mainAnim.get(0).width;
        explosionHeight = mainAnim.get(0).height;
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
        mainAnim.get(timer).draw(x, y, explosionWidth, explosionHeight);

        timer += delta;
        if (mainAnim.isFinished()) {
            visible = false;
        }
    }

    @Override
    public void update() {
    }
}
