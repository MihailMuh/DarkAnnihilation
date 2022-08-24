package com.warfare.darkannihilation.scenes.countdown;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.screens.Screen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.Font;

public class Countdown extends Scene {
    private final Font countdownFont;

    private String text = "3";
    private float textX, textY, lastSwitch;
    private int count = 3;

    public Countdown(MainGameManager mainGameManager, Screen screen) {
        super(mainGameManager);
        this.screen = screen;

        countdownFont = Font.scaledFontWrap(getFonts().canisMinor, SCREEN_WIDTH - 400, getLocales().shoot);
    }

    @Override
    public void update() {
        screen.x -= getPlayer().speedX / 2.8f;
        getPlayer().update();

        if (time - lastSwitch > 1) {
            lastSwitch = time;

            if (count == -1) mainGameManager.finishScene();
            else {
                if (count == 0) {
                    text = getLocales().shoot;
                    getSounds().readySound1.play();
                } else {
                    text = String.valueOf(count);
                    getSounds().readySound0.play();
                }

                textX = HALF_SCREEN_WIDTH - countdownFont.getHalfTextWidth(text);
                textY = HALF_SCREEN_HEIGHT + countdownFont.getHalfTextHeight(text);

                count--;
            }
        }
    }

    @Override
    public void render() {
        countdownFont.draw(textX, textY, text);
    }
}
