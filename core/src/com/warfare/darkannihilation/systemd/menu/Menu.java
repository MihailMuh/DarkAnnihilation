package com.warfare.darkannihilation.systemd.menu;

import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.warfare.darkannihilation.Button;
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
    public void run() {
        mainGameManager.imageHub.loadMenuImages();

        screen = new BackgroundScreen(mainGameManager.imageHub.menuScreenGIF);

        FontHub.resizeFont(FontHub.fontButtons,
                ImageHub.buttonPress.originalWidth - 150,
                "Quit", "Start", "Top Score", "Settings");
        int step = 50;
        buttons[0] = new Button("Top Score", HALF_SCREEN_WIDTH + step / 2f, 10, () -> {
        });
        buttons[1] = new Button("Start", buttons[0].x - buttons[0].width - step, 10,
                () -> mainGameManager.startScene(new Game(mainGameManager)));
        buttons[2] = new Button("Settings", buttons[0].right() + step, 10, () -> {
        });
        buttons[3] = new Button("Quit", buttons[1].x - buttons[0].width - step, 10, () -> {
        });

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
