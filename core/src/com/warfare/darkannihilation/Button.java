package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.constants.Constants.BUTTON_CLICK_TIME;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.warfare.darkannihilation.abstraction.BaseButton;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.utils.FontWrap;

public class Button extends BaseButton {

    private Runnable runnable;
    private String text;
    private float textX;
    private float textY;
    private volatile boolean pressed;
    private float shootTime;

    public static FontWrap buttonFont;

    public Button(String name, float X, float Y, Runnable runnable) {
        super(getImages().buttonNotPress);

        setParams(name, X, Y, runnable);
    }

    public void setParams(String name, float X, float Y, Runnable runnable) {
        this.runnable = runnable;
        text = name;

        x = X;
        y = Y;

        textX = centerX() - buttonFont.getTextWidth(text) / 2f;
        textY = centerY() + buttonFont.getTextHeight(text) / 2f + 5;
    }

    @Override
    public void onClick(float X, float Y) {
        if (time - shootTime >= BUTTON_CLICK_TIME) {
            shootTime = time;
            pressed = false;

            if (checkClick(X, Y)) {
                pressed = true;
                Gdx.input.vibrate(50);

                Service.sleep(95);

                pressed = false;
                Processor.post(runnable);
            }
        }
    }

    public void sweep(float X, float Y) {
        pressed = checkClick(X, Y);
    }

    @Override
    public void render() {
        if (pressed) {
            getImages().buttonPress.draw(x, y);
            buttonFont.setColor(Color.LIGHT_GRAY);
        } else {
            super.render();
            buttonFont.resetColor();
        }

        buttonFont.draw(textX, textY, text);
    }
}
