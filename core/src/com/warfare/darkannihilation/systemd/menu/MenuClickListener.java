package com.warfare.darkannihilation.systemd.menu;

import com.warfare.darkannihilation.Button;
import com.warfare.darkannihilation.utils.ClickListener;

class MenuClickListener extends ClickListener {
    private final Button[] buttons;

    MenuClickListener(Button[] buttons) {
        this.buttons = buttons;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        for (Button button : buttons) {
            button.onClick(x, y);
        }
        return true;
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        for (Button button : buttons) {
            button.sweep(x, y);
        }
        return false;
    }
}