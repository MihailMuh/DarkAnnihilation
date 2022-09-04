package com.warfare.darkannihilation.scenes.pause;

import static com.badlogic.gdx.utils.TimeUtils.millis;
import static com.warfare.darkannihilation.hub.Resources.getBatch;
import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getShaders;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;
import static java.lang.Math.max;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.Frontend;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.utils.ClickListener;
import com.warfare.darkannihilation.utils.Font;

class Countdown extends Scene {
    private final Font countdownFont;
    private final BlurScreen blurScreen;

    private String text = "3";
    private float textX, textY;
    private long lastSwitch;
    private int count = 3;

    private float currentBlur;
    private final float countdownTime = 300;

    private long lastBlur;
    private final long blurTime = 17;

    Countdown(MainGameManager mainGameManager, float initialRadiusForBlurScreen) {
        super(mainGameManager, new ClickListener());
        final float step = initialRadiusForBlurScreen / (countdownTime * 2f / blurTime); // почти методом подбора

        currentBlur = initialRadiusForBlurScreen;
        blurScreen = new BlurScreen(initialRadiusForBlurScreen) {
            @Override
            public void changeBlurLevel() {
                // не испольую time, т.к. Watch.stopTime() в Pause.create();
                if (millis() - lastBlur >= blurTime) {
                    lastBlur = millis();
                    currentBlur -= step;

                    getShaders().blurShader.setUniformf("radius", max(0, currentBlur));
                }
            }
        };

        countdownFont = Font.scaledFontWrap(getFonts().canisMinor, SCREEN_WIDTH - 400, getLocales().shoot);
    }

    @Override
    public void pause() {
        super.pause();
        getSounds().firstLevelMusic.pause();
    }

    @Override
    public void resume() {
        super.resume();
        getSounds().firstLevelMusic.play();
    }

    @Override
    public void update() {
        if (millis() - lastSwitch >= countdownTime) {
            lastSwitch = millis();

            if (count == 0) {
                mainGameManager.finishScene(this);
            } else {
                text = String.valueOf(count);

                textX = HALF_SCREEN_WIDTH - countdownFont.getHalfTextWidth(text);
                textY = HALF_SCREEN_HEIGHT + countdownFont.getHalfTextHeight(text);

                count--;
            }
        }
    }

    @Override
    public void render() {
        blurScreen.apply();

        countdownFont.draw(textX, textY, text);
    }

    @Override
    public void dispose() {
        super.dispose();

        ShaderProgram.pedantic = true;
        Frontend.enableBlurBuffer = false;

        Watch.resetTime();

        getBatch().setShader(null);
        getShaders().disposeBlurShader();
        blurScreen.dispose();
    }
}
