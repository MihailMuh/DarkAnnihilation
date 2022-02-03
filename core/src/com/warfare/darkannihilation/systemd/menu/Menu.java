package com.warfare.darkannihilation.systemd.menu;

import static com.warfare.darkannihilation.constants.Assets.MENU_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.MENU_MUSIC;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.Button;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.FontWrap;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.screens.StaticScreen;
import com.warfare.darkannihilation.systemd.game.Game;

public class Menu extends Scene {
    private final Button[] buttons = new Button[4];

    public Menu(MainGameManager mainGameManager) {
        super(mainGameManager);
        mainGameManager.assetManager.loadAtlas(MENU_ATLAS);
        mainGameManager.assetManager.loadMusic(MENU_MUSIC);
    }

    @Override
    public void create() {
        mainGameManager.imageHub.getMenuImages();
        mainGameManager.soundHub.getMenuSounds();

        screen = new StaticScreen(mainGameManager.imageHub.menuScreenGIF);

        Button.buttonFont = FontWrap.scaledFontWrap(mainGameManager.fontHub.canisMinor,
                mainGameManager.imageHub.buttonPress.originalWidth - 140, "Quit", "Start", "Top Score", "Settings");

        int step = 50;
        buttons[0] = new Button(mainGameManager.imageHub, "Top Score", HALF_SCREEN_WIDTH + step / 2f, 10, () -> {
        });
        buttons[1] = new Button(mainGameManager.imageHub, "Start", buttons[0].x - buttons[0].width - step, 10,
                () -> mainGameManager.startScene(new Game(mainGameManager), true));
        buttons[2] = new Button(mainGameManager.imageHub, "Settings", buttons[0].right() + step, 10, () -> {
        });
        buttons[3] = new Button(mainGameManager.imageHub, "Quit", buttons[1].x - buttons[0].width - step, 10, () -> Gdx.app.exit());

        clickListener = new MenuClickListener(buttons);
        Processor.multiProcessor.insertProcessor(clickListener);

        mainGameManager.soundHub.menuMusic.play();
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
        mainGameManager.imageHub.disposeMenuImages();
        mainGameManager.soundHub.disposeMenuSounds();
    }
}
