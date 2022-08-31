package com.warfare.darkannihilation.scenes.menu;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.widgets.Button;
import com.warfare.darkannihilation.utils.ClickListener;

class MenuClickListener extends ClickListener {
    private final Array<Button> buttons;

    MenuClickListener(Array<Button> buttons) {
        this.buttons = buttons;
    }

    @Override
    public boolean touchUp(float x, float y) {
        for (Button button : buttons) {
            button.onClick(x, y);
        }
        return true;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        for (Button button : buttons) {
            button.sweep(x, y);
        }
        return true;
    }
}
