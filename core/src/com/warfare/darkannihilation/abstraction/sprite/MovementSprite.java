package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.pools.ExplosionPool;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Image;

public abstract class MovementSprite extends BaseSprite {
    private final ExplosionPool explosionPool = getPools().explosionPool;

    protected final int topY;
    protected final int maxHealth;
    protected int health;

    public final int damage;
    public final int killScore;

    public float speedX, speedY;

    public MovementSprite(Image image, int maxHealth, int damage, int killScore) {
        super(image);

        this.maxHealth = maxHealth;
        this.damage = damage;
        this.killScore = killScore;

        topY = SCREEN_HEIGHT + height;
    }

    public void damage(MovementSprite sprite) {
        health -= sprite.damage;

        if (health <= 0) kill();
    }

    public boolean killedBy(MovementSprite sprite) {
        boolean killed = false;
        if (intersect(sprite)) {
            health -= sprite.damage;

            if (health <= 0) {
                kill();
                killed = true;
            }

            sprite.damage(this);
        }
        return killed;
    }

    public void kill() {
        visible = false;
        reset();
    }

    protected void explosion(byte type) {
        float X = centerX();
        float Y = centerY();

        Processor.postToLooper(() -> explosionPool.obtain(X, Y, type));
    }

    protected void explodeSmall() {
        explosion(SMALL_EXPLOSION_DEFAULT);
    }

    protected void explodeSmallTriple() {
        explosion(SMALL_EXPLOSION_TRIPLE);
    }

    protected void explodeDefault() {
        explosion(MEDIUM_EXPLOSION_DEFAULT);
    }

    protected void explodeDefaultTriple() {
        explosion(MEDIUM_EXPLOSION_TRIPLE);
    }

    protected void explodeHuge() {
        explosion(HUGE_EXPLOSION);
    }
}
