package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.hub.Resources.getImages;
import static java.lang.Math.max;

public class HealthBar {
    private final Image outlineColor, barColor;
    private final int outlineHeight, barHeight;
    private final int halfDeltaHeights;
    private final int outlineLen;
    private final int maxHealth;
    private final float halfOutlineLen, halfOutlineHeight;

    private float outlineY, outlineX = Integer.MAX_VALUE;
    private float barY, barX = Integer.MAX_VALUE;

    private int currentHealthBarLen;

    public HealthBar(int outlineLen, int maxHealth, int outlineHeight, int barHeight) {
        this(getImages().whiteColor, getImages().redColor, outlineLen, maxHealth, outlineHeight, barHeight);
    }

    public HealthBar(Image outlineColor, Image barColor, int outlineLen, int maxHealth, int outlineHeight, int barHeight) {
        this.outlineColor = outlineColor;
        this.barColor = barColor;
        this.outlineLen = outlineLen;
        this.maxHealth = maxHealth;
        this.outlineHeight = outlineHeight;
        this.barHeight = barHeight;

        int deltaHeights = outlineHeight - barHeight;
        halfDeltaHeights = deltaHeights / 2;

        halfOutlineLen = outlineLen / 2f;
        halfOutlineHeight = outlineHeight / 2f;

        currentHealthBarLen = outlineLen - deltaHeights;
    }

    public void setOutlineBarCoords(float x, float y) {
        outlineX = x - halfOutlineLen;
        outlineY = y + halfOutlineHeight;

        barX = outlineX + halfDeltaHeights;
        barY = outlineY + halfDeltaHeights;
    }

    public void updateHealthBar(int health) {
        currentHealthBarLen = max(0, (int) ((health / (float) maxHealth) * outlineLen - halfDeltaHeights));
    }

    public void render() {
        outlineColor.draw(outlineX, outlineY, outlineLen, outlineHeight);
        barColor.draw(barX, barY, currentHealthBarLen, barHeight);
    }

    public void hide() {
        outlineY = Integer.MAX_VALUE;
    }
}
