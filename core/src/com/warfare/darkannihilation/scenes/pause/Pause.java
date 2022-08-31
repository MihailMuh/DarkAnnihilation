package com.warfare.darkannihilation.scenes.pause;

import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.scenes.firstlevel.FirstLevel;
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.widgets.Button;

public class Pause extends Scene {
    private final Array<Button> buttons = new Array<>(true, 3, Button.class);

    public Pause(MainGameManager mainGameManager) {
        super(mainGameManager);
    }

    @Override
    public void create() {
        super.create();

        Button buttonContinue = new Button(getLocales().continueStr, 0, 0, () -> mainGameManager.finishScene(this));
        buttonContinue.setCenter(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT + 200);

        Button buttonRestart = new Button(getLocales().restart, 0, 0, () -> mainGameManager.startScene(true, FirstLevel.class, mainGameManager));
        buttonRestart.setCenter(HALF_SCREEN_WIDTH, buttonContinue.getY() - 120);

        Button buttonToMenu = new Button(getLocales().toMenu, 0, 0, () -> mainGameManager.startScene(true, Menu.class, mainGameManager));
        buttonToMenu.setCenter(HALF_SCREEN_WIDTH, buttonRestart.getY() - 120);

        Button buttonExit = new Button(getLocales().quit, 0, 0, Gdx.app::exit);
        buttonExit.setCenter(HALF_SCREEN_WIDTH, buttonToMenu.getY() - 120);

        buttons.addAll(buttonContinue, buttonRestart, buttonToMenu, buttonExit);

        clickListener = new PauseClickListener(() -> mainGameManager.finishScene(this), buttons);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void pause() {
        super.pause();
        getSounds().pauseMusic.pause();
    }

    @Override
    public void resume() {
        super.resume();
        getSounds().pauseMusic.play();
    }

    @Override
    public void render() {
        for (Button button : buttons) {
            button.render();
        }
    }
}
