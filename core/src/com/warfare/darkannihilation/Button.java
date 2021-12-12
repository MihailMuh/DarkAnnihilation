package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.Constants.BUTTON_CLICK_TIME;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.warfare.darkannihilation.abstraction.BaseButton;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class Button extends BaseButton {
    private volatile boolean pressed;
    private float shootTime;

    public Button() {
        super(ImageHub.buttonNotPress);
    }

    public Button setParams(float X, float Y) {
        x = X;
        y = Y;

        return this;
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
            spriteBatch.draw(ImageHub.buttonPress, x, y, 400, height);
        } else {
            spriteBatch.draw(image, x, y, 400, height);
        }
    }
}
