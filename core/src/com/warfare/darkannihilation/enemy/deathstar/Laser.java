package com.warfare.darkannihilation.enemy.deathstar;

import static com.warfare.darkannihilation.hub.Resources.getImages;

import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.AnimationSuper;

public class Laser extends BaseSprite {
    private final AnimationSuper animation;
    private int frameWidth, frameHeight;

    private float timer;
    private int lastFrameIndex;

    public Laser() {
        super(getImages().blackColor);

        animation = getImages().deathStarLaser;
        lastFrameIndex = animation.size - 1;
    }

    @Override
    public void update() {

    }
}
