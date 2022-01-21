package com.warfare.darkannihilation.systemd.game;

import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.FontWrap;

public class Countdown extends Scene {
    private final Runnable function;
    private final FontWrap countdownFont;

    private String text = "3";
    private float textX, textY, lastSwitch;
    private int count = 3;

    public Countdown(MainGameManager mainGameManager, Runnable runnable) {
        super(mainGameManager);
        function = runnable;
        countdownFont = new FontWrap(mainGameManager.fontHub.canisMinorHuge,
                FontHub.resizeFont(mainGameManager.fontHub.canisMinorHuge, SCREEN_WIDTH - 400, "SHOOT!"));
    }

    @Override
    public void update() {
        function.run();

        if (time - lastSwitch > 1) {
            lastSwitch = time;

            if (count == -1) mainGameManager.finishScene();
            else {
                if (count == 0) text = "SHOOT!";
                else text = String.valueOf(count);

                textX = HALF_SCREEN_WIDTH - countdownFont.getTextWidth(text) / 2f;
                textY = HALF_SCREEN_HEIGHT + countdownFont.getTextHeight(text) / 2f;

                count--;
            }
        }
    }

    @Override
    public void render() {
        countdownFont.draw(textX, textY, text);
    }
}
