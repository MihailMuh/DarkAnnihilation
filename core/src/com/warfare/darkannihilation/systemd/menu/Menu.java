package com.warfare.darkannihilation.systemd.menu;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.Button;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.screens.StaticScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.firstlevel.FirstLevel;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.FontWrap;

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

        screen = new StaticScreen(getImages().menuScreenGIF);

        Button.buttonFont = FontWrap.scaledFontWrap(getFonts().canisMinor,
                getImages().buttonPress.width - 140, "Quit", "Start", "Top Score", "Settings");

        int step = 50;
        buttons[0] = new Button("Top Score", HALF_SCREEN_WIDTH + step / 2f, 10, () -> {
        });
        buttons[1] = new Button("Start", buttons[0].x - buttons[0].width - step, 10,
                () -> mainGameManager.startScene(new FirstLevel(mainGameManager), true));
        buttons[2] = new Button("Settings", buttons[0].right() + step, 10, () -> {
        });
        buttons[3] = new Button("Quit", buttons[1].x - buttons[0].width - step, 10, () -> Gdx.app.exit());

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
