package com.warfare.darkannihilation.scenes.gameover;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.screens.Screen;
import com.warfare.darkannihilation.utils.Font;

public class GameOverScreen extends Screen {
    private final Font fontFinger;
    private final float textFingerX, textFingerY;

    private final Font fontDead;
    private final float textDeadX, textDeadY;

    public GameOverScreen() {
        super(getImages().gameOverScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

        fontDead = Font.scaledFontWrap(getFonts().fiendish, HALF_SCREEN_WIDTH + 150, getLocales().youDead);

        textDeadX = HALF_SCREEN_WIDTH - fontDead.getHalfTextWidth(getLocales().youDead);
        textDeadY = HALF_SCREEN_HEIGHT + fontDead.getTextHeight(getLocales().youDead) / 1.4f;

        fontFinger = Font.scaledFontWrap(getFonts().canisMinor, HALF_SCREEN_WIDTH + 250, getLocales().tapScreenWith2Fingers);
        textFingerX = HALF_SCREEN_WIDTH - fontFinger.getHalfTextWidth(getLocales().tapScreenWith2Fingers);
        textFingerY = HALF_SCREEN_HEIGHT / 4f + fontFinger.getHalfTextHeight(getLocales().tapScreenWith2Fingers);
    }

    @Override
    public void render() {
        super.render();

        fontDead.draw(textDeadX, textDeadY, getLocales().youDead);
        fontFinger.draw(textFingerX, textFingerY, getLocales().tapScreenWith2Fingers);
    }
}
