package com.warfare.darkannihilation.widgets;

import static com.badlogic.gdx.utils.TimeUtils.millis;
import static com.warfare.darkannihilation.constants.Constants.BUTTON_CLICK_TIME;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Service.sleep;
import static com.warfare.darkannihilation.systemd.service.Service.vibrate;

import com.badlogic.gdx.graphics.Color;
import com.warfare.darkannihilation.abstraction.BaseButton;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Font;

public class Button extends BaseButton {
    private Runnable runnable;
    private String text;
    private float textX;
    private float textY;
    private boolean pressed, swept;
    private long tapTime;

    public static Font buttonFont;

    public Button(String name, float X, float Y, Runnable runnable) {
        super(getImages().buttonNotPress);

        setParams(name, X, Y, runnable);
    }

    private void updateTexPosition() {
        textX = centerX() - buttonFont.getHalfTextWidth(text);
        textY = centerY() + buttonFont.getHalfTextHeight(text) + 5;
    }

    @Override
    public void setCenter(float x, float y) {
        super.setCenter(x, y);
        updateTexPosition();
    }

    public void setParams(String name, float x, float y, Runnable runnable) {
        this.runnable = runnable;
        text = name;

        setPosition(x, y);
        updateTexPosition();
    }

    @Override
    public void onClick(float x, float y) {
        if (millis() - tapTime >= BUTTON_CLICK_TIME) {
            tapTime = millis();
            pressed = false;

            if (checkClick(x, y)) {
                pressed = true;

                getSounds().spaceBarSound.play();
                vibrate(50);
                sleep(140);

                pressed = false;
                Processor.postTask(runnable);
            }
        }
    }

    public void sweep(float X, float Y) {
        pressed = checkClick(X, Y);

        if (pressed) {
            if (!swept) {
                swept = true;
            }
        } else {
            swept = false;
        }
    }

    @Override
    public void render() {
        if (pressed) {
            setRegion(getImages().buttonPress);
            buttonFont.setColor(Color.LIGHT_GRAY);
        } else {
            setRegion(getImages().buttonNotPress);
            buttonFont.resetColor();
        }

        super.render();
        buttonFont.draw(textX, textY, text);
    }
}
