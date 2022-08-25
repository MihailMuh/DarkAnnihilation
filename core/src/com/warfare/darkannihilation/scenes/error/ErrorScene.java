package com.warfare.darkannihilation.scenes.error;

import static com.warfare.darkannihilation.hub.Resources.getBatch;
import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.MainGame;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.utils.ClickListener;
import com.warfare.darkannihilation.utils.Font;
import com.warfare.darkannihilation.utils.ScenesArray;

public class ErrorScene extends Scene {
    private final Array<String> report = new Array<>(true, 20, String.class);
    private final MainGame mainGame;
    private final ScenesArray scenesArray;

    private float unexpectedErrorTextY, pleaseExitTextY;

    private Font errorFont;
    private Font pleaseExitFont;

    public boolean running = false;

    public ErrorScene(MainGame mainGame, MainGameManager mainGameManager, ScenesArray scenesArray) {
        super(mainGameManager, new ClickListener());
        this.mainGame = mainGame;
        this.scenesArray = scenesArray;
    }

    public void hardRun() {
        Gdx.app.postRunnable(() -> {
            try {
                mainGame.pause();
            } catch (Exception ignored) {
            }
            scenesArray.clear();
            scenesArray.add(this);
        });
    }

    public void init(Throwable throwable) {
        running = true;

        report.add("--------- Stack Trace ---------");
        report.add(throwable.toString());

        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for (StackTraceElement traceElement : stackTraceElements) {
            report.add(traceElement.toString());
        }
        report.add("------ End Of Stack Trace ------");

        errorFont = Font.scaledFontWrap(getFonts().canisMinor, SCREEN_WIDTH - 200, report.toArray(String.class));
        errorFont.setColor(Color.SCARLET);

        pleaseExitFont = Font.scaledFontWrap(getFonts().fiendish, SCREEN_WIDTH - 200, getLocales().unexpectedError);
        unexpectedErrorTextY = SCREEN_HEIGHT - 150;
        pleaseExitTextY = unexpectedErrorTextY - 30 - pleaseExitFont.getTextHeight(getLocales().unexpectedError);
    }

    @Override
    public void render() {
        getBatch().setColor(1, 1, 1, 0.65f);
        getBatch().draw(getImages().whiteColor, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        getBatch().setColor(1, 1, 1, 1);

        pleaseExitFont.draw(HALF_SCREEN_WIDTH - pleaseExitFont.getHalfTextWidth(getLocales().unexpectedError),
                unexpectedErrorTextY, getLocales().unexpectedError);
        pleaseExitFont.draw(HALF_SCREEN_WIDTH - pleaseExitFont.getHalfTextWidth(getLocales().pleaseReEnter),
                pleaseExitTextY, getLocales().pleaseReEnter);

        for (int i = 0; i < report.size; i++) {
            String traceElement = report.get(i);
            errorFont.draw(HALF_SCREEN_WIDTH - errorFont.getHalfTextWidth(traceElement),
                    pleaseExitTextY - 150 - (i + 1) * 50, traceElement);
        }
    }
}
