package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class AggressiveSprite extends LiveSprite {
    protected final float SHh;
    public final int damage;

    public AggressiveSprite(AtlasRegion texture, int damage) {
        super(texture);
        this.damage = damage;

        SHh = SCREEN_HEIGHT + height;
    }

    public AggressiveSprite(AtlasRegion texture, int damage, float Y) {
        super(texture);
        this.damage = damage;

        SHh = Y;
    }

    public boolean damage(int dmg) {
        return false;
    }

    public void boom() {
        explode();
        reset();
    }

    protected abstract void explode();
}
