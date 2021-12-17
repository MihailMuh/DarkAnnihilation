package com.warfare.darkannihilation.systemd.menu;

import com.warfare.darkannihilation.Button;
import com.warfare.darkannihilation.systemd.BaseClickListener;

class MenuClickListener extends BaseClickListener {
    private final Button[] buttons;

    MenuClickListener(Button[] buttons) {
        super();
        this.buttons = buttons;
    }

    @Override
    public void touchUp(float x, float y, int pointer) {
        for (Button button : buttons) {
            button.onClick(x, y);
        }
    }

    @Override
    public void touchDragged(float x, float y, int pointer) {
        for (Button button : buttons) {
            button.sweep(x, y);
        }
    }
}