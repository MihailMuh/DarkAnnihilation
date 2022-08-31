package com.warfare.darkannihilation.widgets;

import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.BaseButton;

public class PauseButton extends BaseButton {
    public PauseButton() {
        super(getImages().pauseButton);

        visible = false;
        setCenter(SCREEN_WIDTH - 150, SCREEN_HEIGHT - 150);
    }

    @Override
    public void onClick(float x, float y) {

    }

    @Override
    public void reset() {
        visible = true;
    }

    @Override
    public boolean checkClick(float x, float y) {
        return super.checkClick(x, y) && visible;
    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
