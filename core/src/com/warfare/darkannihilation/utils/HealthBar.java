package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.hub.Resources.getBatch;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static java.lang.Math.max;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class HealthBar {
    private final Sprite outlineColor, barColor;
    private final float halfDeltaHeights;
    private final float maxHealth;

    public HealthBar(int outlineLen, int maxHealth, int outlineHeight, int barHeight) {
        this(getImages().whiteColor, getImages().redColor, outlineLen, maxHealth, outlineHeight, barHeight);
    }

    public HealthBar(AtlasRegion outlineColor, AtlasRegion barColor, int outlineWidth, int maxHealth, int outlineHeight, int barHeight) {
        this.outlineColor = new Sprite(outlineColor);
        this.barColor = new Sprite(barColor);
        this.maxHealth = maxHealth;

        int deltaHeights = outlineHeight - barHeight;
        halfDeltaHeights = deltaHeights / 2f;

        this.barColor.setSize(outlineWidth - deltaHeights, barHeight);
        this.outlineColor.setSize(outlineWidth, outlineHeight);

        hide();
    }

    public void setOutlineBarCoords(float x, float y) {
        outlineColor.setCenter(x, y);

        // чтобы dirty стало true - вершины пересчитываться будут только в barColor.draw
        barColor.scale(0);
        barColor.setCenterY(y);
        barColor.setX(outlineColor.getX() + halfDeltaHeights);
    }

    public void updateHealthBar(int health) {
        barColor.setSize(max(0, (health / maxHealth) * outlineColor.getWidth() - halfDeltaHeights), barColor.getHeight());
    }

    public void render() {
        outlineColor.draw(getBatch());
        barColor.draw(getBatch());
    }

    public void hide() {
        outlineColor.setY(Integer.MAX_VALUE);
        barColor.setY(Integer.MAX_VALUE);
    }
}
