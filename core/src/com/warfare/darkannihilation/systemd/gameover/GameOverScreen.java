package com.warfare.darkannihilation.systemd.gameover;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Service.print;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.warfare.darkannihilation.screens.BaseScreen;
import com.warfare.darkannihilation.utils.FontWrap;

public class GameOverScreen extends BaseScreen {
    private final FontWrap fontFinger;
    private final float textFingerX, textFingerY;
    private final String TEXT_FINGER = "Tap this screen with two fingers to restart";

    private final FontWrap fontDead;
    private final String YOU_DEAD = "You Dead";
    private final float textDeadX, textDeadY;

    public GameOverScreen() {
        super(getImages().gameOverScreen);

        fontDead = FontWrap.scaledFontWrap(getFonts().fiendish, HALF_SCREEN_WIDTH + 150, YOU_DEAD);

        textDeadX = HALF_SCREEN_WIDTH - fontDead.getTextWidth(YOU_DEAD) / 2f;
        textDeadY = HALF_SCREEN_HEIGHT + fontDead.getTextHeight(YOU_DEAD) / 1.4f;

        fontFinger = FontWrap.scaledFontWrap(getFonts().canisMinor, HALF_SCREEN_WIDTH + 250, TEXT_FINGER);
        textFingerX = HALF_SCREEN_WIDTH - fontFinger.getTextWidth(TEXT_FINGER) / 2f;
        textFingerY = HALF_SCREEN_HEIGHT / 4f + fontFinger.getTextHeight(TEXT_FINGER) / 2f;
    }

    @Override
    public void render() {
        super.render();

        fontDead.draw(textDeadX, textDeadY, YOU_DEAD);
        fontFinger.draw(textFingerX, textFingerY, TEXT_FINGER);
    }
}
