package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Image;

public abstract class MovementSprite extends BaseSprite {
    protected final int topY;
    protected final int maxHealth;
    protected int health;

    public final int damage;
    public final int killScore;

    public float speedX, speedY;

    public MovementSprite(Image image, int maxHealth, int damage, int killScore) {
        this(image, image.width, image.height, maxHealth, damage, killScore);
    }

    public MovementSprite(Image image, int width, int height, int maxHealth, int damage, int killScore) {
        super(image, width, height);

        this.maxHealth = maxHealth;
        this.damage = damage;
        this.killScore = killScore;

        health = maxHealth;
        topY = SCREEN_HEIGHT + height;
    }

    public void damage(int damage) {
        health -= damage;

        if (isDead()) {
            internalKill();
        }
    }

    public final boolean killedBy(MovementSprite sprite) {
        boolean killed = false;

        if (intersect(sprite)) {
            sprite.damage(damage);

            killed = sprite.damage >= health;
            damage(sprite.damage);
        }
        return killed;
    }

    public final boolean isDead() {
        return health <= 0;
    }

    public abstract void kill();

    protected final void internalKill() {
        visible = false;
        kill();
    }

    protected void explosion(byte type) {
        float X = centerX();
        float Y = centerY();

        Processor.postToLooper(() -> getPools().explosionPool.obtain(X, Y, type));
    }

    protected void explodeSmall() {
        explosion(SMALL_EXPLOSION_DEFAULT);
    }

    protected void explodeSmallTriple() {
        explosion(SMALL_EXPLOSION_TRIPLE);
    }

    protected void explodeDefault() {
        explosion(MEDIUM_EXPLOSION_DEFAULT);
        getSounds().boomSound.play();
    }

    protected void explodeDefaultTriple() {
        explosion(MEDIUM_EXPLOSION_TRIPLE);
        getSounds().boomSound.play();
    }

    protected void explodeHuge() {
        explosion(HUGE_EXPLOSION);
        getSounds().megaBoomSound.play();
    }
}
