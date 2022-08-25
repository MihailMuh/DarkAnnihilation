package com.warfare.darkannihilation.scenes.menu;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.Button;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.scenes.firstlevel.FirstLevel;
import com.warfare.darkannihilation.screens.Screen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.Font;

public class Menu extends Scene {
    private final Button[] buttons = new Button[4];

    public Menu(MainGameManager mainGameManager) {
        super(mainGameManager);
        getImages().loadMenuImages();
        getSounds().loadMenuSounds();
    }

    @Override
    public void create() {
        getImages().getMenuImages();
        getSounds().getMenuSounds();

        screen = new Screen(getImages().menuScreenGIF, SCREEN_WIDTH, SCREEN_HEIGHT);

        Button.buttonFont = Font.scaledFontWrap(getFonts().canisMinor, getImages().buttonPress.originalWidth - 140,
                getLocales().quit, getLocales().start, getLocales().topScore, getLocales().settings);

        int step = 50;
        buttons[0] = new Button(getLocales().topScore, HALF_SCREEN_WIDTH + step / 2f, 10, () -> {
        });
        buttons[1] = new Button(getLocales().start, buttons[0].getX() - buttons[0].getWidth() - step, 10,
                () -> mainGameManager.startScene(new FirstLevel(mainGameManager), true));
        buttons[2] = new Button(getLocales().settings, buttons[0].right() + step, 10, () -> {
        });
        buttons[3] = new Button(getLocales().quit, buttons[1].getX() - buttons[0].getWidth() - step, 10, () -> Gdx.app.exit());

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
    public void dispose() {
        super.dispose();
        getImages().disposeMenuImages();
        getSounds().disposeMenuSounds();
    }
}
