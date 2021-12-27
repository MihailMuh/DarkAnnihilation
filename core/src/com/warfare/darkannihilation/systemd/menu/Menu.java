package com.warfare.darkannihilation.systemd.menu;

import static com.warfare.darkannihilation.constants.Assets.MENU_ATLAS;
import static com.warfare.darkannihilation.hub.FontHub.buttonFont;
import static com.warfare.darkannihilation.hub.FontHub.canisMinor;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.Button;
import com.warfare.darkannihilation.FontWrap;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.screens.BackgroundScreen;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.game.Game;

public class Menu extends Scene {
    private final Button[] buttons = new Button[4];

    public Menu(MainGameManager mainGameManager) {
        super(mainGameManager);
    }

    @Override
    public void readyAssets() {
        mainGameManager.imageHub.loadAtlas(MENU_ATLAS);
    }

    @Override
    public void create() {
        mainGameManager.imageHub.getMenuImages();

        screen = new BackgroundScreen(mainGameManager.imageHub.menuScreenGIF);

        buttonFont = new FontWrap(canisMinor, FontHub.resizeFont(canisMinor, ImageHub.buttonPress.originalWidth - 150,
                "Quit", "Start", "Top Score", "Settings"));
        int step = 50;
        buttons[0] = new Button("Top Score", HALF_SCREEN_WIDTH + step / 2f, 10, () -> {
        });
        buttons[1] = new Button("Start", buttons[0].x - buttons[0].width - step, 10,
                () -> mainGameManager.startScene(new Game(mainGameManager), true));
        buttons[2] = new Button("Settings", buttons[0].right() + step, 10, () -> {
        });
        buttons[3] = new Button("Quit", buttons[1].x - buttons[0].width - step, 10, () -> Gdx.app.exit());

        clickListener = new MenuClickListener(buttons);
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
        mainGameManager.imageHub.disposeMenuImages();
    }
}
