package com.warfare.darkannihilation.scenes.pause;

import static com.badlogic.gdx.Input.Keys.BACK;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.utils.ClickListener;
import com.warfare.darkannihilation.widgets.Button;

public class PauseClickListener extends ClickListener {
    private final Runnable actionOnBackButton;
    private final Array<Button> buttons;

    PauseClickListener(Runnable actionOnBackButton, Array<Button> buttons) {
        this.actionOnBackButton = actionOnBackButton;
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

    @Override
    public boolean keyDown(int key) {
        if (key == BACK) {
            actionOnBackButton.run();
        }
        return true;
    }
}
