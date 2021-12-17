package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.Constants.BUTTON_CLICK_TIME;
import static com.warfare.darkannihilation.hub.FontHub.fontButtons;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.warfare.darkannihilation.abstraction.BaseButton;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.service.Service;

public class Button extends BaseButton {
    private final GlyphLayout glyph = new GlyphLayout();
    private String text;
    private float textX;
    private float textY;
    private volatile boolean pressed;
    private float shootTime;

    public Button(String name, float X, float Y) {
        super(ImageHub.storage.buttonNotPress);

        setParams(name, X, Y);
    }

    public void setParams(String name, float X, float Y) {
        text = name;

        x = X;
        y = Y;

        glyph.setText(fontButtons, text);

        textX = centerX() - glyph.width / 2f;
        textY = centerY() + glyph.height / 2f + 5;
    }

    @Override
    public void onClick(float X, float Y) {
        if (time - shootTime >= BUTTON_CLICK_TIME) {
            shootTime = time;

            if (checkClick(X, Y)) {
                pressed = true;
                Service.sleep(160);
                pressed = false;
            }
        }
    }

    public void sweep(float X, float Y) {
        pressed = checkClick(X, Y);
    }

    @Override
    public void render() {
        if (pressed) {
            spriteBatch.draw(ImageHub.storage.buttonPress, x, y, width, height);
            fontButtons.setColor(Color.LIGHT_GRAY);
        } else {
            spriteBatch.draw(image, x, y, width, height);
            fontButtons.setColor(255, 255, 255, 1);
        }

        fontButtons.draw(spriteBatch, text, textX, textY);
    }
}
