package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.warfare.darkannihilation.abstraction.BaseSprite;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.AnimationG;

public class Explosion extends BaseSprite {
    private final AnimationG animationTriple;
    private final AnimationG animationDefault;
    private final AnimationG animationHuge;
    private AnimationG mainAnim;
    private int additionalWidth, additionalHeight;

    private float timer;

    public Explosion(MainGameManager mainGameManager) {
        super(mainGameManager.imageHub.defaultExplosionAnim.get(0));
        this.animationTriple = mainGameManager.imageHub.tripleExplosionAnim;
        this.animationDefault = mainGameManager.imageHub.defaultExplosionAnim;
        this.animationHuge = mainGameManager.imageHub.hugeExplosionAnim;
    }

    public void start(float X, float Y, byte type) {
        switch (type)
        {
            case SMALL_EXPLOSION_TRIPLE:
                additionalWidth = (int) (animationTriple.get(0).originalWidth / 2.5);
                additionalHeight = (int) (animationTriple.get(0).originalHeight / 2.5);
                mainAnim = animationTriple;
                break;
            case SMALL_EXPLOSION_DEFAULT:
                additionalWidth = (int) (animationDefault.get(0).originalWidth / 2.5);
                additionalHeight = (int) (animationDefault.get(0).originalHeight / 2.5);
                mainAnim = animationDefault;
                break;
            case MEDIUM_EXPLOSION_TRIPLE:
                additionalWidth = animationTriple.get(0).originalWidth;
                additionalHeight = animationTriple.get(0).originalHeight;
                mainAnim = animationTriple;
                break;
            case MEDIUM_EXPLOSION_DEFAULT:
                additionalWidth = animationDefault.get(0).originalWidth;
                additionalHeight = animationDefault.get(0).originalHeight;
                mainAnim = animationDefault;
                break;
            case HUGE_EXPLOSION:
                additionalWidth = animationHuge.get(0).originalWidth;
                additionalHeight = animationHuge.get(0).originalHeight;
                mainAnim = animationHuge;
                break;
        }
        x = X - additionalWidth / 2f;
        y = Y - additionalHeight / 2f;

        visible = true;
    }

    @Override
    public void reset() {
        timer = 0;
    }

    @Override
    public void render() {
        spriteBatch.draw(mainAnim.get(timer), x, y, additionalWidth, additionalHeight);

        timer += delta;
        if (mainAnim.isFinished()) {
            visible = false;
        }
    }

    @Override
    public void update() {
    }
}
