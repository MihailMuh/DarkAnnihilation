package com.warfare.darkannihilation.scenes.menu;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.scenes.firstlevel.FirstLevel;
import com.warfare.darkannihilation.screens.AnimatedScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Font;
import com.warfare.darkannihilation.widgets.Button;

public class Menu extends Scene {
    private final Array<Button> buttons = new Array<>(true, 4, Button.class);

    public Menu(MainGameManager mainGameManager) {
        super(mainGameManager);
        getImages().loadMenuImages();
        getSounds().loadMenuSounds();
    }

    @Override
    public void create() {
        super.create();
        getImages().getMenuImages();
        getSounds().getMenuSounds();

        screen = new AnimatedScreen(getImages().menuScreenGIF, SCREEN_WIDTH, SCREEN_HEIGHT);

        Button.buttonFont = Font.scaledFontWrap(getFonts().canisMinor, getImages().buttonPress.originalWidth - 140,
                getLocales().quit, getLocales().start, getLocales().topScore, getLocales().settings);

        int step = 50;
        Button buttonTopScore = new Button(getLocales().topScore, HALF_SCREEN_WIDTH + step / 2f, 10, () -> {
        });
        Button buttonStart = new Button(getLocales().start, buttonTopScore.getX() - buttonTopScore.getWidth() - step, 10,
                () -> mainGameManager.startScene(true, FirstLevel.class, mainGameManager));

        buttons.addAll(
                buttonTopScore,
                buttonStart,
                new Button(getLocales().settings, buttonTopScore.right() + step, 10, () -> {
                }),
                new Button(getLocales().quit, buttonStart.getX() - buttonTopScore.getWidth() - step, 10, () -> Gdx.app.exit())
        );

        clickListener = new MenuClickListener(buttons);
        Processor.multiProcessor.insertProcessor(clickListener);

        getSounds().menuMusic.play();
    }

    @Override
    public void render() {
        screen.render();

        for (Button button : buttons) {
            button.render();
        }
    }

    @Override
    public void updateInThread() {
        screen.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        getImages().disposeMenuImages();
        getSounds().disposeMenuSounds();
    }
}
