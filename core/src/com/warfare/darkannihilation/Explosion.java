package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;

public class Explosion extends BaseSprite {
    private Animation<AtlasRegion> animation;

    private float timer;

    public Explosion() {
        super(getImages().tripleExplosionAnim.getKeyFrames()[21]);
    }

    public void start(float x, float y, byte type) {
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
        }

        AtlasRegion firstFrame = animation.getKeyFrame(0);

        setRegion(firstFrame);
        setScale(small ? 0.4f : 1);
        setSize(firstFrame.originalWidth, firstFrame.originalHeight);
        setOriginCenter();
        setCenter(x, y);

        timer = 0;
        visible = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void updateInThread() {
        setRegion(animation.getKeyFrame(timer));

        timer += delta;
        if (animation.isAnimationFinished(timer)) {
            visible = false;
        }
    }
}
