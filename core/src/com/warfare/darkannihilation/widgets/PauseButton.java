package com.warfare.darkannihilation.widgets;

import static com.warfare.darkannihilation.constants.Constants.PAUSE_BUTTON_CLICK_TIME;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Service.vibrate;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.abstraction.BaseButton;

public class PauseButton extends BaseButton {
    private final Runnable action;
    private float tapTime;

    public PauseButton(Runnable action) {
        super(getImages().pauseButton);
        this.action = action;

        visible = false;
        setCenter(SCREEN_WIDTH - 150, SCREEN_HEIGHT - 150);
    }

    @Override
    public void onClick(float x, float y) {
        if (time - tapTime >= PAUSE_BUTTON_CLICK_TIME) {
            tapTime = time;

            if (checkClick(x, y)) {
                getSounds().spaceBarSound.play();
                vibrate(50);

                Gdx.app.postRunnable(action);
            }
        }
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
